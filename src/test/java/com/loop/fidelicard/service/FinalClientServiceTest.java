package com.loop.fidelicard.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;

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
import com.loop.fidelicard.dto.finalclient.FinalClientAndEnterpriseOwnerEmailDTO;
import com.loop.fidelicard.dto.finalclient.FinalClientCreateDTO;
import com.loop.fidelicard.dto.finalclient.ResponseFinalClientDTO;
import com.loop.fidelicard.dto.hybrid.ClientUIAndEnterpriseIdDTO;
import com.loop.fidelicard.dto.hybrid.ClientUIAndEnterpriseOwnerEmailDTO;
import com.loop.fidelicard.mock.MyMock;
import com.loop.fidelicard.model.Card;
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

	@Test
	public void testSave() {
		FinalClientCreateDTO finalClientCreateDTO = new FinalClientCreateDTO();
		String email = "newclientemail@gmail.com";
		String uniqueIdentifier = "654321";

		finalClientCreateDTO.setEmail(email);
		finalClientCreateDTO.setUniqueIdentifier(uniqueIdentifier);

		FinalClient finalClient = finalClientService.save(finalClientCreateDTO);

		ResponseFinalClientDTO expectedResponseFinalClientDTO = new ResponseFinalClientDTO();
		expectedResponseFinalClientDTO.setEmail(email);
		expectedResponseFinalClientDTO.setUniqueIdentifier(uniqueIdentifier);
		expectedResponseFinalClientDTO.setId(finalClient.getId());
		expectedResponseFinalClientDTO.setCards(new ArrayList<>());
		assertEquals(expectedResponseFinalClientDTO, finalClient.toResponseFinalClientDTO());
	}

//	@Test
//	public void testExistClientbyUICardInEnterpriseWHENExist() {
//		ClientUIAndEnterpriseIdDTO clientUIAndEnterpriseIdDTO = new ClientUIAndEnterpriseIdDTO();
//		clientUIAndEnterpriseIdDTO.setEnterpriseId(MyMock.getEnterprise().getId());
//		clientUIAndEnterpriseIdDTO.setFinalClienteUniqueIdentifier("anything");
//
//		FinalClient finalClient = finalClientService.findClientByUICardInEnterprise(clientUIAndEnterpriseIdDTO);
//
//		assertNull(finalClient);
//	}

//	@Test
//	public void testExistClientbyUICardInEnterpriseWHENexist() {
//		ClientUIAndEnterpriseIdDTO clientUIAndEnterpriseIdDTO = new ClientUIAndEnterpriseIdDTO();
//		clientUIAndEnterpriseIdDTO.setEnterpriseId(MyMock.getEnterprise2().getId());
//		clientUIAndEnterpriseIdDTO.setFinalClienteUniqueIdentifier(MyMock.getFinalClient2().getUniqueIdentifier());
//
//		FinalClient finalClient = finalClientService.findClientByUICardInEnterprise(clientUIAndEnterpriseIdDTO);
//
//		ResponseFinalClientDTO expectedResponseFinalClientDTO = new ResponseFinalClientDTO();
//		expectedResponseFinalClientDTO.setEmail(MyMock.getFinalClient2().getEmail());
//		expectedResponseFinalClientDTO.setId(MyMock.getFinalClient2().getId());
//		expectedResponseFinalClientDTO.setUniqueIdentifier(MyMock.getFinalClient2().getUniqueIdentifier());
//		expectedResponseFinalClientDTO.setCards(MyMock.get);
//		assertEquals(expectedResponseFinalClientDTO, finalClient.toResponseFinalClientDTO());
//	}

	@Test
	public void testFindClientCardByUIAndEnterpriseIdWhenExists() {
		ClientUIAndEnterpriseIdDTO dto = new ClientUIAndEnterpriseIdDTO();
		dto.setEnterpriseId(MyMock.getEnterprise().getId());

		dto.setFinalClienteUniqueIdentifier(MyMock.getFinalClient().getUniqueIdentifier());
		Card card = finalClientService.findClientCardByUIAndEnterpriseId(dto);
		assertNull(card);
	}

	@Test
	public void testFindClientByUIAndEnterpriseOwnerEmailWhenHasNotOwnerEmailAndUI() {
		ClientUIAndEnterpriseOwnerEmailDTO dto = new ClientUIAndEnterpriseOwnerEmailDTO();
		dto.setEnterpriseOwnerEmail("unknown owner email");
		dto.setFinalClientUI("unknown client UI");
		Card card = finalClientService.findClientByUIAndEnterpriseOwnerEmail(dto);
		assertNull(card);
	}

	@Test
	public void testFindClientByUIAndEnterpriseOwnerEmailWhenHasNotOwnerEmailAndHasUI() {
		ClientUIAndEnterpriseOwnerEmailDTO dto = new ClientUIAndEnterpriseOwnerEmailDTO();
		dto.setEnterpriseOwnerEmail("unknown owner email");
		dto.setFinalClientUI(MyMock.getFinalClient2().getUniqueIdentifier());
		Card card = finalClientService.findClientByUIAndEnterpriseOwnerEmail(dto);
		assertNull(card);
	}

	@Test
	public void testFindClientByUIAndEnterpriseOwnerEmailWhenHasOwnerEmailAndHasNotUI() {
		ClientUIAndEnterpriseOwnerEmailDTO dto = new ClientUIAndEnterpriseOwnerEmailDTO();
		dto.setEnterpriseOwnerEmail(MyMock.getEnterprise2().getOwnerLoginUser().getEmail());
		dto.setFinalClientUI("unknown client UI");
		Card card = finalClientService.findClientByUIAndEnterpriseOwnerEmail(dto);
		assertNull(card);
	}

	@Test
	public void testFindClientByUIAndEnterpriseOwnerEmailWhenHasOwnerEmailAndHasUI() {
		ClientUIAndEnterpriseOwnerEmailDTO dto = new ClientUIAndEnterpriseOwnerEmailDTO();
		dto.setEnterpriseOwnerEmail(MyMock.getEnterprise2().getOwnerLoginUserEmail());
		dto.setFinalClientUI(MyMock.getFinalClient2().getUniqueIdentifier());
		Card card = finalClientService.findClientByUIAndEnterpriseOwnerEmail(dto);
		Card expectedcard = MyMock.getCard2();
		assertEquals(expectedcard, card);
	}

	@Test
	public void testCreateWithStampV0() {
		String newClientEmail = "newClient@gmail.com";
		String uniqueIdentifier = "newUniqueIdentifier";

		FinalClientAndEnterpriseOwnerEmailDTO dto = new FinalClientAndEnterpriseOwnerEmailDTO();
		dto.setEmail(newClientEmail);
		dto.setUniqueIdentifier(uniqueIdentifier);
		dto.setEnterpriseOwnerEmail(MyMock.getEnterprise2().getOwnerLoginUserEmail());

		Card card = finalClientService.createWithStamp(dto);

		assertNotNull(card);

		assertEquals(1, card.getStampQuantity());
		assertNotNull(card.getFinalClient().getId());
		assertEquals(newClientEmail, card.getFinalClient().getEmail());
		assertEquals(uniqueIdentifier, card.getFinalClient().getUniqueIdentifier());

	}

	@Test
	public void testCreateWithStampV1() {
		String newClientEmail = "newClient@gmail.com";
		String uniqueIdentifier = "newUniqueIdentifier";

		FinalClientAndEnterpriseIdDTO dto = new FinalClientAndEnterpriseIdDTO();
		dto.setFinalClientEmail(newClientEmail);
		dto.setFinalClienteUniqueIdentifier(uniqueIdentifier);
		dto.setEnterpriseId(MyMock.getEnterprise2().getId());

		ResponseFinalClientDTO responseFinalClientDTO = finalClientService.createWithStamp(dto);
		ResponseCardDTO card =responseFinalClientDTO.getCards().get(0);
		assertNotNull(card);

		assertEquals(1, card.getAtualStampQuantity().intValue());
		assertNotNull(card.getFinalClientId());
		assertEquals(newClientEmail, responseFinalClientDTO.getEmail());
		assertEquals(uniqueIdentifier, responseFinalClientDTO.getUniqueIdentifier());

	}
}
