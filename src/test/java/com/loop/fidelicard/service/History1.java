package com.loop.fidelicard.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.loop.fidelicard.dto.hybrid.ClientUIAndEnterpriseIdDTO;
import com.loop.fidelicard.mock.MyMock;
import com.loop.fidelicard.security.model.LoginUserService;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class History1 {
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
		MyMock.createLoginUser1(loginUserService);
	}

	@Test(expected = NullPointerException.class)
	public void testFindClientResponseDTOByUIAndEnterpriseIdWhenNotExist() {
		ClientUIAndEnterpriseIdDTO dto = new ClientUIAndEnterpriseIdDTO();
		dto.setFinalClientUI("new UI");
		dto.setEnterpriseId(MyMock.getEnterprise().getId());
		finalClientService.findFinalClientResponseDTOByUIAndEnterpriseId(dto);
	}

	@Test(expected = NullPointerException.class)
	public void testFindClientResponseDTOByUIAndEnterpriseIdWhenExist() {

		// ClientUIAndEnterpriseIdDTO dto = new ClientUIAndEnterpriseIdDTO();
		// dto.setFinalClientUI(MyMock.getFinalClient().getUniqueIdentifier());
		// dto.setEnterpriseId(MyMock.getEnterprise().getId());
		// ResponseFinalClientDTO responseDTO =
		// finalClientService.findFinalClientResponseDTOByUIAndEnterpriseId(dto);
		//
		// assertEquals(MyMock.getFinalClient().to, actual);
	}
}
