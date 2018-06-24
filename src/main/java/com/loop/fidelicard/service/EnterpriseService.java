package com.loop.fidelicard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loop.fidelicard.dto.EnterpriseDTO;
import com.loop.fidelicard.dto.FullCardDTO;
import com.loop.fidelicard.model.Enterprise;
import com.loop.fidelicard.model.FinalClient;
import com.loop.fidelicard.repository.EnterpriseRepository;

@Service
public class EnterpriseService {
	@Autowired
	private EnterpriseRepository enterpriseRepository;
	@Autowired
	private FinalClientService finalClientService;

	public Iterable<Enterprise> findAll() {
		return enterpriseRepository.findAll();
	}

	public Enterprise save(EnterpriseDTO enterpriseDTO) {
		Enterprise enterprise = new Enterprise(enterpriseDTO);
		enterpriseRepository.save(enterprise);
		return enterprise;
	}

	public Enterprise addFinalClientToEnterprise(FullCardDTO enterpriseFinalClientDTO) {
		FinalClient finalCLient = finalClientService.findById(enterpriseFinalClientDTO.getFinalClientId());
		Enterprise enterprise = enterpriseRepository.findById(enterpriseFinalClientDTO.getOfferId()).get();
		enterprise.addFinalClient(finalCLient);
		enterpriseRepository.save(enterprise);
		return enterprise;
	}

	public Enterprise findById(Long enterpriseId) {
		return enterpriseRepository.findById(enterpriseId).get();
	}
}
