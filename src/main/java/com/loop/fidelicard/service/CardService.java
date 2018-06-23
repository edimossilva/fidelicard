package com.loop.fidelicard.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loop.fidelicard.dto.FinalClientDTO;
import com.loop.fidelicard.model.Card;
import com.loop.fidelicard.model.FinalClient;
import com.loop.fidelicard.repository.CardRepository;

@Service
public class CardService {
	@Autowired
	private CardRepository cardRepository;

	@Autowired
	private FinalClientService finalClientService;

	public Iterable<Card> findAll() {
		return cardRepository.findAll();
	}

	public Card save() {
		return cardRepository.save(new Card());
	}

	public Optional<Card> findById(Long id) {
		return cardRepository.findById(id);
	}

	public Card createCardFromFinalClient(FinalClient finalClient) {
		Card card = new Card();
		card.setFinalClient(finalClient);
		return cardRepository.save(card);
	}

	public Card createCardFromFinalClientDTO(FinalClientDTO finalClientDTO) {
		FinalClient finalClient = finalClientService.getFromDTO(finalClientDTO);
		return createCardFromFinalClient(finalClient);
	}

}
