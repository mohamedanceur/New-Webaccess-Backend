package com.Atos.Webaccess.Domain.Virement.DTO;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VirementResponse {

    private Integer idoperation;
    private String typeoperation;
    private Integer idinstrument;
    private String ribtire;
    private String ribbeneficiaire;
    private String montantoperation;
    private String rio;
    private String numerointerne;
}