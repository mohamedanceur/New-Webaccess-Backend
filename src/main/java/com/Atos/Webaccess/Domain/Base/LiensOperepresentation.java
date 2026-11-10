package com.Atos.Webaccess.Domain.Base;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "LIENS_OPEREPRESENTATION", schema = "dbo")
@Getter
@Setter
@NoArgsConstructor
public class LiensOperepresentation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDOPERATIONREPRESENTEE")
    private Integer idoperationrepresentee;

    @Column(name = "IDOPERATIONREPRESENTATION", nullable = false)
    private Integer idoperationrepresentation;

    @Column(name = "IDOPERATIONREJET", nullable = false)
    private Integer idoperationrejet;

    @Column(name = "STATUT")
    private Integer statut;
}