package com.loop.fidelicard.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.loop.fidelicard.dto.CardDTO;
import com.loop.fidelicard.dto.FinalClientDTO;
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
	public ResponseEntity<FinalClient> save(@RequestBody FinalClientDTO finalClientDTO) {
		FinalClient finalClient = finalClientService.save(finalClientDTO);
		return ResponseEntity.ok(finalClient);
	}

	@RequestMapping(value = "/finalClient/addStamp/{id}", method = POST)
	public ResponseEntity<FinalClient> addStamp(@RequestBody CardDTO cardDTO, @PathVariable("id") long id) {
		FinalClient finalClient = finalClientService.addStamp(id,cardDTO);
		return ResponseEntity.ok(finalClient);
	}
}
