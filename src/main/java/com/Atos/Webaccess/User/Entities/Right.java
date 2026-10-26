package com.Atos.Webaccess.User.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "rights")
@Getter
@Setter
public class Right {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_right", nullable = false, unique = true, updatable = false)
    private Long idRight;

    @Column(name = "description", nullable = false, unique = true)
    private String description;

    @Column(name = "value")
    private String value;

    @Column(name = "status")
    private Integer status;
    
    
    
}
