package com.loop.fidelicard.service;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loop.fidelicard.dto.offer.OfferDTO;
import com.loop.fidelicard.model.Card;
import com.loop.fidelicard.model.Enterprise;
import com.loop.fidelicard.model.FinalClient;
import com.loop.fidelicard.model.Offer;
import com.loop.fidelicard.repository.OfferRepository;

@Service
public class OfferService {
	@Autowired
	private OfferRepository offerRepository;
	@Autowired
	private EnterpriseService enterpriseService;
	@Autowired
	private CardService cardService;
	@Autowired
	private ErrorsService eS;

	public Iterable<Offer> findAll() {
		return offerRepository.findAll();
	}

	public Offer save(OfferDTO offerDTO) {

		Enterprise enterprise = enterpriseService.findById(offerDTO.getEnterpriseId());
		List<Offer> offers = findAllByEnterprise(enterprise);
		if (offers.size() > 0) {
			return offers.get(0);
		}
		Offer offer = new Offer(offerDTO);
		offer.setEnterprise(enterprise);
//		enterprise.getOffers().add(offer);
//		enterpriseService.save(enterprise);
		System.out.println(offer);
		offerRepository.save(offer);
		return offer;
	}

	public Offer findById(Long offerId) {
		return offerRepository.findById(offerId);
	}

	public Offer findByDescriprion(String offerDescription) {
		return offerRepository.findByDescription(offerDescription);
	}

	public List<Offer> findAllByEnterprise(Enterprise enterprise) {
		return offerRepository.findAllByEnterprise(enterprise);
	}

	public Offer findOfferByFinalClient(List<Offer> offers, FinalClient finalClient) {
		for (Offer offer : offers) {
			Card card = cardService.findCardByFinalClient(offer.getCards(), finalClient);
			if (card != null) {
				return offer;
			}
		}
		return null;
	}

	public void save(Offer offer) {
		offerRepository.save(offer);
	}

	public List<String> errorsToSave(@Valid OfferDTO offerDTO) {
		List<String> errors = new ArrayList<String>();
		eS.addErrorsIfOfferByDescriptionExist(offerDTO.getDescription(), errors);
		return errors;
	}

}
