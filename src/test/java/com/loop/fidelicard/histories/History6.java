package com.loop.fidelicard.histories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.loop.fidelicard.dto.hybrid.FinalClientIdAndEnterpriseIdDTO;
import com.loop.fidelicard.mock.MyMock;
import com.loop.fidelicard.model.Card;
import com.loop.fidelicard.security.model.LoginUserService;
import com.loop.fidelicard.service.CardService;
import com.loop.fidelicard.service.EnterpriseService;
import com.loop.fidelicard.service.FinalClientService;
import com.loop.fidelicard.service.OfferService;
import com.loop.fidelicard.service.StampService;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class History6 {
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
		MyMock.createLoginUser2(loginUserService);
		MyMock.createEnterprise1(loginUserService, enterpriseService);
		MyMock.createEnterprise2(loginUserService, enterpriseService);
		MyMock.createOffer1(offerService, enterpriseService);
		MyMock.createOffer2(offerService, enterpriseService);
		MyMock.createFinalClient1WithCard(finalClientService);
	}

	@Test
	// 6.1 - receber premio
	public void testHistory6Step1() {
		addingStampsUntilCardBecomeFull();
		FinalClientIdAndEnterpriseIdDTO dto = new FinalClientIdAndEnterpriseIdDTO();
		dto.setEnterpriseId(MyMock.getEnterprise().getId());
		dto.setFinalClientId(MyMock.getFinalClient().getId());

		Card card = cardService.setRewardReceivedCard(dto);

		assertEquals(0, card.getNormalizedQuantity());
		assertNotEquals(card.getStampQuantity(), card.getNormalizedQuantity());

		assertEquals(true, card.isRewardReceived());
		assertEquals(false, card.isFull());

	}

	private void addingStampsUntilCardBecomeFull() {
		FinalClientIdAndEnterpriseIdDTO dto1 = new FinalClientIdAndEnterpriseIdDTO();
		dto1.setFinalClientId(MyMock.getFinalClient().getId());
		dto1.setEnterpriseId(MyMock.getEnterprise().getId());

		for (int i = 1; i < MyMock.getOffer().getQuantity(); i++) {
			stampService.addStampAndSave(dto1);
		}
	}

}
