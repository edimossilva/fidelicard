package com.loop.fidelicard.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loop.fidelicard.dto.card.CardDTO;
import com.loop.fidelicard.model.Card;
import com.loop.fidelicard.model.FinalClient;
import com.loop.fidelicard.model.Offer;
import com.loop.fidelicard.repository.CardRepository;

@Service
public class CardService {
	@Autowired
	private CardRepository cardRepository;

	@Autowired
	private FinalClientService finalClientService;
	@Autowired
	private OfferService offerService;

	public Iterable<Card> findAll() {
		return cardRepository.findAll();
	}

	public Card save() {
		return cardRepository.save(new Card());
	}

	public Optional<Card> findById(Long id) {
		return cardRepository.findById(id);
	}

	public Optional<Card> findByFinalClientAndOffer(FinalClient finalClient, Offer offer) {
		return cardRepository.findByFinalClientAndOffer(finalClient, offer);
	}

	public Card createCardFromCardDTO(CardDTO cardDTO) {
		FinalClient finalClient = finalClientService.findById(cardDTO.getFinalClientId());
		Offer offer = offerService.findById(cardDTO.getOfferId());
		Card card = new Card(finalClient, offer);
		return cardRepository.save(card);
	}

}
