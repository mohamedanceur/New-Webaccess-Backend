package com.Atos.Webaccess.Approve.objects;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class signataireObject {

    private Integer idSignataire;
    private String nomSignataire = "";
    private String prenomSignataire = "";
    private String photoSignataire;
    private List imagesSignature;
    private int debut;
    private int fin;
}