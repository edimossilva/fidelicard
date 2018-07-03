package com.loop.fidelicard.mock;

import com.loop.fidelicard.dto.enterprise.EnterpriseDTO;
import com.loop.fidelicard.dto.offer.OfferDTO;
import com.loop.fidelicard.model.Enterprise;
import com.loop.fidelicard.security.model.LoginUser;
import com.loop.fidelicard.security.model.UserRole;
import com.loop.fidelicard.security.service.LoginUserService;
import com.loop.fidelicard.service.EnterpriseService;
import com.loop.fidelicard.service.OfferService;

public class MyMock {
	public static final String LOGIN_USER_EMAIL = "enterprise@gmail.com";
	public static final String ENTERPRISE_NAME = "Enterprise Name!";

	public static void createLoginUser(LoginUserService loginUserService) {

		String password = "secretword";
		UserRole userRole = UserRole.ENTERPRISE;

		LoginUser loginUser = LoginUser.builder().email(LOGIN_USER_EMAIL).password(password).userRole(userRole).build();
		loginUserService.save(loginUser).getId();

	}

	public static void createEnterprise(LoginUserService loginUserService, EnterpriseService enterpriseService) {
		Long loginUserId = loginUserService.findByEmail(LOGIN_USER_EMAIL).getId();

		EnterpriseDTO enterpriseDTO = new EnterpriseDTO();
		enterpriseDTO.setName(ENTERPRISE_NAME);
		enterpriseDTO.setLoginUserId(loginUserId);

		enterpriseService.save(enterpriseDTO);
	}

	public static void createOffer(OfferService offerService, EnterpriseService enterpriseService) {
		String name = "compre 8 ganhe 1";
		String description = "na compra de 8 acais o proximo eh de graca :)";
		Integer quantity = 8;
		Enterprise enterprise = enterpriseService.findByOwnerLoginUserEmail("enterprise@gmail.com");
		Long enterpriseId = enterprise.getId();

		OfferDTO offerDTO = new OfferDTO();
		offerDTO.setName(name);
		offerDTO.setDescription(description);
		offerDTO.setQuantity(quantity);
		offerDTO.setEnterpriseId(enterpriseId);

		offerService.save(offerDTO).toResponseOfferDTO();

	}
}
