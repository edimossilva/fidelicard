package com.loop.fidelicard.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.loop.fidelicard.dto.CardDTO;
import com.loop.fidelicard.dto.StampDTO;
import com.loop.fidelicard.model.Stamp;
import com.loop.fidelicard.service.StampService;

@RestController
public class StampController {
	@Autowired
	private StampService stampService;

	@RequestMapping(value = "/stamp", method = GET)
	public Iterable<Stamp> index() {
		return stampService.findAll();
	}

	@RequestMapping(value = "/stamp", method = POST)
	public ResponseEntity<StampDTO> save(@RequestBody StampDTO stampDTO) {
		stampService.save(stampDTO);
		return ResponseEntity.ok(stampDTO);
	}

	@RequestMapping(value = "/stamp/addStamp/", method = POST)
	public ResponseEntity<Stamp> addStamp(@RequestBody CardDTO cardDTO) {
		Stamp stamp = stampService.addStamp(cardDTO);
		return ResponseEntity.ok(stamp);
	}
}
