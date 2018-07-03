package com.loop.fidelicard.service;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.loop.fidelicard.dto.enterprise.EnterpriseDTO;
import com.loop.fidelicard.dto.offer.OfferDTO;
import com.loop.fidelicard.dto.offer.ResponseOfferDTO;
import com.loop.fidelicard.model.Enterprise;
import com.loop.fidelicard.security.model.LoginUser;
import com.loop.fidelicard.security.model.UserRole;
import com.loop.fidelicard.security.service.LoginUserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OfferServiceTest {
	@Autowired
	LoginUserService loginUserService;
	@Autowired
	EnterpriseService enterpriseService;
	@Autowired
	OfferService offerService;

	private final String email = "enterprise@gmail.com";

	@Before
	public void before() {
		createLoginUser();
		createEnterprise();
	}

	private void createLoginUser() {
		String password = "secretword";
		UserRole userRole = UserRole.ENTERPRISE;

		LoginUser loginUser = LoginUser.builder().email(email).password(password).userRole(userRole).build();
		System.out.println("ID = " + loginUserService.save(loginUser).getId());

	}

	private void createEnterprise() {
		String name = "super acai";
		Long loginUserId = loginUserService.findByEmail(email).getId();

		EnterpriseDTO enterpriseDTO = new EnterpriseDTO();
		enterpriseDTO.setName(name);
		enterpriseDTO.setLoginUserId(loginUserId);

		enterpriseService.save(enterpriseDTO);
	}

	@Test
	public void saveTest() {
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

		ResponseOfferDTO responseOfferDTO = offerService.save(offerDTO).toResponseOfferDTO();

		ResponseOfferDTO expectedResponseEnterpriseDTO = new ResponseOfferDTO();
		expectedResponseEnterpriseDTO.setDescription(description);
		expectedResponseEnterpriseDTO.setId(responseOfferDTO.getId());
		expectedResponseEnterpriseDTO.setName(name);
		expectedResponseEnterpriseDTO.setQuantity(quantity);

		assertEquals(expectedResponseEnterpriseDTO, responseOfferDTO);
	}
}
