package com.loop.fidelicard.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import com.loop.fidelicard.dto.DefaultDTO;
import com.loop.fidelicard.factory.DTOFactory;
import com.loop.fidelicard.model.DefaultModel;
import com.loop.fidelicard.service.DefaultService;
import com.loop.fidelicard.util.GenericsUtil;

@SuppressWarnings("rawtypes")
public abstract class RESTController<E extends DefaultService> {
	@Autowired
	private E service;

	@SuppressWarnings("unchecked")
	public ResponseEntity index() {
		List<DefaultDTO> dtoList = new ArrayList<DefaultDTO>();
		service.findAll().forEach(e -> dtoList.add((DefaultDTO) DTOFactory.get(e)));

		return GenericsUtil.dTOToResponse(dtoList);
	}

	public ResponseEntity save(@RequestBody DefaultDTO defaultDTO) {
		DefaultModel defaultModel = service.save(defaultDTO);
		DefaultDTO responseDTO = DTOFactory.get(defaultModel);

		return GenericsUtil.dTOToResponse(responseDTO);
	}
}
