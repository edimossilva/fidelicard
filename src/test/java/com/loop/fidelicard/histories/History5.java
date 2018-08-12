package com.loop.fidelicard.histories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.loop.fidelicard.dto.finalclient.ResponseFinalClientDTO;
import com.loop.fidelicard.dto.hybrid.ClientUIAndEnterpriseIdDTO;
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
public class History5 {
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
	// 5.1 - existe cliente na aplicacao? sim, e tbm nessa empresa
	public void testHistory5Step1() {

		ClientUIAndEnterpriseIdDTO dto2 = new ClientUIAndEnterpriseIdDTO();
		dto2.setFinalClientUI(MyMock.getFinalClient().getUniqueIdentifier());
		dto2.setEnterpriseId(MyMock.getEnterprise().getId());
		ResponseFinalClientDTO responseDTO = finalClientService.findFinalClientResponseDTOByUIAndEnterpriseId(dto2);

		assertNotNull(responseDTO.getCard());
		assertEquals(responseDTO.getCard().getEnterpriseId(), MyMock.getEnterprise().getId());
	}

	@Test
	// 5.2 - carimbar cartao do cliente na empresa
	public void testHistory5Step2() {

		FinalClientIdAndEnterpriseIdDTO dto1 = new FinalClientIdAndEnterpriseIdDTO();
		dto1.setFinalClientId(MyMock.getFinalClient().getId());
		dto1.setEnterpriseId(MyMock.getEnterprise().getId());

		Card card = stampService.addStampAndSave(dto1);
		assertEquals(2, card.getStamps().size());

		card = stampService.addStampAndSave(dto1);
		assertEquals(3, card.getStamps().size());

		card = stampService.addStampAndSave(dto1);
		assertEquals(4, card.getStamps().size());

	}

}
