package com.loop.fidelicard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loop.fidelicard.dto.StampDTO;
import com.loop.fidelicard.model.Card;
import com.loop.fidelicard.model.Stamp;
import com.loop.fidelicard.repository.StampRepository;

@Service
public class StampService {
	@Autowired
	private StampRepository stampRepository;
	@Autowired
	private CardService cardService;

	public Iterable<Stamp> findAll() {
		return stampRepository.findAll();
	}

	public Stamp save(StampDTO stampDTO) {
//		System.out.println(stampDTO);
		Card card = cardService.findById(stampDTO.getCardId()).get();
		Stamp stamp = new Stamp(card);
		stamp.setCard(card);
		stampRepository.save(stamp);
		return stamp;
	}

}
