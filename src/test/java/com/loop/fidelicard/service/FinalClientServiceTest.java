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

	@Before
	public void before() {
		MyMock.createLoginUser(loginUserService);
		MyMock.createEnterprise(loginUserService, enterpriseService);
		MyMock.createOffer(offerService, enterpriseService);
	}

	@After
	public void after() {
		loginUserService.removeCredentials(MyMock.LOGIN_USER_EMAIL);
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
		// ClientUIAndEnterpriseIdDTO clientUIAndEnterpriseIdDTO = new
		// ClientUIAndEnterpriseIdDTO();
		// clientUIAndEnterpriseIdDTO.setEnterpriseId(MyMock.getEnterprise().getId());
		// clientUIAndEnterpriseIdDTO.setFinalClientUI(MyMock.getLoginUser().getEmail());
		//
		// FinalClient finalClient =
		// finalClientService.findClientByUICardInEnterprise(clientUIAndEnterpriseIdDTO);
		// ResponseFinalClientDTO expectedResponseFinalClientDTO = new
		// ResponseFinalClientDTO();
		// expectedResponseFinalClientDTO.setEmail(email);
		// assertNull(finalClient);
	}
}
