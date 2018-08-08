package com.loop.fidelicard.histories;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.loop.fidelicard.dto.finalclient.FinalClientAndEnterpriseIdDTO;
import com.loop.fidelicard.dto.finalclient.ResponseFinalClientDTO;
import com.loop.fidelicard.dto.hybrid.ClientUIAndEnterpriseIdDTO;
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
public class History3 {
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
		// MyMock.createFinalClient1(finalClientService);
	}

	@Test(expected = NullPointerException.class)
	// 3.1 - existe cliente na aplicacao? nao
	public void testFindClientResponseDTOByUIAndEnterpriseIdWhenNotExist() {
		ClientUIAndEnterpriseIdDTO dto = new ClientUIAndEnterpriseIdDTO();
		dto.setFinalClientUI("new UI");
		dto.setEnterpriseId(MyMock.getEnterprise().getId());
		finalClientService.findFinalClientResponseDTOByUIAndEnterpriseId(dto);
	}

	@Test
	// 3.2 - cadastrar cliente com primeiro carimbo
	public void testFindClientResponseDTOByUIAndEnterpriseIdWhenExist() {
		FinalClientAndEnterpriseIdDTO dto = new FinalClientAndEnterpriseIdDTO();
		dto.setEnterpriseId(MyMock.getEnterprise().getId());
		dto.setFinalClientEmail("newEmail");
		dto.setFinalClientUI("someUI");

		ResponseFinalClientDTO expectedDTO = finalClientService.createWithStamp(dto);
		assertEquals(expectedDTO.getCard().getCurrentStampQuantity().intValue(), 1);
	}
}
