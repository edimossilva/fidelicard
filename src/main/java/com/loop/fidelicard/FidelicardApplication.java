package com.loop.fidelicard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

import com.loop.fidelicard.security.model.LoginUser;
import com.loop.fidelicard.security.model.UserRole;
import com.loop.fidelicard.security.service.LoginUserService;

@SpringBootApplication
@RestController

public class FidelicardApplication {
	@Autowired
	LoginUserService loginUserService;

	public static void main(String[] args) {
		SpringApplication.run(FidelicardApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner() {
		return args -> {
			LoginUser loginUser = loginUserService.findByEmail("admin@gmail.com");
			if (loginUser == null) {
				LoginUser loginUser1 = LoginUser.builder().email("admin@gmail.com").password("123")
						.userRole(UserRole.ADMIN).build();
				loginUserService.save(loginUser1);
			}
		};
	}

}
