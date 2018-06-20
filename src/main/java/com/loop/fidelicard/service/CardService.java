package com.loop.fidelicard.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loop.fidelicard.model.Card;
import com.loop.fidelicard.repository.CardRepository;

@Service
public class CardService {
	@Autowired
	private CardRepository cardRepository;

	public Iterable<Card> findAll() {
		return cardRepository.findAll();
	}

	public Card save() {
		return cardRepository.save(new Card());
	}

	public Optional<Card> findById(Long id) {
		return cardRepository.findById(id);
	}

}
