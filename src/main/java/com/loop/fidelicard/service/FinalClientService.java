package com.loop.fidelicard.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loop.fidelicard.dto.CardDTO;
import com.loop.fidelicard.dto.FinalClientDTO;
import com.loop.fidelicard.model.Card;
import com.loop.fidelicard.model.FinalClient;
import com.loop.fidelicard.repository.FinalClientRepository;

@Service
public class FinalClientService {
	@Autowired
	FinalClientRepository finalClientRepository;

	@Autowired
	CardService cardService;

	@Autowired
	StampService stampService;

	public Iterable<FinalClient> findAll() {
		return finalClientRepository.findAll();
	}

	public FinalClient save(FinalClientDTO finalClientDTO) {
		FinalClient finalClient = new FinalClient(finalClientDTO.getUniqueIdentifier());
		return finalClientRepository.save(finalClient);

	}

	public FinalClient addStamp(long id, CardDTO cardDTO) {
		FinalClient finalClient = finalClientRepository.findById(id).get();
		Optional<Card> optionalCard = cardService.findById(cardDTO.getCardId());
		Card card = null;
		if (!optionalCard.isPresent()) {
			card = cardService.addCardToFinalClient(finalClient);
		}else {
			card = optionalCard.get();
		}
		stampService.addStamp(card);
		return finalClient;
	}

}
