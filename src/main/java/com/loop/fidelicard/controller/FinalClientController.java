package com.loop.fidelicard.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.loop.fidelicard.dto.consumer.ConsumerFinalClientDTO;
import com.loop.fidelicard.dto.finalclient.FinalClientAndEnterpriseIdDTO;
import com.loop.fidelicard.dto.finalclient.ResponseFinalClientDTO;
import com.loop.fidelicard.dto.finalclient.UIDTO;
import com.loop.fidelicard.dto.hybrid.ClientUIAndEnterpriseIdDTO;
import com.loop.fidelicard.model.FinalClient;
import com.loop.fidelicard.service.FinalClientService;
import com.loop.fidelicard.util.GenericsUtil;

@RestController
@CrossOrigin(origins = "*")
public class FinalClientController {
	@Autowired
	private FinalClientService finalClientService;

	@SuppressWarnings("rawtypes")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@RequestMapping(value = "/v1/finalClient", method = GET)
	public ResponseEntity findAll() {

		List<FinalClient> finalClientList = finalClientService.findAll();

		List<ConsumerFinalClientDTO> dtoList = new ArrayList<>();
		finalClientList.forEach(fc -> dtoList.add(fc.toConsumerFinalClientDTO()));

		return GenericsUtil.objectToResponse(dtoList);

	}

	@SuppressWarnings("rawtypes")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@RequestMapping(value = "/v1/finalClient/countFinalClientsByEnterpriseId/{id}", method = GET)
	public ResponseEntity findAllByEnterpriseId(@PathVariable("id") long id) {

		HashSet<FinalClient> finalClients = finalClientService.findAllByEnterpriseId(id);

		List<ConsumerFinalClientDTO> dtoList = new ArrayList<>();
		finalClients.forEach(fc -> dtoList.add(fc.toConsumerFinalClientDTO()));

		return GenericsUtil.objectToResponse(dtoList.size());

	}

	@SuppressWarnings("rawtypes")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ENTERPRISE')")
	@RequestMapping(value = "/v1/finalClient/existClientByUIAndEnterpriseId", method = POST)
	public ResponseEntity existClientByUIAndEnterpriseId(@Valid @RequestBody ClientUIAndEnterpriseIdDTO dto,
			BindingResult result) {

		if (result.hasErrors()) {
			return GenericsUtil.errorsToResponse(result);
		}

		List<String> errors = finalClientService.errorsToExistClientByUIAndEnterpriseId(dto);
		if (!errors.isEmpty()) {
			return GenericsUtil.errorsToResponse(errors);
		}

		ResponseFinalClientDTO finalClientResponseDTO = finalClientService
				.findFinalClientResponseDTOByUIAndEnterpriseId(dto);

		return GenericsUtil.objectToResponse(finalClientResponseDTO);

	}

	@SuppressWarnings("rawtypes")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ENTERPRISE')")
	@RequestMapping(value = "/v1/finalClient/createWithStamp", method = POST)
	public ResponseEntity createWithStamp(@Valid @RequestBody FinalClientAndEnterpriseIdDTO dto, BindingResult result) {

		if (result.hasErrors()) {
			return GenericsUtil.errorsToResponse(result);
		}

		List<String> errors = finalClientService.errorsToCreateWithStamp(dto);
		if (!errors.isEmpty()) {
			return GenericsUtil.errorsToResponse(errors);
		}

		ResponseFinalClientDTO finalClientResponseDTO = finalClientService.createWithStamp(dto);

		return GenericsUtil.objectToResponse(finalClientResponseDTO);
	}

	@SuppressWarnings("rawtypes")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_FINAL_CLIENT')")
	@RequestMapping(value = "/v1/finalClient/getAllCardsByUI", method = POST)
	public ResponseEntity getAllCardsByUI(@Valid @RequestBody UIDTO uIDTO, BindingResult result) {
		if (result.hasErrors()) {
			return GenericsUtil.errorsToResponse(result);
		}

		List<String> errors = finalClientService.errorsToGetAllCardsByUI(uIDTO);
		if (!errors.isEmpty()) {
			return GenericsUtil.errorsToResponse(errors);
		}

		ConsumerFinalClientDTO dto = finalClientService.findConsumerFinalClientByUI(uIDTO.getFinalClientUI());
		return GenericsUtil.objectToResponse(dto);

	}
}
