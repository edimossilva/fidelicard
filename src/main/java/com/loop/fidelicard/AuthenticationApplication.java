package com.loop.fidelicard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.loop.fidelicard.security.model.LoginUser;
import com.loop.fidelicard.security.model.UserRole;
import com.loop.fidelicard.security.service.LoginUserService;
import com.loop.fidelicard.service.CardService;
import com.loop.fidelicard.service.EnterpriseService;
import com.loop.fidelicard.service.FinalClientService;
import com.loop.fidelicard.service.OfferService;

@SpringBootApplication
@RestController

public class AuthenticationApplication {
	@Autowired
	LoginUserService loginUserService;
	@Autowired
	EnterpriseService enterpriseService;
	@Autowired
	FinalClientService finalClientService;
	@Autowired
	OfferService offerService;
	@Autowired
	CardService cardService;
	public static void main(String[] args) {
		final ApplicationContext ctx = SpringApplication.run(AuthenticationApplication.class, args);

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
			// history29();
			// history3();
			System.out.println(loginUserService.findAll());
		};
	}
//	@Bean
//	public WebMvcConfigurer corsConfigurer() {
//		return new WebMvcConfigurerAdapter() {
//			@Override
//			public void addCorsMappings(CorsRegistry registry) {
//				registry.addMapping("/**").allowedOrigins("http://localhost:3000");
//			}
//		};
//	}

	@GetMapping("/")
	public String home() {
		return "Home Page";
	}
}
