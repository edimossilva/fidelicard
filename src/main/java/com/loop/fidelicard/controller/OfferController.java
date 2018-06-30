package com.loop.fidelicard.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.loop.fidelicard.dto.offer.OfferDTO;
import com.loop.fidelicard.dto.offer.ResponseOfferDTO;
import com.loop.fidelicard.model.Offer;
import com.loop.fidelicard.service.OfferService;
import com.loop.fidelicard.util.GenericsUtil;

@RestController
public class OfferController {
	@Autowired
	private OfferService offerService;

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/offer", method = GET)
	public ResponseEntity index() {
		List<ResponseOfferDTO> responseOfferList = new ArrayList<>();
		offerService.findAll().forEach(o -> responseOfferList.add(new ResponseOfferDTO(o)));
		return GenericsUtil.objectToResponse(responseOfferList);
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/offer", method = POST)
	public ResponseEntity save(@RequestBody OfferDTO offerDTO) {
		Offer offer = offerService.save(offerDTO);
		ResponseOfferDTO responseOfferDTO = new ResponseOfferDTO(offer);
		return GenericsUtil.objectToResponse(responseOfferDTO);
	}
}
