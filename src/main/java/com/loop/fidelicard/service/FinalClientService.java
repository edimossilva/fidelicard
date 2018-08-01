package com.loop.fidelicard.service;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loop.fidelicard.dto.finalclient.FinalClientAndEnterpriseOwnerEmailDTO;
import com.loop.fidelicard.dto.finalclient.FinalClientCreateDTO;
import com.loop.fidelicard.dto.hybrid.ClientUIAndEnterpriseIdDTO;
import com.loop.fidelicard.dto.hybrid.ClientUIAndEnterpriseOwnerEmailDTO;
import com.loop.fidelicard.model.Card;
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
	@Autowired
	private CardService cardService;
	@Autowired
	private ErrorsService eS;

	public Iterable<FinalClient> findAll() {
		return finalClientRepository.findAll();
	}

	public FinalClient findById(Long finalClientId) {

		return finalClientRepository.findById(finalClientId);
	}

	public FinalClient findByEmail(String email) {
		return finalClientRepository.findByEmail(email);
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

	public FinalClient findClientByUICardInEnterprise(ClientUIAndEnterpriseIdDTO clientUIAndEnterpriseIdDTO) {
		FinalClient finalClient = findByUI(clientUIAndEnterpriseIdDTO.getFinalClientUI());
		Enterprise enterprise = enterpriseService.findById(clientUIAndEnterpriseIdDTO.getEnterpriseId());
		List<Offer> offers = offerService.findAllByEnterprise(enterprise);
		Offer offer = offerService.findOfferByFinalClient(offers, finalClient);
		if (offer != null) {
			return finalClient;
		}
		return null;
	}

	public void save(FinalClient finalClient) {
		finalClientRepository.save(finalClient);
	}

	public Card findClientByUIAndEnterpriseOwnerEmail(
			ClientUIAndEnterpriseOwnerEmailDTO cliUIAndEnterpriseOwnerEmailDTO) {
		FinalClient finalClient = findByUI(cliUIAndEnterpriseOwnerEmailDTO.getFinalClientUI());
		Enterprise enterprise = enterpriseService
				.findByOwnerEmail(cliUIAndEnterpriseOwnerEmailDTO.getEnterpriseOwnerEmail());

		List<Offer> offers = offerService.findAllByEnterprise(enterprise);
		Offer offer = offerService.findOfferByFinalClient(offers, finalClient);
		if (offer != null) {
			return cardService.findByFinalClientAndOffer(finalClient, offer);
		}
		return null;
	}

	public Card createWithStamp(FinalClientAndEnterpriseOwnerEmailDTO finalClientAndEnterpriseOwnerEmailDTO) {
		FinalClient finalClient = new FinalClient();
		finalClient.setEmail(finalClientAndEnterpriseOwnerEmailDTO.getEmail());
		finalClient.setUniqueIdentifier(finalClientAndEnterpriseOwnerEmailDTO.getUniqueIdentifier());
		save(finalClient);
		Enterprise enterprise = enterpriseService
				.findByOwnerEmail(finalClientAndEnterpriseOwnerEmailDTO.getEnterpriseOwnerEmail());
		List<Offer> offers = offerService.findAllByEnterprise(enterprise);
		Offer offer = offers.get(0);
		Card card = cardService.createCardWithStampFromFinalClientAndOffer(finalClient, offer);
		return card;
	}

	public List<String> errorsToExistClientByUIAndEnterpriseOwnerEmail(@Valid ClientUIAndEnterpriseOwnerEmailDTO dto) {
		List<String> errors = new ArrayList<String>();
		eS.addErrosIfFinalClientByUINotExist(dto.getFinalClientUI(), errors);
		eS.addErrosIfEnterprisByOwnerEmailFinalClientNotExist(dto.getEnterpriseOwnerEmail(), errors);

		return errors;
	}

	public List<String> errorsToCreateWithStamp(FinalClientAndEnterpriseOwnerEmailDTO dto) {
		List<String> errors = new ArrayList<String>();

		eS.addErrosIfFinalClientByUIExist(dto.getUniqueIdentifier(), errors);
		eS.addErrosIfFinalClientByEmailExist(dto.getEmail(), errors);
		eS.addErrosIfEnterprisByOwnerEmailFinalClientNotExist(dto.getEnterpriseOwnerEmail(), errors);

		return errors;
	}

}
