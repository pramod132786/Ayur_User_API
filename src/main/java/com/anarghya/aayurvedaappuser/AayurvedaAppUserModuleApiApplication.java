package com.anarghya.aayurvedaappuser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class AayurvedaAppUserModuleApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(AayurvedaAppUserModuleApiApplication.class, args);
	}

}
