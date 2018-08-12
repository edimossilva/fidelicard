package com.loop.fidelicard.histories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

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
public class History4 {
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
//		MyMock.createFinalClient2(finalClientService);
		// MyMock.createFinalClient1(finalClientService);
	}

	@Test
	// 4.1 - existe cliente na aplicacao? sim, mas nao nessa empresa
	public void testHistory4Step1() {
		ClientUIAndEnterpriseIdDTO dto = new ClientUIAndEnterpriseIdDTO();
		dto.setFinalClientUI(MyMock.getFinalClient().getUniqueIdentifier());
		dto.setEnterpriseId(MyMock.getEnterprise2().getId());
		ResponseFinalClientDTO responseDTO = finalClientService.findFinalClientResponseDTOByUIAndEnterpriseId(dto);
		assertNull(responseDTO.getCard());
	}

	@Test
	// 4.2 - criar cartao do cliente com primeiro carimbo em outra empresa
	public void testHistory4Step2() {
		FinalClientIdAndEnterpriseIdDTO dto = new FinalClientIdAndEnterpriseIdDTO();
		dto.setFinalClientId(MyMock.getFinalClient().getId());
		dto.setEnterpriseId(MyMock.getEnterprise2().getId());

		ResponseFinalClientDTO createWithStamp = cardService.createWithStamp(dto);
		assertEquals(createWithStamp.getCard().getCurrentStampQuantity().intValue(), 1);
	}

}
