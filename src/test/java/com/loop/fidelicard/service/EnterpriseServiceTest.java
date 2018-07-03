package com.loop.fidelicard.service;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.loop.fidelicard.dto.enterprise.EnterpriseDTO;
import com.loop.fidelicard.dto.enterprise.ResponseEnterpriseDTO;
import com.loop.fidelicard.model.Enterprise;
import com.loop.fidelicard.security.model.LoginUser;
import com.loop.fidelicard.security.model.UserRole;
import com.loop.fidelicard.security.service.LoginUserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EnterpriseServiceTest {
	@Autowired
	EnterpriseService enterpriseService;
	@Autowired
	LoginUserService loginUserService;

	@Before
	public void before() {
		String email = "enterprise@gmail.com";
		String password = "secretword";
		UserRole userRole = UserRole.ENTERPRISE;

		LoginUser loginUser = LoginUser.builder().email(email).password(password).userRole(userRole).build();
		loginUserService.save(loginUser);

	}

	@Test
	public void saveTest() {
		String name = "super acai";
		Long loginUserId = loginUserService.findByEmail("enterprise@gmail.com").getId();
		LoginUser loginUser = loginUserService.findById(loginUserId);

		EnterpriseDTO enterpriseDTO = new EnterpriseDTO();
		enterpriseDTO.setName(name);
		enterpriseDTO.setLoginUserId(loginUserId);

		Enterprise enterprise = enterpriseService.save(enterpriseDTO);

		ResponseEnterpriseDTO expectedResponseEnterpriseDTO = new ResponseEnterpriseDTO(enterprise);
		expectedResponseEnterpriseDTO.setId(enterprise.getId());
		expectedResponseEnterpriseDTO.setName(name);
		expectedResponseEnterpriseDTO.setLoginUser(loginUser.toResponseLoginUserDTO());

		assertEquals(expectedResponseEnterpriseDTO, enterprise.toResponseEnterpriseDTO());
	}
}
