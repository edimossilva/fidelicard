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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.loop.fidelicard.dto.card.ResponseCardDTO;
import com.loop.fidelicard.dto.finalclient.ResponseFinalClientDTO;
import com.loop.fidelicard.dto.hybrid.ClientIdAndEnterpriseIdDTO;
import com.loop.fidelicard.dto.hybrid.ClientUIAndEnterpriseIdDTO;
import com.loop.fidelicard.mock.MyMock;
import com.loop.fidelicard.model.Card;
import com.loop.fidelicard.model.Enterprise;
import com.loop.fidelicard.model.FinalClient;
import com.loop.fidelicard.model.Offer;
import com.loop.fidelicard.security.model.LoginUserService;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
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
		ClientIdAndEnterpriseIdDTO clientIDAndEnterpriseIdDTO = new ClientIdAndEnterpriseIdDTO();
		clientIDAndEnterpriseIdDTO.setFinalClientId(MyMock.getFinalClient().getId());
		clientIDAndEnterpriseIdDTO.setEnterpriseId(MyMock.getEnterprise().getId());

		Card card = cardService.createCardWithStampFromClientIDAndEnterpriseIdDTO(clientIDAndEnterpriseIdDTO);

		ResponseCardDTO expectedResponseCardDTO = new ResponseCardDTO();
		// expectedResponseCardDTO.setFinalClient(MyMock.getFinalClient().toResponseFinalClientDTO());
		expectedResponseCardDTO.setCardId(card.getId());
		expectedResponseCardDTO.setOfferId(MyMock.getOffer().getId());
		expectedResponseCardDTO.setAtualStampQuantity(1);
		expectedResponseCardDTO.setMaxStampQuantity(MyMock.getOffer().getQuantity());
		expectedResponseCardDTO.setEnterpriseId(MyMock.getEnterprise().getId());
		expectedResponseCardDTO.setFinalClientId(MyMock.getFinalClient().getId());
		assertEquals(expectedResponseCardDTO, card.toResponseCardDTO());
	}

	@Test
	public void testFindByClientIdAndEnterpriseIdDTOWith2Stamps() {
		ClientIdAndEnterpriseIdDTO clientIDAndEnterpriseIdDTO = new ClientIdAndEnterpriseIdDTO();
		clientIDAndEnterpriseIdDTO.setFinalClientId(MyMock.getFinalClient2().getId());
		clientIDAndEnterpriseIdDTO.setEnterpriseId(MyMock.getEnterprise2().getId());

		Card card = cardService.findByClientIdAndEnterpriseIdDTO(clientIDAndEnterpriseIdDTO);
		stampService.addStampAndSave(card);

		card = cardService.findByClientIdAndEnterpriseIdDTO(clientIDAndEnterpriseIdDTO);

		ResponseCardDTO expectedResponseCardDTO = new ResponseCardDTO();
		// expectedResponseCardDTO.setFinalClient(MyMock.getFinalClient2().toResponseFinalClientDTO());
		expectedResponseCardDTO.setCardId(card.getId());
		expectedResponseCardDTO.setOfferId(MyMock.getOffer2().getId());
		expectedResponseCardDTO.setAtualStampQuantity(2);
		expectedResponseCardDTO.setMaxStampQuantity(MyMock.getOffer2().getQuantity());
		expectedResponseCardDTO.setEnterpriseId(MyMock.getEnterprise2().getId());
		expectedResponseCardDTO.setFinalClientId(MyMock.getFinalClient2().getId());
		assertEquals(expectedResponseCardDTO, card.toResponseCardDTO());
	}

	@Test
	public void testFindByClientIdAndEnterpriseIdDTOWithMaxStamps() {
		ClientIdAndEnterpriseIdDTO clientIDAndEnterpriseIdDTO = new ClientIdAndEnterpriseIdDTO();
		clientIDAndEnterpriseIdDTO.setFinalClientId(MyMock.getFinalClient2().getId());
		clientIDAndEnterpriseIdDTO.setEnterpriseId(MyMock.getEnterprise2().getId());

		Card card = cardService.findByClientIdAndEnterpriseIdDTO(clientIDAndEnterpriseIdDTO);
		for (int i = 1; i < MyMock.getOffer2().getQuantity(); i++) {
			stampService.addStampAndSave(card);
		}

		card = cardService.findByClientIdAndEnterpriseIdDTO(clientIDAndEnterpriseIdDTO);

		ResponseCardDTO expectedResponseCardDTO = new ResponseCardDTO();
		// expectedResponseCardDTO.setFinalClient(MyMock.getFinalClient2().toResponseFinalClientDTO());
		expectedResponseCardDTO.setCardId(card.getId());
		expectedResponseCardDTO.setOfferId(MyMock.getOffer2().getId());
		expectedResponseCardDTO.setAtualStampQuantity(MyMock.getOffer2().getQuantity());
		expectedResponseCardDTO.setMaxStampQuantity(MyMock.getOffer2().getQuantity());
		expectedResponseCardDTO.setEnterpriseId(MyMock.getEnterprise2().getId());
		expectedResponseCardDTO.setFinalClientId(MyMock.getFinalClient2().getId());

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

		ClientIdAndEnterpriseIdDTO clientIDAndEnterpriseIdDTO = new ClientIdAndEnterpriseIdDTO();
		clientIDAndEnterpriseIdDTO.setEnterpriseId(MyMock.getEnterprise2().getId());
		clientIDAndEnterpriseIdDTO.setFinalClientId(MyMock.getFinalClient2().getId());
		card = cardService.cleanCard(clientIDAndEnterpriseIdDTO);
		assertEquals(0, card.getStampQuantity());
	}

	@Test
	public void testSetRewardReceived() {
		Card card = MyMock.getCard2();
		Offer offer = MyMock.getOffer2();
		while (card.getStamps().size() < offer.getQuantity()) {
			stampService.addStampAndSave(card);
		}

		int stampQuantity = card.getStamps().size();
		int expectedStampQuanity = offer.getQuantity();

		assertEquals(expectedStampQuanity, stampQuantity);

		ClientIdAndEnterpriseIdDTO dto = new ClientIdAndEnterpriseIdDTO();
		dto.setEnterpriseId(MyMock.getEnterprise2().getId());
		dto.setFinalClientId(MyMock.getFinalClient2().getId());
		card = cardService.setRewardReceivedCard(dto);
		assertEquals(0, card.getNormalizedQuantity());
	}

	@Test
	public void testCreateWithStamp() {
		Enterprise enterprise = MyMock.getEnterprise();
		FinalClient finalClient2 = MyMock.getFinalClient2();

		ClientUIAndEnterpriseIdDTO dto = new ClientUIAndEnterpriseIdDTO();
		dto.setEnterpriseId(enterprise.getId());
		dto.setFinalClientUI(finalClient2.getUniqueIdentifier());

		ResponseFinalClientDTO responseFinalClientDTO = cardService.createWithStamp(dto);
		assertEquals(responseFinalClientDTO.getCards().get(0).getAtualStampQuantity().intValue(), 1);
	}
}
