package com.loop.fidelicard.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;

import com.loop.fidelicard.dto.finalclient.FinalClientCreateDTO;
import com.loop.fidelicard.dto.finalclient.ResponseFinalClientDTO;
import com.loop.fidelicard.dto.hybrid.ClientUIAndEnterpriseIdDTO;
import com.loop.fidelicard.mock.MyMock;
import com.loop.fidelicard.model.FinalClient;
import com.loop.fidelicard.security.service.LoginUserService;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)

public class FinalClientServiceTest {
	@Autowired
	FinalClientService finalClientService;
	@Autowired
	EnterpriseService enterpriseService;
	@Autowired
	LoginUserService loginUserService;
	@Autowired
	OfferService offerService;
	@Autowired
	CardService cardService;

	@Before
	public void before() {
		MyMock.createLoginUser1(loginUserService);
		MyMock.createEnterprise1(loginUserService, enterpriseService);
		MyMock.createOffer1(offerService, enterpriseService);
		
		MyMock.createLoginUser2(loginUserService);
		MyMock.createEnterprise2(loginUserService, enterpriseService);
		MyMock.createOffer2(offerService, enterpriseService);
		MyMock.createFinalClient2(finalClientService);
		MyMock.createCard2(cardService);
	}

	@After
	public void after() {
		loginUserService.removeCredentials(MyMock.LOGIN_USER_EMAIL_1);
		loginUserService.removeCredentials(MyMock.LOGIN_USER_EMAIL_2);
	}

	@Test
	public void save() {
		FinalClientCreateDTO finalClientCreateDTO = new FinalClientCreateDTO();
		String email = "clientemail@gmail.com";
		String uniqueIdentifier = "54321";

		finalClientCreateDTO.setEmail(email);
		finalClientCreateDTO.setUniqueIdentifier(uniqueIdentifier);

		FinalClient finalClient = finalClientService.save(finalClientCreateDTO);

		ResponseFinalClientDTO expectedResponseFinalClientDTO = new ResponseFinalClientDTO();
		expectedResponseFinalClientDTO.setEmail(email);
		expectedResponseFinalClientDTO.setUniqueIdentifier(uniqueIdentifier);
		expectedResponseFinalClientDTO.setId(finalClient.getId());

		assertEquals(expectedResponseFinalClientDTO, finalClient.toResponseFinalClientDTO());
	}

	@Test
	public void existClientbyUICardInEnterpriseWHENnotExist() {
		ClientUIAndEnterpriseIdDTO clientUIAndEnterpriseIdDTO = new ClientUIAndEnterpriseIdDTO();
		clientUIAndEnterpriseIdDTO.setEnterpriseId(MyMock.getEnterprise().getId());
		clientUIAndEnterpriseIdDTO.setFinalClientUI("anything");

		FinalClient finalClient = finalClientService.findClientByUICardInEnterprise(clientUIAndEnterpriseIdDTO);

		assertNull(finalClient);
	}

	@Test
	public void existClientbyUICardInEnterpriseWHENexist() {
		ClientUIAndEnterpriseIdDTO clientUIAndEnterpriseIdDTO = new ClientUIAndEnterpriseIdDTO();
		clientUIAndEnterpriseIdDTO.setEnterpriseId(MyMock.getEnterprise2().getId());
		clientUIAndEnterpriseIdDTO.setFinalClientUI(MyMock.getFinalClient2().getUniqueIdentifier());

		FinalClient finalClient = finalClientService.findClientByUICardInEnterprise(clientUIAndEnterpriseIdDTO);

		ResponseFinalClientDTO expectedResponseFinalClientDTO = new ResponseFinalClientDTO();
		expectedResponseFinalClientDTO.setEmail(MyMock.getFinalClient2().getEmail());
		expectedResponseFinalClientDTO.setId(MyMock.getFinalClient2().getId());
		expectedResponseFinalClientDTO.setUniqueIdentifier(MyMock.getFinalClient2().getUniqueIdentifier());

		assertEquals(expectedResponseFinalClientDTO, finalClient.toResponseFinalClientDTO());
	}
}
