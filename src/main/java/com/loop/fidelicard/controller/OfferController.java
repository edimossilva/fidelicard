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

import com.loop.fidelicard.dto.offer.OfferDTO;
import com.loop.fidelicard.model.Offer;
import com.loop.fidelicard.service.OfferService;
import com.loop.fidelicard.util.GenericsUtil;
import com.loop.fidelicard.util.MyLogger;

@RestController
@CrossOrigin(origins = "*")
public class OfferController {
	private static final String V1_OFFER = "/v1/offer";

	@Autowired
	private OfferService offerService;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = V1_OFFER, method = POST)
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	public ResponseEntity save(@Valid @RequestBody OfferDTO dto, BindingResult result) {

		logger.info(MyLogger.getMessage(V1_OFFER, dto));

		if (result.hasErrors()) {
			logger.error(MyLogger.getErrorMessage(V1_OFFER, result));
			return GenericsUtil.errorsToResponse(result);
		}
		
		List<String> errors = offerService.errorsToSave(dto);
		if (!errors.isEmpty()) {
			logger.error(MyLogger.getErrorMessageFromList(V1_OFFER, errors));

			return GenericsUtil.errorsToResponse(errors);
		}
		
		Offer offer = offerService.save(dto);
		return GenericsUtil.objectToResponse(offer.toResponseOfferDTO());
	}
}
