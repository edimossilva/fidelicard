package com.loop.fidelicard.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.ArrayList;
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

import com.loop.fidelicard.dto.consumer.ConsumerFinalClientDTO;
import com.loop.fidelicard.dto.finalclient.FinalClientAndEnterpriseIdDTO;
import com.loop.fidelicard.dto.finalclient.ResponseFinalClientDTO;
import com.loop.fidelicard.dto.finalclient.UIDTO;
import com.loop.fidelicard.dto.hybrid.ClientUIAndEnterpriseIdDTO;
import com.loop.fidelicard.model.FinalClient;
import com.loop.fidelicard.service.FinalClientService;
import com.loop.fidelicard.util.GenericsUtil;
import com.loop.fidelicard.util.MyLogger;

@RestController
@CrossOrigin(origins = "*")
public class FinalClientController {
	private static final String V1_FINAL_CLIENT = "/v1/finalClient";

	private static final String V1_FINAL_CLIENT_CREATE_WITH_STAMP = "/v1/finalClient/createWithStamp";

	private static final String V1_FINAL_CLIENT_EXIST_CLIENT_BY_UI_AND_ENTERPRISE_ID = "/v1/finalClient/existClientByUIAndEnterpriseId";

	private static final String V1_FINAL_CLIENT_GET_ALL_CARDS_BY_UI = "/v1/finalClient/getAllCardsByUI";


	@Autowired
	private FinalClientService finalClientService;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@SuppressWarnings("rawtypes")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@RequestMapping(value = V1_FINAL_CLIENT, method = GET)
	public ResponseEntity findAll() {

		logger.info(MyLogger.getMessage(V1_FINAL_CLIENT, ""));

		List<FinalClient> finalClientList = finalClientService.findAll();

		List<ConsumerFinalClientDTO> dtoList = new ArrayList<>();
		finalClientList.forEach(fc -> dtoList.add(fc.toConsumerFinalClientDTO()));

		return GenericsUtil.objectToResponse(dtoList);

	}

	@SuppressWarnings("rawtypes")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ENTERPRISE')")
	@RequestMapping(value = V1_FINAL_CLIENT_EXIST_CLIENT_BY_UI_AND_ENTERPRISE_ID, method = POST)
	public ResponseEntity existClientByUIAndEnterpriseId(@Valid @RequestBody ClientUIAndEnterpriseIdDTO dto,
			BindingResult result) {

		logger.info(MyLogger.getMessage(V1_FINAL_CLIENT_EXIST_CLIENT_BY_UI_AND_ENTERPRISE_ID, dto));

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
	@RequestMapping(value = V1_FINAL_CLIENT_CREATE_WITH_STAMP, method = POST)
	public ResponseEntity createWithStamp(@Valid @RequestBody FinalClientAndEnterpriseIdDTO dto, BindingResult result) {

		logger.info(MyLogger.getMessage(V1_FINAL_CLIENT_CREATE_WITH_STAMP, dto));

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
	@RequestMapping(value = V1_FINAL_CLIENT_GET_ALL_CARDS_BY_UI, method = POST)
	public ResponseEntity getAllCardsByUI(@Valid @RequestBody UIDTO uIDTO, BindingResult result) {

		logger.info(MyLogger.getMessage(V1_FINAL_CLIENT_GET_ALL_CARDS_BY_UI, uIDTO));

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
