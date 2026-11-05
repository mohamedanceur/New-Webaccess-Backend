package com.Atos.Webaccess.config;


import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;

@Component
public class ApplicationParameters {

    private final ResourceLoader resourceLoader;

    @Value("${app.parameters-file:classpath:config/paramApprove.xml}")
    private String parametersFile;

    @Value("${app.parameters-section:general}")
    private String sectionName;

    private String codeBanque;
    private String codeAgence;

    private String pathImagesOperationsBD;
    private String pathImagesOperations;

    private int existConnexSIB;
    private String nomSIB;
    private String urlConnectionSIB;
    private String usernameSIB;
    private String passwordSIB;

    private String pathsignature;
    private String pathphoto;

    private int deepCheck;
    private String typechequeAD;
    private String typechequeCB;

    private int docubase;
    private String docubase_USER;
    private String docubase_PASSWORD;
    private String docubase_IPSERVER;

    private String typeImage;

    private String urlConnectionSIBSignature;
    private String usernameSIBSignature;
    private String passwordSIBSignature;

    private String carteRetrai;
    private String cartePaiement;
    private String opCarteGAB;
    private String opCarteTPE;
    private String opCarteRemTPE;
    private String paramOpCarteAValider;
    private String paramOpCarteValide;
    private String monCodeAgence;
    private String devise;
    
    public ApplicationParameters(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @PostConstruct
    public void loadParameters() {
        loadSection(sectionName);
    }

    public void loadSection(String section) {

        try {
            Resource resource = resourceLoader.getResource(parametersFile);

            DocumentBuilderFactory factory =
                    DocumentBuilderFactory.newInstance();

            factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            factory.setFeature(
                    "http://apache.org/xml/features/disallow-doctype-decl",
                    true
            );
            factory.setFeature(
                    "http://xml.org/sax/features/external-general-entities",
                    false
            );
            factory.setFeature(
                    "http://xml.org/sax/features/external-parameter-entities",
                    false
            );

            factory.setXIncludeAware(false);
            factory.setExpandEntityReferences(false);

            factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
            factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");

            try (InputStream inputStream = resource.getInputStream()) {

                Document document = factory
                        .newDocumentBuilder()
                        .parse(inputStream);

                Element root = document.getDocumentElement();

                Element config = getDirectChild(root, section);

                if (config == null) {
                    throw new IllegalStateException(
                            "Configuration section not found: " + section
                    );
                }

                loadValues(config);
            }

        } catch (Exception e) {
            throw new IllegalStateException(
                    "Unable to load application parameters from: "
                            + parametersFile,
                    e
            );
        }
    }

    private void loadValues(Element config) {

        codeBanque = getText(config, "CodeBanque", "");
        codeAgence = getText(config, "CodeAgence", "");

        pathImagesOperationsBD =
                getText(config, "PathImagesOperationsBD", "");

        pathImagesOperations =
                getText(config, "PathImagesOperations", "");

        existConnexSIB = getInteger(config, "ExistConnexSIB", 0);

        nomSIB = getText(config, "NomSIB", "");
        urlConnectionSIB = getText(config, "urlConnectionSIB", "");
        usernameSIB = getText(config, "usernameSIB", "");
        passwordSIB = getText(config, "passwordSIB", "");

        pathsignature = getText(config, "pathsignature", "");
        pathphoto = getText(config, "pathphoto", "");

        deepCheck = getInteger(config, "deepCheck", 0);

        typechequeAD = getText(config, "typechequeAD", "");
        typechequeCB = getText(config, "typechequeCB", "");

        typeImage = getText(config, "typeImage", "");

        if (!typeImage.isEmpty()) {
            typeImage = typeImage.equalsIgnoreCase("BLOB")
                    ? "BLOB"
                    : "FICHIER";
        }

        docubase = getInteger(config, "Docubase", 0);

        if (docubase == 1) {
            docubase_USER = getText(config, "docubase_USER", "");
            docubase_PASSWORD = getText(config, "docubase_PASSWORD", "");
            docubase_IPSERVER = getText(config, "docubase_IPSERVER", "");
        } else {
            docubase_USER = "";
            docubase_PASSWORD = "";
            docubase_IPSERVER = "";
        }

        urlConnectionSIBSignature =
                getText(config, "urlConnectionSIBSignature", "");

        usernameSIBSignature =
                getText(config, "usernameSIBSignature", "");

        passwordSIBSignature =
                getText(config, "passwordSIBSignature", "");
    }

    private Element getDirectChild(Element parent, String name) {

        NodeList children = parent.getChildNodes();

        for (int i = 0; i < children.getLength(); i++) {

            Node node = children.item(i);

            if (node instanceof Element
                    && node.getNodeName().equals(name)) {
                return (Element) node;
            }
        }

        return null;
    }

    private String getText(Element parent, String name, String defaultValue) {

        Element element = getDirectChild(parent, name);

        return element == null
                ? defaultValue
                : element.getTextContent().trim();
    }

    private int getInteger(Element parent, String name, int defaultValue) {

        String value = getText(parent, name, "");

        return value.isEmpty()
                ? defaultValue
                : Integer.parseInt(value);
    }

    // Getters

    public String getCodeBanque() {
        return codeBanque;
    }

    public String getCodeAgence() {
        return codeAgence;
    }

    public String getPathImagesOperationsBD() {
        return pathImagesOperationsBD;
    }

    public String getPathImagesOperations() {
        return pathImagesOperations;
    }

    public int getExistConnexSIB() {
        return existConnexSIB;
    }

    public String getNomSIB() {
        return nomSIB;
    }

    public String getUrlConnectionSIB() {
        return urlConnectionSIB;
    }

    public String getUsernameSIB() {
        return usernameSIB;
    }

    public String getPasswordSIB() {
        return passwordSIB;
    }

    public String getPathsignature() {
        return pathsignature;
    }

    public String getPathphoto() {
        return pathphoto;
    }

    public int getDeepCheck() {
        return deepCheck;
    }

    public String getTypechequeAD() {
        return typechequeAD;
    }

    public String getTypechequeCB() {
        return typechequeCB;
    }

    public int getDocubase() {
        return docubase;
    }

    public String getDocubase_USER() {
        return docubase_USER;
    }

    public String getDocubase_PASSWORD() {
        return docubase_PASSWORD;
    }

    public String getDocubase_IPSERVER() {
        return docubase_IPSERVER;
    }

    public String getTypeImage() {
        return typeImage;
    }

    public String getUrlConnectionSIBSignature() {
        return urlConnectionSIBSignature;
    }

    public String getUsernameSIBSignature() {
        return usernameSIBSignature;
    }

    public String getPasswordSIBSignature() {
        return passwordSIBSignature;
    }
    
    public String getCarteRetrai() {
        return carteRetrai;
    }

    public String getCartePaiement() {
        return cartePaiement;
    }

    public String getOpCarteGAB() {
        return opCarteGAB;
    }

    public String getOpCarteTPE() {
        return opCarteTPE;
    }

    public String getOpCarteRemTPE() {
        return opCarteRemTPE;
    }

    public String getParamOpCarteAValider() {
        return paramOpCarteAValider;
    }

    public String getParamOpCarteValide() {
        return paramOpCarteValide;
    }

    public String getMonCodeAgence() {
        return monCodeAgence;
    }

    public String getDevise() {
        return devise;
    }
}