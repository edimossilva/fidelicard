package com.loop.fidelicard.controller;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.loop.fidelicard.dto.card.CardDTO;
import com.loop.fidelicard.dto.card.ResponseCardDTO;
import com.loop.fidelicard.dto.hybrid.ClientIdAndEnterpriseIdDTO;
import com.loop.fidelicard.model.Card;
import com.loop.fidelicard.service.CardService;
import com.loop.fidelicard.util.GenericsUtil;

@RestController
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
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
	public ResponseEntity createWithStamp(@RequestBody ClientIdAndEnterpriseIdDTO clientIDAndEnterpriseIdDTO) {
		Card card = cardService.createCardWithStampFromClientIDAndEnterpriseIdDTO(clientIDAndEnterpriseIdDTO);
		ResponseCardDTO responseCardDTO = new ResponseCardDTO(card);

		return GenericsUtil.objectToResponse(responseCardDTO);
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/card/isBeforeLastStamp/", method = POST)
	public ResponseEntity isLastStamp(@RequestBody ClientIdAndEnterpriseIdDTO clientIDAndEnterpriseIdDTO) {
		Boolean isLastStamp = cardService.isBeforeLastStamp(clientIDAndEnterpriseIdDTO);

		return GenericsUtil.objectToResponse(isLastStamp);
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/card/isLastStamp/", method = POST)
	public ResponseEntity isFull(@RequestBody ClientIdAndEnterpriseIdDTO clientIDAndEnterpriseIdDTO) {
		Boolean isFull = cardService.isFull(clientIDAndEnterpriseIdDTO);

		return GenericsUtil.objectToResponse(isFull);
	}

	@SuppressWarnings("rawtypes")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ENTERPRISE')")
	@RequestMapping(value = "/card/cleanCard/", method = POST)
	public ResponseEntity cleanCard(@Valid @RequestBody ClientIdAndEnterpriseIdDTO dto, BindingResult result) {

		if (result.hasErrors()) {
			return GenericsUtil.errorsToResponse(result);
		}

		List<String> errors = cardService.errorsTocleanCard(dto);
		if (!errors.isEmpty()) {
			return GenericsUtil.errorsToResponse(errors);
		}

		Card card = cardService.cleanCard(dto);

		return GenericsUtil.objectToResponse(card.toResponseCardDTO());
	}
	// v1/card/getReward/

	@SuppressWarnings("rawtypes")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ENTERPRISE')")
	@RequestMapping(value = "/v1/card/getReward/", method = POST)
	public ResponseEntity getReward(@Valid @RequestBody ClientIdAndEnterpriseIdDTO dto, BindingResult result) {

		if (result.hasErrors()) {
			return GenericsUtil.errorsToResponse(result);
		}

		List<String> errors = cardService.errorsToGetReward(dto);
		if (!errors.isEmpty()) {
			return GenericsUtil.errorsToResponse(errors);
		}

		Card card = cardService.setRewardReceivedCard(dto);

		return GenericsUtil.objectToResponse(card.toResponseCardDTO());
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/card/cardStatus/", method = POST)
	public ResponseEntity findByClientIdAndEnterpriseIdDTO(
			@RequestBody ClientIdAndEnterpriseIdDTO clientIDAndEnterpriseIdDTO) {
		Card card = cardService.findByClientIdAndEnterpriseIdDTO(clientIDAndEnterpriseIdDTO);

		return GenericsUtil.objectToResponse(card.toResponseCardDTO());
	}

}
