package com.loop.fidelicard.controller;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.loop.fidelicard.dto.finalclient.ResponseFinalClientDTO;
import com.loop.fidelicard.dto.hybrid.FinalClientIdAndEnterpriseIdDTO;
import com.loop.fidelicard.model.Card;
import com.loop.fidelicard.service.CardService;
import com.loop.fidelicard.util.GenericsUtil;
import com.loop.fidelicard.util.MyLogger;

@RestController
@CrossOrigin(origins = "*")
public class CardController {
	private static final String V1_CARD_GET_REWARD = "/v1/card/getReward";

	private static final String V1_CARD_CREATE_CARD_WITH_STAMP = "/v1/card/createCardWithStamp";

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CardService cardService;


	@SuppressWarnings("rawtypes")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ENTERPRISE')")
	@RequestMapping(value = V1_CARD_CREATE_CARD_WITH_STAMP, method = POST)
	public ResponseEntity createWithStamp(@Valid @RequestBody FinalClientIdAndEnterpriseIdDTO dto,
			BindingResult result) {

		logger.info(MyLogger.getMessage(V1_CARD_CREATE_CARD_WITH_STAMP, dto));

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
	@RequestMapping(value = V1_CARD_GET_REWARD, method = POST)
	public ResponseEntity getReward(@Valid @RequestBody FinalClientIdAndEnterpriseIdDTO dto, BindingResult result) {
		
		logger.info(MyLogger.getMessage(V1_CARD_GET_REWARD, dto));

		if (result.hasErrors()) {
			return GenericsUtil.errorsToResponse(result);
		}

		List<String> errors = cardService.errorsToGetReward(dto);
		if (!errors.isEmpty()) {
			return GenericsUtil.errorsToResponse(errors);
		}

		Card card = cardService.setRewardReceivedCard(dto);

		return GenericsUtil.objectToResponse(new ResponseFinalClientDTO(card));
	}

}
