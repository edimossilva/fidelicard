package com.loop.fidelicard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loop.fidelicard.dto.EnterpriseDTO;
import com.loop.fidelicard.model.Enterprise;
import com.loop.fidelicard.repository.EnterpriseRepository;

@Service
public class EnterpriseService {
	@Autowired
	private EnterpriseRepository enterpriseRepository;

	public Iterable<Enterprise> findAll() {
		return enterpriseRepository.findAll();
	}

	public Enterprise save(EnterpriseDTO enterpriseDTO) {
		Enterprise enterprise = new Enterprise(enterpriseDTO);
		enterpriseRepository.save(enterprise);
		return enterprise;
	}
}
