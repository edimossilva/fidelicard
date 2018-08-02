package com.loop.fidelicard.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.loop.fidelicard.dto.enterprise.EnterpriseDTO;
import com.loop.fidelicard.dto.enterprise.ResponseEnterpriseWithLoginUserDTO;
import com.loop.fidelicard.dto.finalclient.FinalClientToEnterpriseDTO;
import com.loop.fidelicard.model.Enterprise;
import com.loop.fidelicard.security.dto.LoginUserEmailDTO;
import com.loop.fidelicard.security.dto.LoginUserIdDTO;
import com.loop.fidelicard.service.EnterpriseService;
import com.loop.fidelicard.util.GenericsUtil;

@RestController
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class EnterpriseController {
	@Autowired
	private EnterpriseService enterpriseService;

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/enterprise", method = GET)
	public ResponseEntity index() {
		List<ResponseEnterpriseWithLoginUserDTO> enterpriseDTOList = new ArrayList<ResponseEnterpriseWithLoginUserDTO>();
		enterpriseService.findAll().forEach(e -> enterpriseDTOList.add(e.toResponseEnterpriseDTO()));

		return GenericsUtil.objectToResponse(enterpriseDTOList);
	}

	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/v1/enterprise", method = POST)
	public ResponseEntity save(@Valid @RequestBody EnterpriseDTO enterpriseDTO, BindingResult result) {
		if (result.hasErrors()) {
			return GenericsUtil.errorsToResponse(result);
		}
		
		List<String> errors = enterpriseService.errorsToSave(enterpriseDTO);
		if(!errors.isEmpty()) {
			return GenericsUtil.errorsToResponse(errors);
		}
		
		Enterprise enterprise = enterpriseService.save(enterpriseDTO);
		
		return GenericsUtil.objectToResponse(enterprise.toResponseEnterpriseDTO());
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/enterprise/addFinalClientToEnterprise", method = POST)
	public ResponseEntity addFinalClientToEnterprise(@RequestBody FinalClientToEnterpriseDTO enterpriseFinalClientDTO) {
		Enterprise enterprise = enterpriseService.addFinalClientToEnterprise(enterpriseFinalClientDTO);
		return GenericsUtil.objectToResponse(enterprise.toResponseEnterpriseDTO());
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/enterprise/findByLoginUserId", method = POST)
	public ResponseEntity findByLoginUserId(@RequestBody LoginUserIdDTO enterpriseFinalClientDTO) {
		Enterprise enterprise = enterpriseService.findByOwnerLoginUserId(enterpriseFinalClientDTO.getLoginUserId());
		if (enterprise != null) {
			return GenericsUtil.objectToResponse(enterprise.toResponseEnterpriseDTO());
		} else {
			String message = "Enterprise not found for loginUser with id = "
					+ enterpriseFinalClientDTO.getLoginUserId();
			return GenericsUtil.objectToResponse(message);
		}
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/enterprise/findByOwnerLoginUserEmail", method = POST)
	public ResponseEntity findByLoginUserId(@RequestBody LoginUserEmailDTO loginUserEmailDTO) {
		Enterprise enterprise = enterpriseService.findByOwnerLoginUserEmail(loginUserEmailDTO.getLoginUserEmail());
		if (enterprise != null) {
			return GenericsUtil.objectToResponse(enterprise.toResponseEnterpriseDTO());
		} else {
			String message = "Enterprise not found for loginUser with email = " + loginUserEmailDTO.getLoginUserEmail();
			return GenericsUtil.objectToResponse(message);
		}
	}
}
