
package com.Atos.Webaccess.Approve.Priv;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.Atos.Webaccess.Domain.Tache.Tache;
import com.Atos.Webaccess.User.Entities.User;

@Controller
public class PrivUserAction {

    private final PrivUserTacheManager manager;

    public PrivUserAction(PrivUserTacheManager manager) {
        this.manager = manager;
    }

    /**
     * Displays the task privilege management page.
     */
    @GetMapping("/approve/privileges")
    public String go_privTache(
            HttpSession session,
            Model model) {

        session.removeAttribute("error");
        session.removeAttribute("list_tache");
        session.removeAttribute("users");

        session.setAttribute("TARGET", "APPROVE");

        try {
            if (!hasPrivilegeAccess(session)) {
                return unauthorized(model);
            }

            User connectedUser =
                    (User) session.getAttribute("USER_CONNECTED");

            List<PrivUserTacheManager.UserPrivilegeDto> users =
                    manager.getUsersByBanque(connectedUser.getIdBanque());

            List<Tache> tasks = manager.getTachesValForPriv();

            session.setAttribute("users", users);
            session.setAttribute("list_tache", tasks);

            model.addAttribute("users", users);
            model.addAttribute("list_tache", tasks);

            return "privApprove";

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error", "err.java");

            return "privApprove";
        }
    }

    /**
     * AJAX endpoint:
     * Returns JavaScript to check/uncheck the selected user's tasks.
     */
    @GetMapping(
            value = "/approve/ajax_tachesuser",
            produces = "application/javascript;charset=UTF-8"
    )
    @ResponseBody
    public ResponseEntity<String> ajax_tachesuser(
            @RequestParam("iduser") Long idUser,
            HttpSession session) {

        try {
            if (!hasPrivilegeAccess(session)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("");
            }

            List<Tache> tasks =
                    manager.getTachesValForPriv();

            List<Integer> assignedTaskIds =
                    manager.getIdTachesUser(idUser);

            StringBuilder javascript = new StringBuilder();

            javascript.append(
                    "document.getElementById('error_ajax').style.display='none';\n"
            );

            for (Tache task : tasks) {

                boolean checked =
                        assignedTaskIds.contains(task.getId());

                javascript.append(
                        "document.getElementById('ck")
                        .append(task.getId())
                        .append("').checked=")
                        .append(checked)
                        .append(";\n");
            }

            javascript.append(
                    "document.getElementById('divload_').style.display='none';\n"
            );

            javascript.append(
                    "document.getElementById('divtache_').style.display='block';\n"
            );

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(
                            "application/javascript;charset=UTF-8"
                    ))
                    .header(
                            "Content-Security-Policy",
                            "frame-ancestors 'self'"
                    )
                    .body(javascript.toString());

        } catch (Exception e) {
            e.printStackTrace();

            String errorScript =
                    "document.getElementById('error_ajax').style.display='block';\n"
                    + "document.getElementById('divload_').style.display='none';\n"
                    + "document.getElementById('divtache_').style.display='none';";

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(
                            "application/javascript;charset=UTF-8"
                    ))
                    .body(errorScript);
        }
    }

    /**
     * Saves the selected task privileges for a user.
     */
    @PostMapping("/approve/saveModifPriv")
    public String saveModifPriv(
            HttpServletRequest request,
            HttpSession session,
            Model model) {

        session.removeAttribute("error");

        try {
            if (!hasPrivilegeAccess(session)) {
                return unauthorized(model);
            }

            String idUserParameter =
                    request.getParameter("iduser");

            if (idUserParameter == null
                    || idUserParameter.isBlank()) {
                throw new IllegalArgumentException(
                        "User ID is required."
                );
            }

            Long idUser = Long.valueOf(idUserParameter);

            @SuppressWarnings("unchecked")
            List<Tache> tasks =
                    (List<Tache>) session.getAttribute("list_tache");

            if (tasks == null) {
                throw new IllegalStateException(
                        "The task list is missing from the session."
                );
            }

            Set<Integer> selectedTaskIds = new HashSet<>();

            for (Tache task : tasks) {

                String parameterName = "ck" + task.getId();

                if (request.getParameter(parameterName) != null) {
                    selectedTaskIds.add(task.getId());
                }
            }

            int result =
                    manager.updatePrivTache(idUser, selectedTaskIds);

            if (result == 1) {
                return "home";
            }

            return "home";

        } catch (Exception e) {
            e.printStackTrace();

            session.setAttribute("error", "err.java");

            return "home";
        }
    }

    /**
     * Checks the ADMIN role and module 402 access.
     */
    private boolean hasPrivilegeAccess(HttpSession session) {

        Object connectedObject =
                session.getAttribute("USER_CONNECTED");

        Object modulesObject =
                session.getAttribute("USER_MODULES");

        if (!(connectedObject instanceof User connectedUser)) {
            return false;
        }

        if (!(modulesObject instanceof List<?> modules)) {
            return false;
        }

        boolean isAdmin = connectedUser.getAuthorities()
                .stream()
                .anyMatch(authority -> {
                    String role = authority.getAuthority();

                    return "ADMIN".equals(role)
                            || "ROLE_ADMIN".equals(role);
                });

        boolean hasModule = modules.contains("402");

        return isAdmin && hasModule;
    }

    /**
     * Handles unauthorized access.
     */
    private String unauthorized(Model model) {

        model.addAttribute("error", "unauthorized");

        return "Error";
    }
}
