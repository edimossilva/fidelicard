package com.loop.fidelicard.security.controller;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.loop.fidelicard.security.dto.loginuser.LoginUserDTO;
import com.loop.fidelicard.security.model.LoginUser;
import com.loop.fidelicard.security.model.LoginUserService;
import com.loop.fidelicard.util.GenericsUtil;
import com.loop.fidelicard.util.MyLogger;

@RestController
@CrossOrigin(origins = "*")
public class LoginUserController {
	private static final String V1_LOGIN_USER = "/v1/loginUser";

	@Autowired
	private LoginUserService loginUserService;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@SuppressWarnings("rawtypes")
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value = V1_LOGIN_USER, method = POST)
	public ResponseEntity save(@Valid @RequestBody LoginUserDTO dto, BindingResult result) {

		logger.info(MyLogger.getMessage(V1_LOGIN_USER, dto));

		if (result.hasErrors()) {
			logger.error(MyLogger.getErrorMessage(V1_LOGIN_USER, result));

			return GenericsUtil.errorsToResponse(result);
		}

		List<String> errors = loginUserService.errorsToSave(dto);
		if (!errors.isEmpty()) {
			logger.error(MyLogger.getErrorMessageFromList(V1_LOGIN_USER, errors));

			return GenericsUtil.errorsToResponse(errors);
		}

		LoginUser loginUser = loginUserService.save(dto);
		return GenericsUtil.objectToResponse(loginUser.toResponseLoginUserDTO());
	}

}
