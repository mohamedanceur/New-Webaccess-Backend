
package com.Atos.Webaccess;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;


@SpringBootApplication
@EntityScan(basePackages = {
    "com.Atos.Webaccess.User.Entities",
    "com.Atos.Webaccess.Domain"
})
public class WebaccessApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebaccessApplication.class, args);
    }
}

