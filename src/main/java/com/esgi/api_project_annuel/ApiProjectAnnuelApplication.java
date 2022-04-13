package com.esgi.api_project_annuel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@ConfigurationPropertiesScan
public class ApiProjectAnnuelApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(ApiProjectAnnuelApplication.class, args);
	}

}
