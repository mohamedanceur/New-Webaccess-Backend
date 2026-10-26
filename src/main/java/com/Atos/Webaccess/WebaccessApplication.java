package com.Atos.Webaccess;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.Atos.Webaccess")
@EntityScan(basePackages = "com.Atos.Webaccess")
public class WebaccessApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebaccessApplication.class, args);
	}

}
