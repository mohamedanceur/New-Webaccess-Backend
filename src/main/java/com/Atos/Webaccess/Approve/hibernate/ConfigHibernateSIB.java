package com.Atos.Webaccess.Approve.hibernate;



import org.hibernate.cfg.Configuration;

import com.Atos.Webaccess.Approve.common.ParamApprove;

public class ConfigHibernateSIB {

    private final ParamApprove param = new ParamApprove();

    public Configuration getConfig(Configuration cfg) {

        int type = param.getTypeBddSIB();
        String typeSIB = param.getTypeSIB();

        if (type == param.TypeSGBD_Interbase) {
            getConfigInterbase(cfg);
        } else if (type == param.TypeSGBD_PostGreSQL) {
            getConfigPostGreSQL(cfg);
        } else if (type == param.TypeSGBD_Oracle) {
            getConfigOracle(cfg);
        } else if (type == param.TypeSGBD_SQLServeur) {
            getConfigSQLServeur(cfg);
        } else if (type == param.TypeSGBD_Informix) {
            getConfigInformix(cfg);
        } else {
            throw new IllegalStateException(
                "Unsupported SIB database type: " + type
            );
        }

        getConfigGeneral(cfg);

        if ("MoneCheck".equalsIgnoreCase(typeSIB)
                || "BNP".equalsIgnoreCase(typeSIB)
                || "Calyon".equalsIgnoreCase(typeSIB)) {
            cfg.addResource("/../Hibernate_dao/Comptes.hbm.xml");
        }

        return cfg;
    }

    public Configuration getConfigInterbase(Configuration cfg) {
        return configureDatabase(cfg,
            "interbase.interclient.Driver",
            param.geturlConnectionSIB(),
            param.getusernameSIB(),
            param.getpasswordSIB(),
            "org.hibernate.dialect.InterbaseDialect");
    }

    public Configuration getConfigOracle(Configuration cfg) {
        return configureDatabase(cfg,
            "oracle.jdbc.driver.OracleDriver",
            param.geturlConnectionSIB(),
            param.getusernameSIB(),
            param.getpasswordSIB(),
            "org.hibernate.dialect.OracleDialect");
    }

    public Configuration getConfigPostGreSQL(Configuration cfg) {
        return configureDatabase(cfg,
            "org.postgresql.Driver",
            param.geturlConnectionSIB(),
            param.getusernameSIB(),
            param.getpasswordSIB(),
            "org.hibernate.dialect.PostgreSQLDialect");
    }

    public Configuration getConfigSQLServeur(Configuration cfg) {
        return configureDatabase(cfg,
            "com.microsoft.sqlserver.jdbc.SQLServerDriver",
            param.geturlConnectionSIB(),
            param.getusernameSIB(),
            param.getpasswordSIB(),
            "org.hibernate.dialect.SQLServerDialect");
    }

    public Configuration getConfigInformix(Configuration cfg) {
        return configureDatabase(cfg,
            "com.informix.jdbc.IfxDriver",
            param.geturlConnectionSIB(),
            param.getusernameSIB(),
            param.getpasswordSIB(),
            "org.hibernate.dialect.InformixDialect");
    }

    private Configuration configureDatabase(
            Configuration cfg,
            String driver,
            String url,
            String username,
            String password,
            String dialect) {

        cfg.setProperty("hibernate.connection.driver_class", driver);
        cfg.setProperty("hibernate.connection.url", url);
        cfg.setProperty("hibernate.connection.username", username);
        cfg.setProperty("hibernate.connection.password", password);
        cfg.setProperty("hibernate.dialect", dialect);
        cfg.setProperty("dialect", dialect);

        return cfg;
    }

    public Configuration getConfigGeneral(Configuration cfg) {

        cfg.setProperty("hibernate.connection.pool_size", "10");
        cfg.setProperty("hibernate.show_sql", "true");
        cfg.setProperty("show_sql", "true");
        cfg.setProperty("hibernate.current_session_context_class", "thread");
        cfg.setProperty("current_session_context_class", "thread");
        cfg.setProperty("hibernate.hbm2ddl.auto", "none");

        return cfg;
    }
}
