package com.Atos.Webaccess.User.Entities;

import java.util.*;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.Atos.Webaccess.Domain.Tache.Tache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;



@Entity
@Table(name = "users")
@Getter
@Setter
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_generator")
    @SequenceGenerator(
        name = "user_generator",
        sequenceName = "user_seq",
        allocationSize = 1
    )
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "DEFAULTIPADRESS")
    private String defaultipadress;

    @Column(name = "IDBANQUE")
    private long idBanque;

    @Column(name = "IDGROUPE")
    private long idGroupe;

    @Column(name = "DEFAULT_CODE_AGENCE")
    private String default_Code_Agence;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "MOBILE")
    private String mobile;

    @Column(name = "NOM")
    private String nom;

    @Column(name = "PRENOM")
    private String prenom;

    private ArrayList<String> authoritesList;

    @Column(name = "STATUS")
    private int status;

    @Column(name = "FIRST_LOGIN")
    private int first_login;

    @ManyToMany(targetEntity = Role.class, fetch = FetchType.EAGER)
    @JoinTable(
        name = "users_roles",
        joinColumns = @JoinColumn(
            name = "user_id",
            referencedColumnName = "id"
        ),
        inverseJoinColumns = @JoinColumn(
            name = "role_id",
            referencedColumnName = "id"
        )
    )
    private List<GrantedAuthority> authorites = new ArrayList<>();
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "UTILISATEURS_TACHES",
        joinColumns = @JoinColumn(name = "IDUTILISATEUR"),
        inverseJoinColumns = @JoinColumn(name = "IDTACHE")
    )
    private Set<Tache> taches_user = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorites;
    }

    @Override
    @Transient
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @Transient
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @Transient
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @Transient
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "User [id=" + id
                + ", username=" + username
                + ", password=" + password
                + ", defaultipadress=" + defaultipadress
                + ", idBanque=" + idBanque
                + ", idGroupe=" + idGroupe
                + ", default_Code_Agence=" + default_Code_Agence
                + ", email=" + email
                + ", mobile=" + mobile
                + ", nom=" + nom
                + ", prenom=" + prenom
                + ", authoritesList=" + authoritesList
                + ", status=" + status
                + ", first_login=" + first_login
                + ", authorites=" + authorites
                + "]";
    }
}