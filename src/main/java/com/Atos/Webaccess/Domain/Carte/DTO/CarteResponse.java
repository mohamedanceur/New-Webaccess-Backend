package com.Atos.Webaccess.Domain.Carte.DTO;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CarteResponse {

    // Operation identifiers
    private Integer idoperation;
    private Integer idopcarte;

    // Card information
    private String numerocarte;

    // Operation information
    private String banquetire;
    private BigDecimal montantoperation;
    private Integer origine;
    private String libelleorigine;
    private String rio;
    private String referenceinterne;

    // Operation type
    private String typeoperation;
    private String libelletypeoperation;

    // Operation state
    private Integer etat;
    private String libelleetat;


    
}


