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
		entityManagerFactoryRef = "entityManagerFactory",
		basePackages = {"com.atos.security.domain", "com.atos.security.security",
				"com.atos.security.users", "com.atos.security.technical"}
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
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource mainDataSource(@Qualifier("maindataSourceProperties") DataSourceProperties properties) {
		
		return properties.initializeDataSourceBuilder().build();
		
	}

	@Primary
	@Bean(name = "entityManagerFactory")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(
			EntityManagerFactoryBuilder builder, 
			@Qualifier("mainDataSource") DataSource dataSource
			) {
	    Map<String, Object> properties = new HashMap<String, Object>();
	    //properties.put("hibernate.hbm2ddl.auto", "create-drop");
	    properties.put("hibernate.hbm2ddl.auto", "update");

		return builder
				.dataSource(dataSource)
				.packages("com.atos.security.domain", "com.atos.security.security",
						"com.atos.security.users", "com.atos.security.technical")
				.persistenceUnit("main")
	            .properties(properties)
				.build();
	}
	
	@Primary
	@Bean(name = "transactionManager")
	@ConfigurationProperties("spring.jpa")
	public PlatformTransactionManager transactionManager(
			@Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory
			) {
		return new JpaTransactionManager(entityManagerFactory);
	}
	
	@Bean(name = "mainJdbcTemplate")
	 public JdbcTemplate jdbcTemplate(@Qualifier("mainDataSource") DataSource ds) {
	  return new JdbcTemplate(ds);
	 }
}
