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

import com.loop.fidelicard.dto.finalclient.FinalClientCreateDTO;
import com.loop.fidelicard.dto.finalclient.ResponseFinalClientDTO;
import com.loop.fidelicard.model.FinalClient;
import com.loop.fidelicard.service.FinalClientService;
import com.loop.fidelicard.util.GenericsUtil;

@RestController
public class FinalClientController {
	@Autowired
	private FinalClientService finalClientService;

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/finalClient", method = GET)
	public ResponseEntity index() {
		List<ResponseFinalClientDTO> finalClientDTOList = new ArrayList<>();
		finalClientService.findAll().forEach(fc -> finalClientDTOList.add(new ResponseFinalClientDTO(fc)));

		return GenericsUtil.dTOToResponse(finalClientDTOList);
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/finalClient", method = POST)
	public ResponseEntity save(@RequestBody FinalClientCreateDTO finalClientDTO) {
		FinalClient finalClient = finalClientService.save(finalClientDTO);
		ResponseFinalClientDTO responseFinalClientDTO = new ResponseFinalClientDTO(finalClient);

		return GenericsUtil.dTOToResponse(responseFinalClientDTO);
	}

}
