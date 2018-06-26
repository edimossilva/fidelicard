package com.loop.fidelicard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.loop.fidelicard.security.model.LoginUser;
import com.loop.fidelicard.security.model.UserRole;
import com.loop.fidelicard.security.service.LoginUserService;

@SpringBootApplication
public class FidelicardApplication {
	@Autowired
	LoginUserService loginUserService;

	public static void main(String[] args) {
		SpringApplication.run(FidelicardApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner() {
		return args -> {
//			LoginUser loginUser1 = LoginUser.builder().email("admin@gmail.com").password("123").userRole(UserRole.ADMIN)
//					.build();
//			LoginUser loginUser2 = LoginUser.builder().email("finalclient@gmail.com").password("123")
//					.userRole(UserRole.FINAL_CLIENT).build();
//			LoginUser loginUser3 = LoginUser.builder().email("enterprise@gmail.com").password("123")
//					.userRole(UserRole.ENTERPRISE).build();
//			loginUserService.save(loginUser1);
//			loginUserService.save(loginUser2);
//			loginUserService.save(loginUser3);
			System.out.println(loginUserService.findAll());
		};
	}
}
