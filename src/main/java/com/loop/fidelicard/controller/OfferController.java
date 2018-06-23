package com.loop.fidelicard.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.loop.fidelicard.dto.OfferDTO;
import com.loop.fidelicard.model.Offer;
import com.loop.fidelicard.service.OfferService;

@RestController
public class OfferController {
	@Autowired
	private OfferService offerService;

	@RequestMapping(value = "/offer", method = GET)
	public Iterable<Offer> index() {
		return offerService.findAll();
	}

	@RequestMapping(value = "/offer", method = POST)
	public ResponseEntity<Offer> save(@RequestBody OfferDTO offerDTO) {
		Offer offer = offerService.save(offerDTO);
		return ResponseEntity.ok(offer);
	}
}
