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

import com.loop.fidelicard.mock.MyMock;
import com.loop.fidelicard.model.Card;
import com.loop.fidelicard.security.service.LoginUserService;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)

public class StampServiceTest {
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
		loginUserService.removeCredentials(MyMock.LOGIN_USER_EMAIL_2);
	}

	@Test
	public void testAddStampAndSave() {
		Card card = MyMock.getCard2();
		int stampQuantity = card.getStamps().size();
		card = stampService.addStampAndSave(card);
		int expectedStampQuanity = cardService.findById(card.getId()).getStamps().size();

		assertEquals(expectedStampQuanity, stampQuantity + 1);

	}
}
