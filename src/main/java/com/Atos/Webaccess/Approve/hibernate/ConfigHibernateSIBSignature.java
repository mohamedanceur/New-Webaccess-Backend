package com.Atos.Webaccess.Approve.hibernate;


import org.hibernate.cfg.Configuration;

import com.Atos.Webaccess.Approve.common.ParamApprove;

public class ConfigHibernateSIBSignature {

    private final ParamApprove param = new ParamApprove();

    public Configuration getConfig(Configuration cfg) {

        int type = param.getTypeBddSIB();
        String typeSIB = param.getTypeSIB();

        if (type == param.TypeSGBD_Interbase) {
            cfg = getConfigInterbase(cfg);
        } else if (type == param.TypeSGBD_PostGreSQL) {
            cfg = getConfigPostGreSQL(cfg);
        } else if (type == param.TypeSGBD_Oracle) {
            cfg = getConfigOracle(cfg);
        } else if (type == param.TypeSGBD_SQLServeur) {
            cfg = getConfigSQLServeur(cfg);
        } else if (type == param.TypeSGBD_Informix) {
            cfg = getConfigInformix(cfg);
        }

        cfg = getConfigGeneral(cfg);

        if ("MoneCheck".equalsIgnoreCase(typeSIB)
                || "BNP".equalsIgnoreCase(typeSIB)
                || "Calyon".equalsIgnoreCase(typeSIB)) {
            cfg.addResource("/../Hibernate_dao/Comptes.hbm.xml");
        }

        return cfg;
    }

    private Configuration configureDatabase(
            Configuration cfg,
            String driver,
            String dialect) {

        cfg.setProperty("hibernate.connection.driver_class", driver);
        cfg.setProperty("hibernate.connection.url",
                param.getUrlConnectionSIBSignature());
        cfg.setProperty("hibernate.connection.username",
                param.getUsernameSIBSignature());
        cfg.setProperty("hibernate.connection.password",
                param.getPasswordSIBSignature());

        cfg.setProperty("hibernate.dialect", dialect);
        cfg.setProperty("dialect", dialect);

        return cfg;
    }

    public Configuration getConfigInterbase(Configuration cfg) {
        return configureDatabase(cfg,
                "interbase.interclient.Driver",
                "org.hibernate.dialect.InterbaseDialect");
    }

    public Configuration getConfigOracle(Configuration cfg) {
        return configureDatabase(cfg,
                "oracle.jdbc.driver.OracleDriver",
                "org.hibernate.dialect.OracleDialect");
    }

    public Configuration getConfigPostGreSQL(Configuration cfg) {
        return configureDatabase(cfg,
                "org.postgresql.Driver",
                "org.hibernate.dialect.PostgreSQLDialect");
    }

    public Configuration getConfigSQLServeur(Configuration cfg) {
        cfg.setProperty("hibernate.connection.driver_class",
                "com.microsoft.sqlserver.jdbc.SQLServerDriver");
        cfg.setProperty("hibernate.connection.url",
                param.getUrlConnectionSIBSignature());
        cfg.setProperty("hibernate.connection.username",
                param.getUsernameSIBSignature());
        cfg.setProperty("hibernate.connection.password",
                param.getPasswordSIBSignature());

        // Preserve the legacy SQL Server dialect property.
        cfg.setProperty("dialect",
                "org.hibernate.dialect.SQLServerDialect");

        return cfg;
    }

    public Configuration getConfigInformix(Configuration cfg) {
        return configureDatabase(cfg,
                "com.informix.jdbc.IfxDriver",
                "org.hibernate.dialect.InformixDialect");
    }

    public Configuration getConfigGeneral(Configuration cfg) {
        cfg.setProperty("hibernate.connection.pool_size", "10");
        cfg.setProperty("hibernate.show_sql", "true");
        cfg.setProperty("show_sql", "true");
        cfg.setProperty("hibernate.current_session_context_class", "thread");
        cfg.setProperty("current_session_context_class", "thread");
        cfg.setProperty("hibernate.cache.provider_class",
                "org.hibernate.cache.NoCacheProvider");
        cfg.setProperty("cache.provider_class",
                "org.hibernate.cache.NoCacheProvider");

        // Safer for an existing production database.
        cfg.setProperty("hibernate.hbm2ddl.auto", "none");

        return cfg;
    }
}
