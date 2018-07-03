package com.loop.fidelicard.mock;

import com.loop.fidelicard.dto.enterprise.EnterpriseDTO;
import com.loop.fidelicard.dto.finalclient.FinalClientCreateDTO;
import com.loop.fidelicard.dto.offer.OfferDTO;
import com.loop.fidelicard.model.Enterprise;
import com.loop.fidelicard.model.FinalClient;
import com.loop.fidelicard.model.Offer;
import com.loop.fidelicard.security.model.LoginUser;
import com.loop.fidelicard.security.model.UserRole;
import com.loop.fidelicard.security.service.LoginUserService;
import com.loop.fidelicard.service.EnterpriseService;
import com.loop.fidelicard.service.FinalClientService;
import com.loop.fidelicard.service.OfferService;

public class MyMock {
	public static final String LOGIN_USER_EMAIL = "testenterprise@gmail.com";
	public static final String ENTERPRISE_NAME = "Enterprise Name!";
	public static final String FINAL_USER_EMAIL = "finaluser@gmail.com";
	private static LoginUser loginUser;
	private static Enterprise enterprise;
	private static Offer offer;
	private static FinalClient finalClient;

	public static void createLoginUser(LoginUserService loginUserService) {

		String password = "secretword";
		UserRole userRole = UserRole.ENTERPRISE;

		LoginUser loginUser = LoginUser.builder().email(LOGIN_USER_EMAIL).password(password).userRole(userRole).build();
		MyMock.setLoginUser(loginUserService.save(loginUser));

	}

	public static void createEnterprise(LoginUserService loginUserService, EnterpriseService enterpriseService) {
		Long loginUserId = loginUserService.findByEmail(LOGIN_USER_EMAIL).getId();

		EnterpriseDTO enterpriseDTO = new EnterpriseDTO();
		enterpriseDTO.setName(ENTERPRISE_NAME);
		enterpriseDTO.setLoginUserId(loginUserId);

		setEnterprise(enterpriseService.save(enterpriseDTO));
	}

	public static void createOffer(OfferService offerService, EnterpriseService enterpriseService) {
		String name = "compre 8 ganhe 1";
		String description = "na compra de 8 acais o proximo eh de graca :)";
		Integer quantity = 8;
		Enterprise enterprise = enterpriseService.findByOwnerLoginUserEmail(LOGIN_USER_EMAIL);
		Long enterpriseId = enterprise.getId();

		OfferDTO offerDTO = new OfferDTO();
		offerDTO.setName(name);
		offerDTO.setDescription(description);
		offerDTO.setQuantity(quantity);
		offerDTO.setEnterpriseId(enterpriseId);

		setOffer(offerService.save(offerDTO));

	}
	
	public static void createFinalClient(FinalClientService finalClientService) {
		FinalClientCreateDTO finalClientCreateDTO = new FinalClientCreateDTO();
		String email = "clientemail@gmail.com";
		String uniqueIdentifier = "123456789";

		finalClientCreateDTO.setEmail(email);
		finalClientCreateDTO.setUniqueIdentifier(uniqueIdentifier);

		setFinalClient(finalClientService.save(finalClientCreateDTO));		
	}

	public static LoginUser getLoginUser() {
		return loginUser;
	}

	private static void setLoginUser(LoginUser loginUser) {
		MyMock.loginUser = loginUser;
	}

	public static Enterprise getEnterprise() {
		return enterprise;
	}

	private static void setEnterprise(Enterprise enterprise) {
		MyMock.enterprise = enterprise;
	}

	public static Offer getOffer() {
		return offer;
	}

	private static void setOffer(Offer offer) {
		MyMock.offer = offer;
	}

	public static FinalClient getFinalClient() {
		return finalClient;
	}

	private static void setFinalClient(FinalClient finalClient) {
		MyMock.finalClient = finalClient;
	}

	

}
