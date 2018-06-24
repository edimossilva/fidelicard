package com.loop.fidelicard.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.loop.fidelicard.dto.finalclient.FinalClientCreateDTO;
import com.loop.fidelicard.model.FinalClient;
import com.loop.fidelicard.service.FinalClientService;

@RestController
public class FinalClientController {
	@Autowired
	private FinalClientService finalClientService;

	@RequestMapping(value = "/finalClient", method = GET)
	public Iterable<FinalClient> index() {
		return finalClientService.findAll();
	}

	@RequestMapping(value = "/finalClient", method = POST)
	public ResponseEntity<FinalClient> save(@RequestBody FinalClientCreateDTO finalClientDTO) {
		FinalClient finalClient = finalClientService.save(finalClientDTO);
		return ResponseEntity.ok(finalClient);
	}

}
