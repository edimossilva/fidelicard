package com.loop.fidelicard.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loop.fidelicard.dto.enterprise.EnterpriseIdDTO;
import com.loop.fidelicard.dto.manager.EnterpriseIdAndDateDTO;
import com.loop.fidelicard.model.Stamp;

@Service
public class ManagerService {
	@Autowired
	private StampService stampService;

	@Autowired
	private FinalClientService finalClientService;

	@Autowired
	private ErrorsService eS;

	public int countAllStamps() {
		return stampService.findAll().size();
	}

	public int countFinalClientsByEnterpriseIdDTO(EnterpriseIdDTO dto) {
		return finalClientService.findAllByEnterpriseId(dto.getEnterpriseId()).size();
	}

	public int countStampsByEnterpriseIdAndDateDTO(EnterpriseIdAndDateDTO dto) {
		List<Stamp> stamps = stampService.findAllByEnterpriseIdAndDate(dto);
		return stamps.size();
	}

	public int countStampsByEnterpriseIdDTO(EnterpriseIdDTO dto) {
		List<Stamp> stamps = stampService.findAllByEnterpriseId(dto.getEnterpriseId());
		return stamps.size();
	}

	public List<String> errorsToCountStampsByEnterpriseId(EnterpriseIdDTO dto) {
		List<String> errors = new ArrayList<String>();

		eS.addErrorsIfEnterpriseByIdNotExist(dto.getEnterpriseId(), errors);
		eS.addErrorsIfOfferByEnterpriseIdNotExist(dto.getEnterpriseId(), errors);

		return errors;
	}

	public List<String> errorsToCountByEnterpriseIdAndDate(EnterpriseIdAndDateDTO dto) {
		List<String> errors = new ArrayList<String>();

		eS.addErrorsIfEnterpriseByIdNotExist(dto.getEnterpriseId(), errors);
		eS.addErrorsIfOfferByEnterpriseIdNotExist(dto.getEnterpriseId(), errors);

		return errors;
	}

	public List<String> errorsToCountFinalClientsByEnterpriseId(EnterpriseIdDTO dto) {
		List<String> errors = new ArrayList<String>();

		eS.addErrorsIfEnterpriseByIdNotExist(dto.getEnterpriseId(), errors);
		eS.addErrorsIfOfferByEnterpriseIdNotExist(dto.getEnterpriseId(), errors);

		return errors;
	}

}
