package com.loop.fidelicard.controller;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.loop.fidelicard.dto.enterprise.EnterpriseDTO;
import com.loop.fidelicard.model.Enterprise;
import com.loop.fidelicard.service.EnterpriseService;
import com.loop.fidelicard.util.GenericsUtil;

@RestController
@CrossOrigin(origins = "*")
public class EnterpriseController {
	@Autowired
	private EnterpriseService enterpriseService;

	@SuppressWarnings("rawtypes")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@RequestMapping(value = "/v1/enterprise", method = POST)
	public ResponseEntity save(@Valid @RequestBody EnterpriseDTO enterpriseDTO, BindingResult result) {
		if (result.hasErrors()) {
			return GenericsUtil.errorsToResponse(result);
		}

		List<String> errors = enterpriseService.errorsToSave(enterpriseDTO);
		if (!errors.isEmpty()) {
			return GenericsUtil.errorsToResponse(errors);
		}

		Enterprise enterprise = enterpriseService.save(enterpriseDTO);

		return GenericsUtil.objectToResponse(enterprise.toResponseEnterpriseWithLoginUserDTO());
	}

}
