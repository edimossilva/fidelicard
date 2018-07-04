package com.loop.fidelicard.service;

import static org.junit.Assert.assertEquals;

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
import com.loop.fidelicard.dto.hybrid.ClientIDAndEnterpriseIdDTO;
import com.loop.fidelicard.mock.MyMock;
import com.loop.fidelicard.model.Card;
import com.loop.fidelicard.security.service.LoginUserService;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class CardServiceTest {
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
	@Autowired
	StampService stampService;

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
	}

	@Test
	public void testCreateCardFromClientIDAndEnterpriseIdDTO() {
		ClientIDAndEnterpriseIdDTO clientIDAndEnterpriseIdDTO = new ClientIDAndEnterpriseIdDTO();
		clientIDAndEnterpriseIdDTO.setFinalClientId(MyMock.getFinalClient().getId());
		clientIDAndEnterpriseIdDTO.setEnterpriseId(MyMock.getEnterprise().getId());

		Card card = cardService.createCardWithStampFromClientIDAndEnterpriseIdDTO(clientIDAndEnterpriseIdDTO);

		ResponseCardDTO expectedResponseCardDTO = new ResponseCardDTO();
		expectedResponseCardDTO.setFinalClientId(MyMock.getFinalClient().getId());
		expectedResponseCardDTO.setId(card.getId());
		expectedResponseCardDTO.setOfferId(MyMock.getOffer().getId());
		expectedResponseCardDTO.setAtualQuantity(1);
		expectedResponseCardDTO.setMaxQuantity(MyMock.getOffer().getQuantity());
		assertEquals(expectedResponseCardDTO, card.toResponseCardDTO());
	}

	@Test
	public void testFindByClientIdAndEnterpriseIdDTOWith2Stamps() {
		ClientIDAndEnterpriseIdDTO clientIDAndEnterpriseIdDTO = new ClientIDAndEnterpriseIdDTO();
		clientIDAndEnterpriseIdDTO.setFinalClientId(MyMock.getFinalClient2().getId());
		clientIDAndEnterpriseIdDTO.setEnterpriseId(MyMock.getEnterprise2().getId());

		Card card = cardService.findByClientIdAndEnterpriseIdDTO(clientIDAndEnterpriseIdDTO);
		stampService.addStampAndSave(card);

		card = cardService.findByClientIdAndEnterpriseIdDTO(clientIDAndEnterpriseIdDTO);

		ResponseCardDTO expectedResponseCardDTO = new ResponseCardDTO();
		expectedResponseCardDTO.setFinalClientId(MyMock.getFinalClient2().getId());
		expectedResponseCardDTO.setId(card.getId());
		expectedResponseCardDTO.setOfferId(MyMock.getOffer2().getId());
		expectedResponseCardDTO.setAtualQuantity(2);
		expectedResponseCardDTO.setMaxQuantity(MyMock.getOffer2().getQuantity());
		assertEquals(expectedResponseCardDTO, card.toResponseCardDTO());
	}

	@Test
	public void testFindByClientIdAndEnterpriseIdDTOWithMaxStamps() {
		ClientIDAndEnterpriseIdDTO clientIDAndEnterpriseIdDTO = new ClientIDAndEnterpriseIdDTO();
		clientIDAndEnterpriseIdDTO.setFinalClientId(MyMock.getFinalClient2().getId());
		clientIDAndEnterpriseIdDTO.setEnterpriseId(MyMock.getEnterprise2().getId());

		Card card = cardService.findByClientIdAndEnterpriseIdDTO(clientIDAndEnterpriseIdDTO);
		for (int i = 1; i < MyMock.getOffer2().getQuantity(); i++) {
			stampService.addStampAndSave(card);
		}

		card = cardService.findByClientIdAndEnterpriseIdDTO(clientIDAndEnterpriseIdDTO);

		ResponseCardDTO expectedResponseCardDTO = new ResponseCardDTO();
		expectedResponseCardDTO.setFinalClientId(MyMock.getFinalClient2().getId());
		expectedResponseCardDTO.setId(card.getId());
		expectedResponseCardDTO.setOfferId(MyMock.getOffer2().getId());
		expectedResponseCardDTO.setAtualQuantity(MyMock.getOffer2().getQuantity());
		expectedResponseCardDTO.setMaxQuantity(MyMock.getOffer2().getQuantity());

		assertEquals(expectedResponseCardDTO, card.toResponseCardDTO());
	}

	@Test
	public void testCleanCard() {
		Card card = MyMock.getCard2();
		stampService.addStampAndSave(card);
		stampService.addStampAndSave(card);

		int stampQuantity = card.getStamps().size();
		int expectedStampQuanity = 3;

		assertEquals(expectedStampQuanity, stampQuantity);

		ClientIDAndEnterpriseIdDTO clientIDAndEnterpriseIdDTO = new ClientIDAndEnterpriseIdDTO();
		clientIDAndEnterpriseIdDTO.setEnterpriseId(MyMock.getEnterprise2().getId());
		clientIDAndEnterpriseIdDTO.setFinalClientId(MyMock.getFinalClient2().getId());
		card = cardService.cleanCard(clientIDAndEnterpriseIdDTO);
		assertEquals(0, card.getStampQuantity());
	}

}
