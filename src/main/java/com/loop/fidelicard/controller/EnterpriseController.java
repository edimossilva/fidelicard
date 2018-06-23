package com.loop.fidelicard.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.loop.fidelicard.dto.EnterpriseDTO;
import com.loop.fidelicard.dto.EnterpriseFinalClientDTO;
import com.loop.fidelicard.model.Enterprise;
import com.loop.fidelicard.service.EnterpriseService;

@RestController
public class EnterpriseController {
	@Autowired
	private EnterpriseService enterpriseService;

	@RequestMapping(value = "/enterprise", method = GET)
	public Iterable<Enterprise> index() {
		return enterpriseService.findAll();
	}

	@RequestMapping(value = "/enterprise", method = POST)
	public ResponseEntity<Enterprise> save(@RequestBody EnterpriseDTO enterpriseDTO) {
		Enterprise enterprise = enterpriseService.save(enterpriseDTO);
		return ResponseEntity.ok(enterprise);
	}

	@RequestMapping(value = "/enterprise/addFinalClientToEnterprise", method = POST)
	public ResponseEntity<Enterprise> addFinalClientToEnterprise(
			@RequestBody EnterpriseFinalClientDTO enterpriseFinalClientDTO) {
		Enterprise enterprise = enterpriseService.addFinalClientToEnterprise(enterpriseFinalClientDTO);
		return ResponseEntity.ok(enterprise);
	}
}