package com.Atos.Webaccess.User.Entities;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "rights")
@Getter
@Setter
@NoArgsConstructor
public class Right {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_right")
    private Long id;

    @Column(name = "description", nullable = false, length = 255)
    private String description;

    @Column(name = "status")
    private Integer status;

    @Column(name = "value", length = 255)
    private String value;

    // Parent right (source)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source")
    @JsonIgnore
    private Right parent;

    // Child rights
    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Right> children = new HashSet<>();

}