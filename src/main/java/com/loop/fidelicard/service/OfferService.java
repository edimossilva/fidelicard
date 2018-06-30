package com.loop.fidelicard.service;

import java.util.List;

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

	public Iterable<Offer> findAll() {
		return offerRepository.findAll();
	}

	public Offer save(OfferDTO offerDTO) {
		Offer offer = new Offer(offerDTO);
		Enterprise enterprise = enterpriseService.findById(offerDTO.getEnterpriseId());
		offer.setEnterprise(enterprise);
		offerRepository.save(offer);
		return offer;
	}

	public Offer findById(Long offerId) {
		return offerRepository.findById(offerId).get();
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
}
