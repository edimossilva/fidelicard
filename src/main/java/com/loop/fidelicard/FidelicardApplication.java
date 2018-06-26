package com.loop.fidelicard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.loop.fidelicard.security.LoginUser;
import com.loop.fidelicard.security.LoginUserRepository;
import com.loop.fidelicard.security.UserRole;

@SpringBootApplication
public class FidelicardApplication {
	@Autowired
	LoginUserRepository loginUserRepository;

	public static void main(String[] args) {
		SpringApplication.run(FidelicardApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner() {
		return args -> {
//			LoginUser loginUser1 = LoginUser.builder().email("edimo@gmail.com").password("123").userRole(UserRole.ADMIN)
//					.build();
//			LoginUser loginUser2 = LoginUser.builder().email("edimo2@gmail.com").password("123")
//					.userRole(UserRole.GUEST).build();
//			loginUserRepository.save(loginUser1);
//			loginUserRepository.save(loginUser2);
//			System.out.println(loginUserRepository.findAll());
		};
	}
}
