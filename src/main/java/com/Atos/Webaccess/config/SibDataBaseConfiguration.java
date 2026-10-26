package com.Atos.Webaccess.config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.autoconfigure.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;


@Configuration
@ComponentScan("com.atos.security.sib")
public class SibDataBaseConfiguration {

	@Bean(name = "sibdataSourceProperties")
	@ConfigurationProperties(prefix = "sib.datasource")
	public DataSourceProperties dataSourceProperties() {		
		return new DataSourceProperties();		
	}
	
	
	@Bean(name = "sibDataSource")
	@ConfigurationProperties(prefix = "sib.datasource")
	public DataSource mainDataSource(@Qualifier("sibdataSourceProperties") DataSourceProperties properties) {		
		return properties.initializeDataSourceBuilder().build();		
	}
	
	@Bean(name = "sibJdbcTemplate")
	 public JdbcTemplate jdbcTemplate1(@Qualifier("sibDataSource") DataSource ds) {
	  return new JdbcTemplate(ds);
	 }


}
