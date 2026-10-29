package com.Atos.Webaccess.User.Entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "roles")
@Getter
@Setter
public class Role implements GrantedAuthority {

    /**
	 * 
	 */
	private static final long serialVersionUID = -3983363764054651210L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private Long id;

    @Column(name = "role", nullable = false, unique = true)
    private String role;

    
    
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "roles_rights",
        joinColumns = @JoinColumn(
            name = "role_id",
            referencedColumnName = "id"
        ),
        inverseJoinColumns = @JoinColumn(
            name = "right_id",
            referencedColumnName = "id_right"
        )
    )
    private List<Right> rights = new ArrayList<>();

    @Override
    @Transient
    public String getAuthority() {
        return role;
    }
}
