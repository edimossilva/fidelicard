package com.loop.fidelicard.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.loop.fidelicard.dto.finalclient.FinalClientCreateDTO;
import com.loop.fidelicard.dto.finalclient.ResponseFinalClientDTO;
import com.loop.fidelicard.dto.finalclient.UIDTO;
import com.loop.fidelicard.dto.hybrid.ClientUIAndEnterpriseIdDTO;
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

		return GenericsUtil.objectToResponse(finalClientDTOList);
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/finalClient/{id}", method = GET)
	public ResponseEntity findByUniqueIdentifier(@PathVariable("id") String uniqueIdentifir) {

		FinalClient finalClient = finalClientService.findByUI(uniqueIdentifir);
		ResponseFinalClientDTO responseFinalClientDTO = new ResponseFinalClientDTO(finalClient);
		return GenericsUtil.objectToResponse(responseFinalClientDTO);
	}

	@SuppressWarnings("rawtypes")
	@PreAuthorize("hasAuthority('ROLE_ENTERPRISE')")
	@RequestMapping(value = "/finalClient", method = POST)
	public ResponseEntity save(@RequestBody FinalClientCreateDTO finalClientDTO) {
		FinalClient finalClient = finalClientService.save(finalClientDTO);
		ResponseFinalClientDTO responseFinalClientDTO = new ResponseFinalClientDTO(finalClient);

		return GenericsUtil.objectToResponse(responseFinalClientDTO);
	}

	@SuppressWarnings("rawtypes")
	@PreAuthorize("hasAuthority('ROLE_ENTERPRISE')")
	@RequestMapping(value = "/finalClient/existClientbyUICardInEnterprise", method = POST)
	public ResponseEntity existClientbyUICardInEnterprise(
			@RequestBody ClientUIAndEnterpriseIdDTO clientUiAndEnterpriseIdDTO) {
		FinalClient finalClient = finalClientService.existClientByUICardInEnterprise(clientUiAndEnterpriseIdDTO);
		if (finalClient != null) {
			ResponseFinalClientDTO responseFinalClientDTO = new ResponseFinalClientDTO(finalClient);

			return GenericsUtil.objectToResponse(responseFinalClientDTO);
		} else {
			String notFoundByUI = "User not found with UI = " + clientUiAndEnterpriseIdDTO.getFinalClientUI();
			String notFoundByEnterpriseId = " and enterprise id = " + clientUiAndEnterpriseIdDTO.getEnterpriseId();
			String message = notFoundByUI + notFoundByEnterpriseId;
			return GenericsUtil.objectToResponse(message);
		}
	}

	@SuppressWarnings("rawtypes")
	@PreAuthorize("hasAuthority('ROLE_ENTERPRISE')")
	@RequestMapping(value = "/finalClient/existClientbyUI", method = POST)
	public ResponseEntity existClientbyUI(@RequestBody UIDTO uIDTO) {
		FinalClient finalClient = finalClientService.findByUI(uIDTO.getUniqueIdentifier());
		if (finalClient != null) {
			ResponseFinalClientDTO responseFinalClientDTO = new ResponseFinalClientDTO(finalClient);
			return GenericsUtil.objectToResponse(responseFinalClientDTO);
		} else {
			String message = "User not found with UI = " + uIDTO.getUniqueIdentifier();
			return GenericsUtil.objectToResponse(message);
		}
	}
}
