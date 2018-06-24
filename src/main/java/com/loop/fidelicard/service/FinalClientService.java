package com.loop.fidelicard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loop.fidelicard.dto.finalclient.FinalClientCreateDTO;
import com.loop.fidelicard.model.FinalClient;
import com.loop.fidelicard.repository.FinalClientRepository;

@Service
public class FinalClientService {
	@Autowired
	private FinalClientRepository finalClientRepository;

	public Iterable<FinalClient> findAll() {
		return finalClientRepository.findAll();
	}

	public FinalClient findById(Long finalClientId) {
		return finalClientRepository.findById(finalClientId).get();
	}

	public FinalClient getFromDTO(FinalClientCreateDTO finalClientDTO) {
		return finalClientRepository.findByUniqueIdentifier(finalClientDTO.getUniqueIdentifier());
	}

	public FinalClient save(FinalClientCreateDTO finalClientDTO) {
		FinalClient finalClient = new FinalClient(finalClientDTO.getUniqueIdentifier());
		return finalClientRepository.save(finalClient);
	}

}
