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

import com.loop.fidelicard.dto.enterprise.EnterpriseDTO;
import com.loop.fidelicard.dto.enterprise.ResponseEnterpriseWithLoginUserDTO;
import com.loop.fidelicard.mock.MyMock;
import com.loop.fidelicard.model.Enterprise;
import com.loop.fidelicard.security.dto.LoginUserEmailDTO;
import com.loop.fidelicard.security.model.LoginUser;
import com.loop.fidelicard.security.model.LoginUserService;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)

public class EnterpriseServiceTest {
	@Autowired
	EnterpriseService enterpriseService;
	@Autowired
	LoginUserService loginUserService;
	@Autowired
	OfferService offerService;

	@Before
	public void before() {
		MyMock.createLoginUser1(loginUserService);
		MyMock.createEnterprise1(loginUserService, enterpriseService);
		MyMock.createOffer1(offerService, enterpriseService);
	}

	@After
	public void after() {
		loginUserService.removeCredentials(MyMock.LOGIN_USER_EMAIL_1);
	}

	@Test
	public void testSave() {

		String name = "super acai";
		Long loginUserId = loginUserService.findByEmail(MyMock.LOGIN_USER_EMAIL_1).getId();
		LoginUser loginUser = loginUserService.findById(loginUserId);

		EnterpriseDTO enterpriseDTO = new EnterpriseDTO();
		enterpriseDTO.setEnterpriseName(name);
		enterpriseDTO.setLoginUserId(loginUserId);

		Enterprise enterprise = enterpriseService.save(enterpriseDTO);

		ResponseEnterpriseWithLoginUserDTO expectedResponseEnterpriseDTO = new ResponseEnterpriseWithLoginUserDTO();
		expectedResponseEnterpriseDTO.setId(enterprise.getId());
		expectedResponseEnterpriseDTO.setName(name);
		expectedResponseEnterpriseDTO.setOwnerLoginUser(loginUser.toResponseLoginUserDTO());

		assertEquals(expectedResponseEnterpriseDTO, enterprise.toResponseEnterpriseWithLoginUserDTO());
	}

	@Test
	public void findByOwnerLoginUserEmail() {

		LoginUserEmailDTO loginUserEmailDTO = new LoginUserEmailDTO();
		loginUserEmailDTO.setLoginUserEmail(MyMock.LOGIN_USER_EMAIL_1);

		Enterprise enterprise = enterpriseService.findByOwnerLoginUserEmail(loginUserEmailDTO.getLoginUserEmail());

		ResponseEnterpriseWithLoginUserDTO expectedResponseEnterpriseDTO = new ResponseEnterpriseWithLoginUserDTO();
		expectedResponseEnterpriseDTO.setId(enterprise.getId());
		expectedResponseEnterpriseDTO.setOwnerLoginUser(enterprise.getOwnerLoginUser().toResponseLoginUserDTO());
		expectedResponseEnterpriseDTO.setName(MyMock.ENTERPRISE_NAME);

		assertEquals(expectedResponseEnterpriseDTO, enterprise.toResponseEnterpriseWithLoginUserDTO());
	}

	

}
