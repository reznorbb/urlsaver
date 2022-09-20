package com.rezi.recruitment.urlsaver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
public class UrlSaverApplication {

	public static void main(String[] args) {
		SpringApplication.run(UrlSaverApplication.class, args);
	}

}
