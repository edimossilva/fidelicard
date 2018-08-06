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

import com.loop.fidelicard.dto.offer.OfferDTO;
import com.loop.fidelicard.dto.offer.ResponseOfferDTO;
import com.loop.fidelicard.mock.MyMock;
import com.loop.fidelicard.model.Enterprise;
import com.loop.fidelicard.security.model.LoginUserService;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)

public class OfferServiceTest {
	@Autowired
	LoginUserService loginUserService;
	@Autowired
	EnterpriseService enterpriseService;
	@Autowired
	OfferService offerService;

	@Before
	public void before() {
		MyMock.createLoginUser1(loginUserService);
		MyMock.createEnterprise1(loginUserService, enterpriseService);
	}

	@After
	public void after() {
		loginUserService.removeCredentials(MyMock.LOGIN_USER_EMAIL_1);
	}

	@Test
	public void saveTest() {
		String name = "compre 8 ganhe 1";
		String description = "na compra de 8 acais o proximo eh de graca :)";
		Integer quantity = 8;
		Enterprise enterprise = enterpriseService.findByOwnerLoginUserEmail(MyMock.LOGIN_USER_EMAIL_1);
		Long enterpriseId = enterprise.getId();

		OfferDTO offerDTO = new OfferDTO();
		offerDTO.setOfferName(name);
		offerDTO.setOfferDescription(description);
		offerDTO.setOfferQuantity(quantity);
		offerDTO.setEnterpriseId(enterpriseId);

		ResponseOfferDTO responseOfferDTO = offerService.save(offerDTO).toResponseOfferDTO();

		ResponseOfferDTO expectedResponseEnterpriseDTO = new ResponseOfferDTO();
		expectedResponseEnterpriseDTO.setDescription(description);
		expectedResponseEnterpriseDTO.setId(responseOfferDTO.getId());
		expectedResponseEnterpriseDTO.setName(name);
		expectedResponseEnterpriseDTO.setQuantity(quantity);

		assertEquals(expectedResponseEnterpriseDTO, responseOfferDTO);
	}
}
