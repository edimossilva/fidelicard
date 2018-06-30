package com.loop.fidelicard.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loop.fidelicard.dto.finalclient.FinalClientCreateDTO;
import com.loop.fidelicard.dto.hybrid.ClientUIAndEnterpriseIdDTO;
import com.loop.fidelicard.model.Enterprise;
import com.loop.fidelicard.model.FinalClient;
import com.loop.fidelicard.model.Offer;
import com.loop.fidelicard.repository.FinalClientRepository;

@Service
public class FinalClientService {
	@Autowired
	private FinalClientRepository finalClientRepository;
	@Autowired
	private OfferService offerService;
	@Autowired
	private EnterpriseService enterpriseService;

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
		FinalClient finalClient = new FinalClient(finalClientDTO);
		return finalClientRepository.save(finalClient);
	}

	public FinalClient findByUI(String uniqueIdentifir) {

		return finalClientRepository.findByUniqueIdentifier(uniqueIdentifir);
	}

	public FinalClient existClientByUICardInEnterprise(ClientUIAndEnterpriseIdDTO clientUIAndEnterpriseIdDTO) {
		FinalClient finalClient = findByUI(clientUIAndEnterpriseIdDTO.getFinalClientUI());
		Enterprise enterprise = enterpriseService.findById(clientUIAndEnterpriseIdDTO.getEnterpriseId());
		List<Offer> offers = offerService.findAllByEnterprise(enterprise);
		Offer offer = offerService.findOfferByFinalClient(offers, finalClient);
		if (offer != null) {
			return finalClient;
		}
		return null;
	}

}
