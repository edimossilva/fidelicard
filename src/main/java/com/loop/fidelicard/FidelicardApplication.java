package com.loop.fidelicard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.loop.fidelicard.dto.enterprise.EnterpriseDTO;
import com.loop.fidelicard.dto.offer.OfferDTO;
import com.loop.fidelicard.model.Enterprise;
import com.loop.fidelicard.model.Offer;
import com.loop.fidelicard.security.dto.loginuser.LoginUserDTO;
import com.loop.fidelicard.security.model.LoginUser;
import com.loop.fidelicard.security.model.LoginUserService;
import com.loop.fidelicard.security.model.UserRole;
import com.loop.fidelicard.service.EnterpriseService;
import com.loop.fidelicard.service.OfferService;

@SpringBootApplication
public class FidelicardApplication {
	@Autowired
	LoginUserService loginUserService;
	@Autowired
	EnterpriseService enterpriseService;

	@Autowired
	OfferService offerService;

	public static void main(String[] args) {
		SpringApplication.run(FidelicardApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner() {
		return args -> {
			createAdminUser();
			createEnterprise1();
			createEnterprise2();
			createDefaultFinalUser();

		};
	}

	private void createDefaultFinalUser() {
		LoginUser loginUser = loginUserService.findByEmail("defaultfinaluser@gmail.com");
		if (loginUser == null) {
			LoginUser loginUser1 = LoginUser.builder().email("defaultfinaluser@gmail.com").password("123").userRole(UserRole.ROLE_FINAL_CLIENT)
					.build();
			loginUserService.save(loginUser1);
		}
	}

	private void createEnterprise1() {
		LoginUser loginUser = loginUserService.findByEmail("enterprise@gmail.com");
		Enterprise enterprise = null;
		if (loginUser == null) {
			loginUser = createLoginUser1();
			enterprise = createEnterprise1(loginUser.getId());
			createOffer1(enterprise.getId());
		}

	}

	private void createEnterprise2() {
		LoginUser loginUser = loginUserService.findByEmail("enterprise2@gmail.com");
		Enterprise enterprise = null;
		if (loginUser == null) {
			loginUser = createLoginUser2();
			enterprise = createEnterprise2(loginUser.getId());
			createOffer2(enterprise.getId());
		}

	}

	private LoginUser createLoginUser1() {
		LoginUserDTO loginUserDTO = new LoginUserDTO();
		loginUserDTO.setLoginUserEmail("enterprise@gmail.com");
		loginUserDTO.setLoginUserPassword("123");
		loginUserDTO.setLoginUserRole(UserRole.ROLE_ENTERPRISE);
		return loginUserService.save(loginUserDTO);
	}

	private LoginUser createLoginUser2() {
		LoginUserDTO loginUserDTO = new LoginUserDTO();
		loginUserDTO.setLoginUserEmail("enterprise2@gmail.com");
		loginUserDTO.setLoginUserPassword("123");
		loginUserDTO.setLoginUserRole(UserRole.ROLE_ENTERPRISE);
		return loginUserService.save(loginUserDTO);
	}

	private Enterprise createEnterprise1(Long loginUserId) {
		EnterpriseDTO enterpriseDTO = new EnterpriseDTO();
		enterpriseDTO.setEnterpriseName("enterprise acai");
		enterpriseDTO.setLoginUserId(loginUserId);
		return enterpriseService.save(enterpriseDTO);
	}

	private Enterprise createEnterprise2(Long loginUserId) {
		EnterpriseDTO enterpriseDTO = new EnterpriseDTO();
		enterpriseDTO.setEnterpriseName("enterprise marmita");
		enterpriseDTO.setLoginUserId(loginUserId);
		return enterpriseService.save(enterpriseDTO);
	}

	private Offer createOffer1(Long enterpriseId) {
		OfferDTO offerDTO = new OfferDTO();
		offerDTO.setEnterpriseId(enterpriseId);
		offerDTO.setOfferDescription("compre 5, ganhe 1");
		offerDTO.setOfferName("acai de graca");
		offerDTO.setOfferQuantity(5);
		return offerService.save(offerDTO);
	}

	private Offer createOffer2(Long enterpriseId) {
		OfferDTO offerDTO = new OfferDTO();
		offerDTO.setEnterpriseId(enterpriseId);
		offerDTO.setOfferDescription("compre 10 maLmitas, ganhe 1");
		offerDTO.setOfferName("maLmita de graca");
		offerDTO.setOfferQuantity(10);
		return offerService.save(offerDTO);
	}

	private void createAdminUser() {
		LoginUser loginUser = loginUserService.findByEmail("admin@gmail.com");
		if (loginUser == null) {
			LoginUser loginUser1 = LoginUser.builder().email("admin@gmail.com").password("123").userRole(UserRole.ROLE_ADMIN)
					.build();
			loginUserService.save(loginUser1);
		}
	}

}
