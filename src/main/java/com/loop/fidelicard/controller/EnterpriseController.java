package com.loop.fidelicard.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

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
public class EnterpriseController extends RESTController<EnterpriseService> {
	@Autowired
	private EnterpriseService enterpriseService;

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/enterprise", method = GET)
	@Override
	public ResponseEntity index() {
		return super.index();
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/enterprise", method = POST)
	public ResponseEntity save(@RequestBody EnterpriseDTO enterpriseDTO) {
		return super.save(enterpriseDTO);
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/enterprise/addFinalClientToEnterprise", method = POST)
	public ResponseEntity addFinalClientToEnterprise(@RequestBody FinalClientToEnterpriseDTO enterpriseFinalClientDTO) {
		Enterprise enterprise = enterpriseService.addFinalClientToEnterprise(enterpriseFinalClientDTO);
		ResponseEnterpriseDTO responseEnterpriseDTO = new ResponseEnterpriseDTO(enterprise);

		return GenericsUtil.dTOToResponse(responseEnterpriseDTO);
	}

}
