package com.loop.fidelicard.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.loop.fidelicard.dto.FinalClientDTO;
import com.loop.fidelicard.model.Card;
import com.loop.fidelicard.service.CardService;

@RestController
public class CardController {
	@Autowired
	private CardService cardService;

	@RequestMapping(value = "/card", method = GET)
	public Iterable<Card> index() {
		return cardService.findAll();
	}

	@RequestMapping(value = "/card", method = POST)
	public ResponseEntity<Card> save(@RequestBody FinalClientDTO finalClientDTO) {
		Card card = cardService.createCardFromFinalClientDTO(finalClientDTO);
		return ResponseEntity.ok(card);
	}
}
