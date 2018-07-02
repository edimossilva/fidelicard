package com.loop.fidelicard.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loop.fidelicard.dto.card.CardDTO;
import com.loop.fidelicard.dto.hybrid.ClientIDAndEnterpriseIdDTO;
import com.loop.fidelicard.dto.stamp.StampDTO;
import com.loop.fidelicard.model.Card;
import com.loop.fidelicard.model.Enterprise;
import com.loop.fidelicard.model.FinalClient;
import com.loop.fidelicard.model.Offer;
import com.loop.fidelicard.model.Stamp;
import com.loop.fidelicard.repository.StampRepository;

@Service
public class StampService {
	@Autowired
	private StampRepository stampRepository;
	@Autowired
	private CardService cardService;
	@Autowired
	private FinalClientService finalClientService;
	@Autowired
	private OfferService offerService;
	@Autowired
	private EnterpriseService enterpriseService;

	public Iterable<Stamp> findAll() {
		return stampRepository.findAll();
	}

	public Stamp save(StampDTO stampDTO) {
		Card card = cardService.findById(stampDTO.getCardId()).get();
		Stamp stamp = new Stamp(card);
		stamp.setCard(card);
		stampRepository.save(stamp);
		return stamp;
	}

	public Stamp addNewStamp(Card card) {
		Stamp stamp = new Stamp();
		stamp.setCard(card);
		stamp = stampRepository.save(stamp);
		if (card.getStamps() == null) {
			card.setStamps(new ArrayList<Stamp>());
		}
		card.getStamps().add(stamp);
		return stamp;
	}

	public Card addStamp(CardDTO cardIdDTO) {
		FinalClient finalClient = finalClientService.findById(cardIdDTO.getFinalClientId());
		Offer offer = offerService.findById(cardIdDTO.getOfferId());
		Optional<Card> optionalCard = cardService.findByFinalClientAndOffer(finalClient, offer);
		Card card = null;
		if (!optionalCard.isPresent()) {
			card = cardService.createCardFromCardDTO(cardIdDTO);
		} else {
			card = optionalCard.get();
		}
		addNewStamp(card);
		return cardService.findById(card.getId()).get();
	}

	public Card addStamp(ClientIDAndEnterpriseIdDTO clientIDAndEnterpriseIdDTO) {
		FinalClient finalClient = finalClientService.findById(clientIDAndEnterpriseIdDTO.getFinalClientId());
		Enterprise enterprise = enterpriseService.findById(clientIDAndEnterpriseIdDTO.getEnterpriseId());
		Card card = finalClient.getCardByEnterprise(enterprise);

		addNewStamp(card);
		return cardService.save(card);
	}

}
