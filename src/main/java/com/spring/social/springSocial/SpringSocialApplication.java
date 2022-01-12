package com.spring.social.springSocial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

@SpringBootApplication
public class SpringSocialApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSocialApplication.class, args);
	}

}
