package com.Atos.Webaccess.Domain.Agence;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AgenceRequest {

    // For creation, this is the agency-code suffix
    // appended to the selected bank's code.
    private String code;

    private String code_pc;
    private String libelle;
    private Integer id_banque;
}
