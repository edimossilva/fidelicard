package com.loop.fidelicard.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.loop.fidelicard.dto.card.CardDTO;
import com.loop.fidelicard.dto.card.ResponseCardDTO;
import com.loop.fidelicard.dto.hybrid.ClientIDAndEnterpriseIdDTO;
import com.loop.fidelicard.model.Card;
import com.loop.fidelicard.service.CardService;
import com.loop.fidelicard.util.GenericsUtil;

@RestController
public class CardController {
	@Autowired
	private CardService cardService;

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/card", method = GET)
	public ResponseEntity index() {
		List<ResponseCardDTO> responseCardDTOList = new ArrayList<>();
		cardService.findAll().forEach(c -> responseCardDTOList.add(new ResponseCardDTO(c)));

		return GenericsUtil.objectToResponse(responseCardDTOList);
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/card", method = POST)
	public ResponseEntity save(@RequestBody CardDTO cardDTO) {
		Card card = cardService.createCardFromCardDTO(cardDTO);
		ResponseCardDTO responseCardDTO = new ResponseCardDTO(card);

		return GenericsUtil.objectToResponse(responseCardDTO);
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/card/{id}", method = DELETE)
	public ResponseEntity save(@PathVariable("id") Long id) {
		cardService.delete(id);

		return GenericsUtil.objectToResponse("ok");
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/card/createWithStamp/", method = POST)
	public ResponseEntity createWithStamp(@RequestBody ClientIDAndEnterpriseIdDTO clientIDAndEnterpriseIdDTO) {
		Card card = cardService.createCardFromClientIDAndEnterpriseIdDTO(clientIDAndEnterpriseIdDTO);
		ResponseCardDTO responseCardDTO = new ResponseCardDTO(card);

		return GenericsUtil.objectToResponse(responseCardDTO);
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/card/isBeforeLastStamp/", method = POST)
	public ResponseEntity isLastStamp(@RequestBody ClientIDAndEnterpriseIdDTO clientIDAndEnterpriseIdDTO) {
		Boolean isLastStamp = cardService.isBeforeLastStamp(clientIDAndEnterpriseIdDTO);

		return GenericsUtil.objectToResponse(isLastStamp);
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/card/isLastStamp/", method = POST)
	public ResponseEntity isFull(@RequestBody ClientIDAndEnterpriseIdDTO clientIDAndEnterpriseIdDTO) {
		Boolean isFull = cardService.isFull(clientIDAndEnterpriseIdDTO);

		return GenericsUtil.objectToResponse(isFull);
	}
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/card/cleanCard/", method = POST)
	public ResponseEntity cleanCard(@RequestBody ClientIDAndEnterpriseIdDTO clientIDAndEnterpriseIdDTO) {
		Card card = cardService.cleanCard(clientIDAndEnterpriseIdDTO);
		ResponseCardDTO responseCardDTO = new ResponseCardDTO(card);

		return GenericsUtil.objectToResponse(responseCardDTO);
	}
}
