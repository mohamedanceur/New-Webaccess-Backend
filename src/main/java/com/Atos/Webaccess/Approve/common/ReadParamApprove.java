package com.Atos.Webaccess.Approve.common;


import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

/**
 * Reads configuration parameters for the Approve module
 * from WEB-INF/paramApprove.xml.
 */
public class ReadParamApprove {

    private static final String CONFIG_FILE =
            "WEB-INF/paramApprove.xml";

    private final ParamApprove param = new ParamApprove();

    /**
     * Legacy entry point retained for existing callers.
     */
    public void ReadParam_gen(String etatName) {
        getParams_gen(etatName);
    }

    /**
     * Loads the XML configuration and reads the requested section.
     *
     * @param etatName XML element containing the configuration
     */
    public void getParams_gen(String etatName) {

        Objects.requireNonNull(etatName, "etatName must not be null");

        File configFile = new File(
                ParamApprove.getReel_path() + CONFIG_FILE
        );

        SAXBuilder saxBuilder = new SAXBuilder();

        final Document document;

        try {
            document = saxBuilder.build(configFile);
        } catch (JDOMException | IOException exception) {
            throw new IllegalStateException(
                    "Unable to load Approve configuration from: "
                            + configFile.getAbsolutePath(),
                    exception
            );
        }

        Element root = document.getRootElement();

        getParamByName_gen(root, etatName);
    }

    /**
     * Extracts configuration values from the requested XML section.
     *
     * @param root XML root element
     * @param etatName requested configuration section
     */
    private void getParamByName_gen(Element root, String etatName) {

        List<Element> configurations = root.getChildren(etatName);

        for (Element configuration : configurations) {

            // Bank and agency
            param.setCodeBanque(
                    getRequiredText(configuration, "CodeBanque")
            );

            param.setCodeAgence(
                    getRequiredText(configuration, "CodeAgence")
            );

            // Image paths
            param.setPathImagesOperationsBD(
                    getRequiredText(configuration, "PathImagesOperationsBD")
            );

            param.setPathImagesOperations(
                    getRequiredText(configuration, "PathImagesOperations")
            );

            // SIB configuration
            param.setExistConnexSIB(
                    Integer.parseInt(
                            getRequiredText(configuration, "ExistConnexSIB")
                    )
            );

            param.setNomSIB(
                    getRequiredText(configuration, "NomSIB")
            );

            param.seturlConnectionSIB(
                    getRequiredText(configuration, "urlConnectionSIB")
            );

            param.setusernameSIB(
                    getRequiredText(configuration, "usernameSIB")
            );

            param.setpasswordSIB(
                    getRequiredText(configuration, "passwordSIB")
            );

            // Signature and image paths
            param.setpathsignature(
                    getRequiredText(configuration, "pathsignature")
            );

            param.setpathphoto(
                    getRequiredText(configuration, "pathphoto")
            );

            // Cheque verification
            param.setDeepCheck(
                    Integer.parseInt(
                            getRequiredText(configuration, "deepCheck")
                    )
            );

            param.setTypechequeAD(
                    getRequiredText(configuration, "typechequeAD")
            );

            param.setTypechequeCB(
                    getRequiredText(configuration, "typechequeCB")
            );

            param.setTypeImage(
                    getRequiredText(configuration, "typeImage")
            );

            // Signature database connection
            param.setUrlConnectionSIBSignature(
                    getRequiredText(configuration, "urlConnectionSIBSignature")
            );

            param.setUsernameSIBSignature(
                    getRequiredText(configuration, "usernameSIBSignature")
            );

            param.setPasswordSIBSignature(
                    getRequiredText(configuration, "passwordSIBSignature")
            );

            
        }
    }



 

    /**
     * Reads a required XML child value.
     *
     * @throws IllegalStateException if the XML element is missing
     */
    private String getRequiredText(Element parent, String elementName) {

        Element child = parent.getChild(elementName);

        if (child == null) {
            throw new IllegalStateException(
                    "Missing XML element: " + elementName
            );
        }

        return child.getTextTrim();
    }
}
