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

import com.loop.fidelicard.dto.enterprise.EnterpriseIdDTO;
import com.loop.fidelicard.dto.manager.EnterpriseIdAndDateDTO;
import com.loop.fidelicard.service.ManagerService;
import com.loop.fidelicard.util.GenericsUtil;
import com.loop.fidelicard.util.MyLogger;

@RestController
@CrossOrigin(origins = "*")
public class ManagerController {

	private static final String V1_MANAGER_COUNT_FINAL_CLIENTS_BY_ENTERPRISE_ID = "v1/manager/countFinalClientsByEnterpriseId";

	private static final String V1_MANAGER_COUNT_ALL_STAMPS = "v1/manager/countAllStamps";

	private static final String V1_MANAGER_COUNT_STAMPS_BY_ENTERPRISE_ID = "v1/manager/countStampsByEnterpriseId";

	private static final String V1_MANAGER_COUNT_STAMPS_BY_ENTERPRISE_ID_AND_DATE = "v1/manager/countStampsByEnterpriseIdAndDate";

	@Autowired
	private ManagerService managerService;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@SuppressWarnings("rawtypes")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@RequestMapping(value = V1_MANAGER_COUNT_STAMPS_BY_ENTERPRISE_ID_AND_DATE, method = POST)
	public ResponseEntity countByEnterpriseIdAndDate(@Valid @RequestBody EnterpriseIdAndDateDTO dto,
			BindingResult result) {

		logger.info(MyLogger.getMessage(V1_MANAGER_COUNT_STAMPS_BY_ENTERPRISE_ID_AND_DATE, dto));

		if (result.hasErrors()) {
			logger.error(MyLogger.getErrorMessage(V1_MANAGER_COUNT_STAMPS_BY_ENTERPRISE_ID_AND_DATE, result));

			return GenericsUtil.errorsToResponse(result);
		}

		List<String> errors = managerService.errorsToCountByEnterpriseIdAndDate(dto);
		if (!errors.isEmpty()) {
			logger.error(MyLogger.getErrorMessageFromList(V1_MANAGER_COUNT_STAMPS_BY_ENTERPRISE_ID_AND_DATE, errors));

			return GenericsUtil.errorsToResponse(errors);
		}

		int count = managerService.countStampsByEnterpriseIdAndDateDTO(dto);

		return GenericsUtil.objectToResponse(count);

	}

	@SuppressWarnings("rawtypes")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@RequestMapping(value = V1_MANAGER_COUNT_STAMPS_BY_ENTERPRISE_ID, method = POST)
	public ResponseEntity countStampsByEnterpriseId(@Valid @RequestBody EnterpriseIdDTO dto, BindingResult result) {

		logger.info(MyLogger.getMessage(V1_MANAGER_COUNT_STAMPS_BY_ENTERPRISE_ID, dto));

		if (result.hasErrors()) {
			logger.error(MyLogger.getErrorMessage(V1_MANAGER_COUNT_STAMPS_BY_ENTERPRISE_ID, result));

			return GenericsUtil.errorsToResponse(result);
		}

		List<String> errors = managerService.errorsToCountStampsByEnterpriseId(dto);
		if (!errors.isEmpty()) {
			logger.error(MyLogger.getErrorMessageFromList(V1_MANAGER_COUNT_STAMPS_BY_ENTERPRISE_ID, errors));

			return GenericsUtil.errorsToResponse(errors);
		}

		int count = managerService.countStampsByEnterpriseIdDTO(dto);

		return GenericsUtil.objectToResponse(count);

	}

	@SuppressWarnings("rawtypes")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@RequestMapping(value = V1_MANAGER_COUNT_ALL_STAMPS, method = POST)
	public ResponseEntity countAllStamps() {

		logger.info(MyLogger.getMessage(V1_MANAGER_COUNT_ALL_STAMPS, ""));

		int count = managerService.countAllStamps();

		return GenericsUtil.objectToResponse(count);

	}

	@SuppressWarnings("rawtypes")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@RequestMapping(value = V1_MANAGER_COUNT_FINAL_CLIENTS_BY_ENTERPRISE_ID, method = POST)
	public ResponseEntity countFinalClientsByEnterpriseId(@Valid @RequestBody EnterpriseIdDTO dto,
			BindingResult result) {

		logger.info(MyLogger.getMessage(V1_MANAGER_COUNT_FINAL_CLIENTS_BY_ENTERPRISE_ID, dto));

		if (result.hasErrors()) {
			logger.error(MyLogger.getErrorMessage(V1_MANAGER_COUNT_FINAL_CLIENTS_BY_ENTERPRISE_ID, result));

			return GenericsUtil.errorsToResponse(result);
		}

		List<String> errors = managerService.errorsToCountFinalClientsByEnterpriseId(dto);
		if (!errors.isEmpty()) {
			logger.error(MyLogger.getErrorMessageFromList(V1_MANAGER_COUNT_FINAL_CLIENTS_BY_ENTERPRISE_ID, errors));

			return GenericsUtil.errorsToResponse(errors);
		}

		int count = managerService.countFinalClientsByEnterpriseIdDTO(dto);

		return GenericsUtil.objectToResponse(count);

	}

}
