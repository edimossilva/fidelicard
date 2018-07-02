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

import com.loop.fidelicard.dto.card.ResponseCardDTO;
import com.loop.fidelicard.dto.hybrid.ClientIDAndEnterpriseIdDTO;
import com.loop.fidelicard.dto.stamp.ResponseStampDTO;
import com.loop.fidelicard.dto.stamp.StampDTO;
import com.loop.fidelicard.model.Card;
import com.loop.fidelicard.model.Stamp;
import com.loop.fidelicard.service.StampService;
import com.loop.fidelicard.util.GenericsUtil;

@RestController
public class StampController {
	@Autowired
	private StampService stampService;

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/stamp", method = GET)
	public ResponseEntity index() {

		List<ResponseStampDTO> responseStampDTOList = new ArrayList<>();
		stampService.findAll().forEach(s -> responseStampDTOList.add(new ResponseStampDTO(s)));

		return GenericsUtil.objectToResponse(responseStampDTOList);
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/stamp", method = POST)
	public ResponseEntity save(@RequestBody StampDTO stampDTO) {
		Stamp stamp = stampService.save(stampDTO);
		ResponseStampDTO responseStampDTO = new ResponseStampDTO(stamp);

		return GenericsUtil.objectToResponse(responseStampDTO);
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/stamp/addStamp/", method = POST)
	public ResponseEntity addStamp(@RequestBody ClientIDAndEnterpriseIdDTO clientIDAndEnterpriseIdDTO) {
		Card card = stampService.addStamp(clientIDAndEnterpriseIdDTO);
		ResponseCardDTO responseCardDTO = new ResponseCardDTO(card);

		return GenericsUtil.objectToResponse(responseCardDTO);
	}
}
