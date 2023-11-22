package com.userproductmanagement.letsplay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
public class LetsPlayApplication {

	public static void main(String[] args) {

		SpringApplication.run(LetsPlayApplication.class, args);


	}
}
