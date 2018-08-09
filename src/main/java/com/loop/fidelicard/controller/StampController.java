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
import com.loop.fidelicard.dto.hybrid.FinalClientIdAndEnterpriseIdDTO;
import com.loop.fidelicard.model.Card;
import com.loop.fidelicard.service.StampService;
import com.loop.fidelicard.util.GenericsUtil;

@RestController
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@CrossOrigin(origins = "*")
public class StampController {
	@Autowired
	private StampService stampService;

	@SuppressWarnings("rawtypes")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ENTERPRISE')")
	@RequestMapping(value = "v1/stamp/addStampByFinalClientIdAndEnterpriseId/", method = POST)
	public ResponseEntity addStampByFinalClientIdAndEnterpriseId(
			@Valid @RequestBody FinalClientIdAndEnterpriseIdDTO dto, BindingResult result) {
		if (result.hasErrors()) {
			return GenericsUtil.errorsToResponse(result);
		}

		List<String> errors = stampService.errorsToAddStampByFinalClientIdAndEnterpriseId(dto);
		if (!errors.isEmpty()) {
			return GenericsUtil.errorsToResponse(errors);
		}

		Card card = stampService.addStampAndSave(dto);

		return GenericsUtil.objectToResponse(new ResponseFinalClientDTO(card));
	}

}
