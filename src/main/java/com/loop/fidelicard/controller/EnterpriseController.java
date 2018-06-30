package com.loop.fidelicard.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.loop.fidelicard.dto.enterprise.EnterpriseDTO;
import com.loop.fidelicard.dto.enterprise.ResponseEnterpriseDTO;
import com.loop.fidelicard.dto.finalclient.FinalClientToEnterpriseDTO;
import com.loop.fidelicard.model.Enterprise;
import com.loop.fidelicard.service.EnterpriseService;
import com.loop.fidelicard.util.GenericsUtil;

@RestController
public class EnterpriseController {
	@Autowired
	private EnterpriseService enterpriseService;

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/enterprise", method = GET)
	public ResponseEntity index() {
		List<ResponseEnterpriseDTO> enterpriseDTOList = new ArrayList<ResponseEnterpriseDTO>();
		enterpriseService.findAll().forEach(e -> enterpriseDTOList.add(new ResponseEnterpriseDTO(e)));

		return GenericsUtil.objectToResponse(enterpriseDTOList);
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/enterprise", method = POST)
	public ResponseEntity save(@RequestBody EnterpriseDTO enterpriseDTO) {
		Enterprise enterprise = enterpriseService.save(enterpriseDTO);
		ResponseEnterpriseDTO responseEnterpriseDTO = new ResponseEnterpriseDTO(enterprise);

		return GenericsUtil.objectToResponse(responseEnterpriseDTO);
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/enterprise/addFinalClientToEnterprise", method = POST)
	public ResponseEntity addFinalClientToEnterprise(@RequestBody FinalClientToEnterpriseDTO enterpriseFinalClientDTO) {
		Enterprise enterprise = enterpriseService.addFinalClientToEnterprise(enterpriseFinalClientDTO);
		ResponseEnterpriseDTO responseEnterpriseDTO = new ResponseEnterpriseDTO(enterprise);

		return GenericsUtil.objectToResponse(responseEnterpriseDTO);
	}

}
