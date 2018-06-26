package com.loop.fidelicard.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.loop.fidelicard.dto.CardDTO;
import com.loop.fidelicard.model.Card;
import com.loop.fidelicard.service.CardService;

@RestController
public class CardController {
	@Autowired
	private CardService cardService;


	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@RequestMapping(value = "/card", method = GET)
	public Iterable<Card> index() {
		return cardService.findAll();
	}
	
	@RequestMapping(value = "/card", method = POST)
	public ResponseEntity<Card> save(@RequestBody CardDTO cardDTO) {
		Card card = cardService.createCardFromCardDTO(cardDTO);
		return ResponseEntity.ok(card);
	}
}
