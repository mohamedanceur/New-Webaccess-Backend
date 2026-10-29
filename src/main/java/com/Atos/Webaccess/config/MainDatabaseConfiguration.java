package com.Atos.Webaccess.config;

import java.util.HashMap;
import java.util.Map;

import jakarta.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.autoconfigure.DataSourceProperties;
import org.springframework.boot.jpa.EntityManagerFactoryBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackages = {
        "com.Atos.Webaccess.User.Repositories",
        "com.Atos.Webaccess.security.repositories",
        "com.Atos.Webaccess.Domain"
    },
    entityManagerFactoryRef = "entityManagerFactory",
    transactionManagerRef = "transactionManager"
)
public class MainDatabaseConfiguration {

    @Primary
    @Bean(name = "maindataSourceProperties")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean(name = "mainDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    public DataSource mainDataSource(
            @Qualifier("maindataSourceProperties")
            DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().build();
    }

    @Primary
    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("mainDataSource") DataSource dataSource) {

        Map<String, Object> properties = new HashMap<>();
        // "update" pour ce démarrage : crée la table de AuthToken.
        // Ensuite, passe à "none" pour supprimer les warnings.
        properties.put("hibernate.hbm2ddl.auto", "validate");
        properties.put(
        	    "hibernate.physical_naming_strategy",
        	    "org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl"
        	);

        return builder
                .dataSource(dataSource)
                .packages(
                    "com.Atos.Webaccess.User.Entities",
                    "com.Atos.Webaccess.Domain",
                    "com.Atos.Webaccess.security.entities"   // ajouté
                )
                .persistenceUnit("main")
                .properties(properties)
                .build();
    }

    @Primary
    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("entityManagerFactory")
            EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    @Bean(name = "mainJdbcTemplate")
    public JdbcTemplate jdbcTemplate(
            @Qualifier("mainDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}