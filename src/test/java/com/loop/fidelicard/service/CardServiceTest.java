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

	@Before
	public void before() {
		MyMock.createLoginUser(loginUserService);
		MyMock.createEnterprise(loginUserService, enterpriseService);
		MyMock.createOffer(offerService, enterpriseService);
		MyMock.createFinalClient(finalClientService);

	}

	@Test
	public void testCreateCardFromClientIDAndEnterpriseIdDTO() {
		ClientIDAndEnterpriseIdDTO clientIDAndEnterpriseIdDTO = new ClientIDAndEnterpriseIdDTO();
		clientIDAndEnterpriseIdDTO.setFinalClientId(MyMock.getFinalClient().getId());
		clientIDAndEnterpriseIdDTO.setEnterpriseId(MyMock.getEnterprise().getId());

		Card card = cardService.createCardFromClientIDAndEnterpriseIdDTO(clientIDAndEnterpriseIdDTO);

		ResponseCardDTO expectedResponseCardDTO = new ResponseCardDTO();
		expectedResponseCardDTO.setFinalClientId(MyMock.getFinalClient().getId());
		expectedResponseCardDTO.setId(card.getId());
		expectedResponseCardDTO.setOfferId(MyMock.getOffer().getId());
		expectedResponseCardDTO.setQuantity(1);

		assertEquals(expectedResponseCardDTO, card.toResponseCardDTO());
	}

	@After
	public void after() {
		loginUserService.removeCredentials(MyMock.LOGIN_USER_EMAIL);
	}
}
