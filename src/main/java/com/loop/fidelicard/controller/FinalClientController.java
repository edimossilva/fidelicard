package com.loop.fidelicard.controller;

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

import com.loop.fidelicard.dto.finalclient.FinalClientAndEnterpriseOwnerEmailDTO;
import com.loop.fidelicard.dto.finalclient.FinalClientCreateDTO;
import com.loop.fidelicard.dto.finalclient.ResponseFinalClientDTO;
import com.loop.fidelicard.dto.finalclient.UIDTO;
import com.loop.fidelicard.dto.hybrid.ClientUIAndEnterpriseIdDTO;
import com.loop.fidelicard.dto.hybrid.ClientUIAndEnterpriseOwnerEmailDTO;
import com.loop.fidelicard.model.Card;
import com.loop.fidelicard.model.FinalClient;
import com.loop.fidelicard.service.FinalClientService;
import com.loop.fidelicard.util.GenericsUtil;

@RestController
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class FinalClientController {
	@Autowired
	private FinalClientService finalClientService;

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/finalClient", method = GET)
	public ResponseEntity index() {
		List<ResponseFinalClientDTO> finalClientDTOList = new ArrayList<>();
		finalClientService.findAll().forEach(fc -> finalClientDTOList.add(fc.toResponseFinalClientDTO()));

		return GenericsUtil.objectToResponse(finalClientDTOList);
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/finalClient/{id}", method = GET)
	public ResponseEntity findByUniqueIdentifier(@PathVariable("id") String uniqueIdentifir) {

		FinalClient finalClient = finalClientService.findByUI(uniqueIdentifir);
		return GenericsUtil.objectToResponse(finalClient.toResponseFinalClientDTO());
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/finalClient", method = POST)
	public ResponseEntity save(@RequestBody FinalClientCreateDTO finalClientDTO) {
		FinalClient finalClient = finalClientService.save(finalClientDTO);

		return GenericsUtil.objectToResponse(finalClient.toResponseFinalClientDTO());
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/finalClient/existClientbyUICardInEnterprise", method = POST)
	public ResponseEntity existClientbyUICardInEnterprise(
			@RequestBody ClientUIAndEnterpriseIdDTO clientUiAndEnterpriseIdDTO) {

		FinalClient finalClient = finalClientService.findClientByUICardInEnterprise(clientUiAndEnterpriseIdDTO);

		if (finalClient != null) {

			return GenericsUtil.objectToResponse(finalClient.toResponseFinalClientDTO());

		} else {

			String notFoundByUI = "User not found with UI = " + clientUiAndEnterpriseIdDTO.getFinalClientUI();
			String notFoundByEnterpriseId = " and enterprise id = " + clientUiAndEnterpriseIdDTO.getEnterpriseId();
			String message = notFoundByUI + notFoundByEnterpriseId;

			return GenericsUtil.objectToResponse(message);

		}
	}

	@SuppressWarnings("rawtypes")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ENTERPRISE')")
	@RequestMapping(value = "/finalClient/existClientByUIAndEnterpriseOwnerEmail", method = POST)
	public ResponseEntity existClientByUIAndEnterpriseOwnerEmail(
			@Valid @RequestBody ClientUIAndEnterpriseOwnerEmailDTO dto, BindingResult result) {

		if (result.hasErrors()) {
			return GenericsUtil.errorsToResponse(result);
		}

		List<String> errors = finalClientService.errorsToExistClientByUIAndEnterpriseOwnerEmail(dto);
		if (!errors.isEmpty()) {
			return GenericsUtil.errorsToResponse(errors);
		}

		FinalClient finalClient = finalClientService.findClientByUIAndEnterpriseOwnerEmail(dto);

		return GenericsUtil.objectToResponse(finalClient.toResponseFinalClientDTO());

	}

	@SuppressWarnings("rawtypes")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ENTERPRISE')")
	@RequestMapping(value = "/finalClient/createWithStamp", method = POST)
	public ResponseEntity createWithStamp(@Valid @RequestBody FinalClientAndEnterpriseOwnerEmailDTO dto,
			BindingResult result) {

		if (result.hasErrors()) {
			return GenericsUtil.errorsToResponse(result);
		}

		List<String> errors = finalClientService.errorsToCreateWithStamp(dto);
		if (!errors.isEmpty()) {
			return GenericsUtil.errorsToResponse(errors);
		}

		Card card = finalClientService.createWithStamp(dto);

		return GenericsUtil.objectToResponse(card.toResponseCardDTO());
	}

	@SuppressWarnings("rawtypes")
	@PreAuthorize("hasAuthority('ROLE_ENTERPRISE')")
	@RequestMapping(value = "/finalClient/existClientbyUI", method = POST)
	public ResponseEntity existClientbyUI(@RequestBody UIDTO uIDTO) {
		FinalClient finalClient = finalClientService.findByUI(uIDTO.getUniqueIdentifier());
		if (finalClient != null) {
			return GenericsUtil.objectToResponse(finalClient.toResponseFinalClientDTO());
		} else {
			String message = "User not found with UI = " + uIDTO.getUniqueIdentifier();
			return GenericsUtil.objectToResponse(message);
		}
	}
}
