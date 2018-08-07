package com.loop.fidelicard.controller;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.loop.fidelicard.dto.finalclient.ResponseFinalClientDTO;
import com.loop.fidelicard.dto.hybrid.ClientUIAndEnterpriseIdDTO;
import com.loop.fidelicard.dto.hybrid.FinalClientIdAndEnterpriseIdDTO;
import com.loop.fidelicard.model.Card;
import com.loop.fidelicard.service.CardService;
import com.loop.fidelicard.util.GenericsUtil;

@RestController
@CrossOrigin(origins = "*")
public class CardController {
	@Autowired
	private CardService cardService;

	@SuppressWarnings("rawtypes")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ENTERPRISE')")
	@RequestMapping(value = "/v1/card/createCardWithStamp", method = POST)
	public ResponseEntity createWithStamp(@Valid @RequestBody ClientUIAndEnterpriseIdDTO dto, BindingResult result) {

		if (result.hasErrors()) {
			return GenericsUtil.errorsToResponse(result);
		}

		List<String> errors = cardService.errorsToCreateWithStamp(dto);
		if (!errors.isEmpty()) {
			return GenericsUtil.errorsToResponse(errors);
		}

		ResponseFinalClientDTO responseFinalClientDTO = cardService.createWithStamp(dto);

		return GenericsUtil.objectToResponse(responseFinalClientDTO);
	}

	@SuppressWarnings("rawtypes")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ENTERPRISE')")
	@RequestMapping(value = "/v1/card/getReward", method = POST)
	public ResponseEntity getReward(@Valid @RequestBody FinalClientIdAndEnterpriseIdDTO dto, BindingResult result) {

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

}
