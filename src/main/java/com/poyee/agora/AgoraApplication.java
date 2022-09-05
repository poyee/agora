package com.poyee.agora;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
@EnableJpaAuditing()
public class AgoraApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgoraApplication.class, args);
	}

	@PostConstruct
	public void setTimeZone(){
		// Setting Spring Boot SetTimeZone
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
	}
}
