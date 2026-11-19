
package com.Atos.Webaccess.Approve.Priv;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import com.Atos.Webaccess.Approve.hibernate.HibernateSessionFactoryApprove;
import com.Atos.Webaccess.Domain.Tache.Tache;
import com.Atos.Webaccess.User.Entities.User;

@Service
public class PrivUserTacheManager {

    /**
     * DTO used to display users in the privilege management screen.
     * Avoids exposing the User entity's password and security information.
     */
    public static class UserPrivilegeDto {

        private long id;
        private String username;
        private String nom;
        private String prenom;

        public UserPrivilegeDto(long id, String username,
                                String nom, String prenom) {
            this.id = id;
            this.username = username;
            this.nom = nom;
            this.prenom = prenom;
        }

        public long getId() {
            return id;
        }

        public String getUsername() {
            return username;
        }

        public String getNom() {
            return nom;
        }

        public String getPrenom() {
            return prenom;
        }
    }

    /**
     * Retrieves active users belonging to a specific bank.
     * Ordered by username, as in the legacy implementation.
     */
    public List<UserPrivilegeDto> getUsersByBanque(Long idBanque) {

        if (idBanque == null) {
            throw new IllegalArgumentException("Bank ID cannot be null.");
        }

        Session session = null;
        Transaction transaction = null;

        try {
            session = HibernateSessionFactoryApprove.currentSession();
            transaction = session.beginTransaction();

            List<User> users = session.createSelectionQuery(
                    """
                    SELECT u
                    FROM User u
                    WHERE u.idBanque = :idBanque
                      AND u.status = :status
                    ORDER BY u.username
                    """,
                    User.class
            )
            .setParameter("idBanque", idBanque)
            .setParameter("status", 1)
            .getResultList();

            List<UserPrivilegeDto> result = new ArrayList<>();

            for (User user : users) {
                result.add(new UserPrivilegeDto(
                        user.getId(),
                        user.getUsername(),
                        user.getNom(),
                        user.getPrenom()
                ));
            }

            transaction.commit();

            return result;

        } catch (Exception e) {
            rollback(transaction);
            throw new IllegalStateException(
                    "Unable to retrieve users for bank ID: " + idBanque, e
            );

        } finally {
            HibernateSessionFactoryApprove.closeSession();
        }
    }

    /**
     * Retrieves active tasks ordered by label.
     */
    public List<Tache> getTachesValForPriv() {

        Session session = null;
        Transaction transaction = null;

        try {
            session = HibernateSessionFactoryApprove.currentSession();
            transaction = session.beginTransaction();

            List<Tache> tasks = session.createSelectionQuery(
                    """
                    SELECT t
                    FROM Tache t
                    WHERE t.statut = :status
                    ORDER BY t.libelle
                    """,
                    Tache.class
            )
            .setParameter("status", 1)
            .getResultList();

            transaction.commit();

            return tasks;

        } catch (Exception e) {
            rollback(transaction);
            throw new IllegalStateException(
                    "Unable to retrieve active tasks.", e
            );

        } finally {
            HibernateSessionFactoryApprove.closeSession();
        }
    }

    /**
     * Retrieves the IDs of active tasks assigned to a user.
     */
    public List<Integer> getIdTachesUser(Long idUser) {

        if (idUser == null) {
            throw new IllegalArgumentException("User ID cannot be null.");
        }

        Session session = null;
        Transaction transaction = null;

        try {
            session = HibernateSessionFactoryApprove.currentSession();
            transaction = session.beginTransaction();

            List<Integer> taskIds = session.createSelectionQuery(
                    """
                    SELECT t.id
                    FROM Tache t
                    JOIN t.usersTache u
                    WHERE u.id = :idUser
                      AND t.statut = :status
                    ORDER BY t.libelle
                    """,
                    Integer.class
            )
            .setParameter("idUser", idUser)
            .setParameter("status", 1)
            .getResultList();

            transaction.commit();

            return taskIds;

        } catch (Exception e) {
            rollback(transaction);
            throw new IllegalStateException(
                    "Unable to retrieve tasks for user ID: " + idUser, e
            );

        } finally {
            HibernateSessionFactoryApprove.closeSession();
        }
    }

    /**
     * Replaces the task privileges assigned to a user.
     *
     * @param idUser       User identifier.
     * @param selectedIds  IDs of the selected tasks.
     * @return 1 when the operation succeeds.
     */
    public int updatePrivTache(Long idUser, Set<Integer> selectedIds) {

        if (idUser == null) {
            throw new IllegalArgumentException("User ID cannot be null.");
        }

        if (selectedIds == null) {
            selectedIds = new HashSet<>();
        }

        Session session = null;
        Transaction transaction = null;

        try {
            session = HibernateSessionFactoryApprove.currentSession();
            transaction = session.beginTransaction();

            User user = session.find(User.class, idUser);

            if (user == null) {
                throw new IllegalArgumentException(
                        "User not found: " + idUser
                );
            }

            // Remove the user's existing task associations.
            Set<Tache> currentTasks =
                    new HashSet<>(user.getTaches_user());

            for (Tache task : currentTasks) {
                task.getUsersTache().remove(user);
            }

            user.getTaches_user().clear();

            // Add the newly selected tasks.
            for (Integer taskId : selectedIds) {

                if (taskId == null) {
                    continue;
                }

                Tache task = session.find(Tache.class, taskId);

                if (task == null) {
                    throw new IllegalArgumentException(
                            "Task not found: " + taskId
                    );
                }

                if (!Integer.valueOf(1).equals(task.getStatut())) {
                    throw new IllegalArgumentException(
                            "Cannot assign inactive task: " + taskId
                    );
                }

                // Update the owning side of the ManyToMany relationship.
                task.getUsersTache().add(user);

                // Keep the inverse side synchronized in memory.
                user.getTaches_user().add(task);
            }

            transaction.commit();

            return 1;

        } catch (Exception e) {
            rollback(transaction);
            throw new IllegalStateException(
                    "Unable to update task privileges for user ID: " + idUser,
                    e
            );

        } finally {
            HibernateSessionFactoryApprove.closeSession();
        }
    }

    /**
     * Safely rolls back an active transaction.
     */
    private void rollback(Transaction transaction) {

        if (transaction != null && transaction.isActive()) {
            try {
                transaction.rollback();
            } catch (Exception e) {
                // Preserve the original exception.
            }
        }
    }
}
