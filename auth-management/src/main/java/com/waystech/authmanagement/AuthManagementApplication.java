package com.waystech.authmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AuthManagementApplication {

	public static void main(String[] args) {

		SpringApplication.run(AuthManagementApplication.class, args);
	}

}
