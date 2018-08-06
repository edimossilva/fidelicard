package com.loop.fidelicard.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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

import com.loop.fidelicard.dto.card.ResponseCardDTO;
import com.loop.fidelicard.dto.finalclient.FinalClientAndEnterpriseIdDTO;
import com.loop.fidelicard.dto.finalclient.ResponseFinalClientDTO;
import com.loop.fidelicard.dto.hybrid.ClientUIAndEnterpriseIdDTO;
import com.loop.fidelicard.mock.MyMock;
import com.loop.fidelicard.model.Card;
import com.loop.fidelicard.security.model.LoginUserService;

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
		MyMock.createFinalClient1(finalClientService);

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

	// @Test
	// public void testExistClientbyUICardInEnterpriseWHENExist() {
	// ClientUIAndEnterpriseIdDTO clientUIAndEnterpriseIdDTO = new
	// ClientUIAndEnterpriseIdDTO();
	// clientUIAndEnterpriseIdDTO.setEnterpriseId(MyMock.getEnterprise().getId());
	// clientUIAndEnterpriseIdDTO.setFinalClienteUniqueIdentifier("anything");
	//
	// FinalClient finalClient =
	// finalClientService.findClientByUICardInEnterprise(clientUIAndEnterpriseIdDTO);
	//
	// assertNull(finalClient);
	// }

	// @Test
	// public void testExistClientbyUICardInEnterpriseWHENexist() {
	// ClientUIAndEnterpriseIdDTO clientUIAndEnterpriseIdDTO = new
	// ClientUIAndEnterpriseIdDTO();
	// clientUIAndEnterpriseIdDTO.setEnterpriseId(MyMock.getEnterprise2().getId());
	// clientUIAndEnterpriseIdDTO.setFinalClienteUniqueIdentifier(MyMock.getFinalClient2().getUniqueIdentifier());
	//
	// FinalClient finalClient =
	// finalClientService.findClientByUICardInEnterprise(clientUIAndEnterpriseIdDTO);
	//
	// ResponseFinalClientDTO expectedResponseFinalClientDTO = new
	// ResponseFinalClientDTO();
	// expectedResponseFinalClientDTO.setEmail(MyMock.getFinalClient2().getEmail());
	// expectedResponseFinalClientDTO.setId(MyMock.getFinalClient2().getId());
	// expectedResponseFinalClientDTO.setUniqueIdentifier(MyMock.getFinalClient2().getUniqueIdentifier());
	// expectedResponseFinalClientDTO.setCards(MyMock.get);
	// assertEquals(expectedResponseFinalClientDTO,
	// finalClient.toResponseFinalClientDTO());
	// }

	@Test
	public void testFindClientCardByUIAndEnterpriseIdWhenExists() {
		ClientUIAndEnterpriseIdDTO dto = new ClientUIAndEnterpriseIdDTO();
		dto.setEnterpriseId(MyMock.getEnterprise().getId());

		dto.setFinalClientUI(MyMock.getFinalClient().getUniqueIdentifier());
		Card card = finalClientService.findClientCardByUIAndEnterpriseId(dto);
		assertNull(card);
	}

	

	@Test
	public void testCreateWithStampV1() {
		String newClientEmail = "newClient@gmail.com";
		String uniqueIdentifier = "newUniqueIdentifier";

		FinalClientAndEnterpriseIdDTO dto = new FinalClientAndEnterpriseIdDTO();
		dto.setFinalClientEmail(newClientEmail);
		dto.setFinalClientUI(uniqueIdentifier);
		dto.setEnterpriseId(MyMock.getEnterprise2().getId());

		ResponseFinalClientDTO responseFinalClientDTO = finalClientService.createWithStamp(dto);
		ResponseCardDTO card = responseFinalClientDTO.getCard();
		assertNotNull(card);

		assertEquals(1, card.getCurrentStampQuantity().intValue());
		assertNotNull(card.getFinalClientId());
		assertEquals(newClientEmail, responseFinalClientDTO.getFinalClientEmail());
		assertEquals(uniqueIdentifier, responseFinalClientDTO.getFinalClientUI());

	}
}
