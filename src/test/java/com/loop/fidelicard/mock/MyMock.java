package com.loop.fidelicard.mock;

import com.loop.fidelicard.dto.enterprise.EnterpriseDTO;
import com.loop.fidelicard.dto.finalclient.FinalClientAndEnterpriseIdDTO;
import com.loop.fidelicard.dto.finalclient.ResponseFinalClientDTO;
import com.loop.fidelicard.dto.hybrid.FinalClientIdAndEnterpriseIdDTO;
import com.loop.fidelicard.dto.offer.OfferDTO;
import com.loop.fidelicard.model.Enterprise;
import com.loop.fidelicard.model.FinalClient;
import com.loop.fidelicard.model.Offer;
import com.loop.fidelicard.security.model.LoginUser;
import com.loop.fidelicard.security.model.LoginUserService;
import com.loop.fidelicard.security.model.UserRole;
import com.loop.fidelicard.service.CardService;
import com.loop.fidelicard.service.EnterpriseService;
import com.loop.fidelicard.service.FinalClientService;
import com.loop.fidelicard.service.OfferService;

public class MyMock {
	public static final String LOGIN_USER_EMAIL_1 = "testenterprise@gmail.com";
	public static final String LOGIN_USER_EMAIL_2 = "testenterprise2@gmail.com";
	public static final String ENTERPRISE_NAME = "Enterprise Name!";
	public static final String ENTERPRISE_NAME_2 = "Enterprise Name2!";
	public static final String FINAL_USER_EMAIL = "finaluser@gmail.com";
	private static LoginUser loginUser;
	private static LoginUser loginUser2;
	private static Enterprise enterprise;
	private static Enterprise enterprise2;
	private static Offer offer;
	private static Offer offer2;
	private static FinalClient finalClient;
	private static FinalClient finalClient2;

	public static void createCardForClienteInEnterprise2(CardService cardService) {
		FinalClientIdAndEnterpriseIdDTO dto = new FinalClientIdAndEnterpriseIdDTO();
		dto.setFinalClientId(MyMock.getFinalClient().getId());
		dto.setEnterpriseId(MyMock.getEnterprise2().getId());
		cardService.createWithStamp(dto);
	}

	public static void createLoginUser1(LoginUserService loginUserService) {
		String password = "secretword1";
		String email = LOGIN_USER_EMAIL_1;
		LoginUser loginUser = createLoginUser(loginUserService, password, email);
		setLoginUser(loginUser);
	}

	public static void createLoginUser2(LoginUserService loginUserService) {
		String password = "secretword2";
		String email = LOGIN_USER_EMAIL_2;
		LoginUser loginUser = createLoginUser(loginUserService, password, email);
		setLoginUser2(loginUser);
	}

	private static LoginUser createLoginUser(LoginUserService loginUserService, String password, String email) {
		UserRole userRole = UserRole.ROLE_ENTERPRISE;

		LoginUser loginUser = LoginUser.builder().email(email).password(password).userRole(userRole).build();
		return loginUserService.save(loginUser);
	}

	public static void createEnterprise1(LoginUserService loginUserService, EnterpriseService enterpriseService) {
		Long loginUserId = loginUserService.findByEmail(LOGIN_USER_EMAIL_1).getId();

		EnterpriseDTO enterpriseDTO = new EnterpriseDTO();
		enterpriseDTO.setEnterpriseName(ENTERPRISE_NAME);
		enterpriseDTO.setLoginUserId(loginUserId);

		setEnterprise(enterpriseService.save(enterpriseDTO));
	}

	public static void createEnterprise2(LoginUserService loginUserService, EnterpriseService enterpriseService) {
		Long loginUserId = loginUserService.findByEmail(LOGIN_USER_EMAIL_2).getId();

		EnterpriseDTO enterpriseDTO = new EnterpriseDTO();
		enterpriseDTO.setEnterpriseName(ENTERPRISE_NAME_2);
		enterpriseDTO.setLoginUserId(loginUserId);

		setEnterprise2(enterpriseService.save(enterpriseDTO));
	}

	public static void createOffer1(OfferService offerService, EnterpriseService enterpriseService) {
		String name = "compre 8 ganhe 1";
		String description = "na compra de 8 acais o proximo eh de graca :)";
		Integer quantity = 8;
		Enterprise enterprise = enterpriseService.findByOwnerLoginUserEmail(LOGIN_USER_EMAIL_1);
		Long enterpriseId = enterprise.getId();

		OfferDTO offerDTO = new OfferDTO();
		offerDTO.setOfferName(name);
		offerDTO.setOfferDescription(description);
		offerDTO.setOfferQuantity(quantity);
		offerDTO.setEnterpriseId(enterpriseId);

		setOffer(offerService.save(offerDTO));

	}

	public static void createOffer2(OfferService offerService, EnterpriseService enterpriseService) {
		String name = "compre 8 ganhe 12";
		String description = "na compra de 8 acais o proximo eh de graca :)2";
		Integer quantity = 8;
		Enterprise enterprise = enterpriseService.findByOwnerLoginUserEmail(LOGIN_USER_EMAIL_2);
		Long enterpriseId = enterprise.getId();

		OfferDTO offerDTO = new OfferDTO();
		offerDTO.setOfferName(name);
		offerDTO.setOfferDescription(description);
		offerDTO.setOfferQuantity(quantity);
		offerDTO.setEnterpriseId(enterpriseId);

		setOffer2(offerService.save(offerDTO));

	}

	public static void createFinalClient1WithCard(FinalClientService finalClientService) {
		String email = "clientemail@gmail.com";
		String uniqueIdentifier = "123456789";
		FinalClientAndEnterpriseIdDTO dto = new FinalClientAndEnterpriseIdDTO();
		dto.setEnterpriseId(getEnterprise().getId());
		dto.setFinalClientEmail(email);
		dto.setFinalClientUI(uniqueIdentifier);
		ResponseFinalClientDTO responseDTO = finalClientService.createWithStamp(dto);
		FinalClient finalClient = finalClientService.findByEmail(responseDTO.getFinalClientEmail());
		setFinalClient(finalClient);
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

	public static void setFinalClient(FinalClient finalClient) {
		MyMock.finalClient = finalClient;
	}

	public static LoginUser getLoginUser2() {
		return loginUser2;
	}

	public static void setLoginUser2(LoginUser loginUser2) {
		MyMock.loginUser2 = loginUser2;
	}

	public static Enterprise getEnterprise2() {
		return enterprise2;
	}

	public static void setEnterprise2(Enterprise enterprise2) {
		MyMock.enterprise2 = enterprise2;
	}

	public static Offer getOffer2() {
		return offer2;
	}

	public static void setOffer2(Offer offer2) {
		MyMock.offer2 = offer2;
	}

	public static FinalClient getFinalClient2() {
		return finalClient2;
	}

	public static void setFinalClient2(FinalClient finalClient2) {
		MyMock.finalClient2 = finalClient2;
	}

}
