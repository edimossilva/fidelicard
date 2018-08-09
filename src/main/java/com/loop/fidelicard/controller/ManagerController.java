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

import com.loop.fidelicard.dto.enterprise.EnterpriseIdDTO;
import com.loop.fidelicard.dto.manager.EnterpriseIdAndDateDTO;
import com.loop.fidelicard.service.ManagerService;
import com.loop.fidelicard.util.GenericsUtil;

@RestController
@CrossOrigin(origins = "*")
public class ManagerController {

	@Autowired
	private ManagerService managerService;

	@SuppressWarnings("rawtypes")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@RequestMapping(value = "v1/manager/countStampsByEnterpriseIdAndDate", method = POST)
	public ResponseEntity countByEnterpriseIdAndDate(@Valid @RequestBody EnterpriseIdAndDateDTO dto,
			BindingResult result) {

		if (result.hasErrors()) {
			return GenericsUtil.errorsToResponse(result);
		}

		List<String> errors = managerService.errorsToCountByEnterpriseIdAndDate(dto);
		if (!errors.isEmpty()) {
			return GenericsUtil.errorsToResponse(errors);
		}

		int count = managerService.countStampsByEnterpriseIdAndDateDTO(dto);

		return GenericsUtil.objectToResponse(count);

	}

	@SuppressWarnings("rawtypes")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@RequestMapping(value = "v1/manager/countStampsByEnterpriseId", method = POST)
	public ResponseEntity countStampsByEnterpriseId(@Valid @RequestBody EnterpriseIdDTO dto, BindingResult result) {

		if (result.hasErrors()) {
			return GenericsUtil.errorsToResponse(result);
		}

		List<String> errors = managerService.errorsToCountStampsByEnterpriseId(dto);
		if (!errors.isEmpty()) {
			return GenericsUtil.errorsToResponse(errors);
		}

		int count = managerService.countStampsByEnterpriseIdDTO(dto);

		return GenericsUtil.objectToResponse(count);

	}

	@SuppressWarnings("rawtypes")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@RequestMapping(value = "v1/manager/countAllStamps", method = POST)
	public ResponseEntity countAllStamps() {

		int count = managerService.countAllStamps();

		return GenericsUtil.objectToResponse(count);

	}
	
	@SuppressWarnings("rawtypes")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@RequestMapping(value = "v1/manager/countFinalClientsByEnterpriseId", method = POST)
	public ResponseEntity countFinalClientsByEnterpriseId(@Valid @RequestBody EnterpriseIdDTO dto, BindingResult result) {

		if (result.hasErrors()) {
			return GenericsUtil.errorsToResponse(result);
		}

		List<String> errors = managerService.errorsToCountFinalClientsByEnterpriseId(dto);
		if (!errors.isEmpty()) {
			return GenericsUtil.errorsToResponse(errors);
		}

		int count = managerService.countFinalClientsByEnterpriseIdDTO(dto);

		return GenericsUtil.objectToResponse(count);

	}

}
