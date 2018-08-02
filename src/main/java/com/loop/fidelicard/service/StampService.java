package com.loop.fidelicard.service;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loop.fidelicard.dto.hybrid.ClientIDAndEnterpriseIdDTO;
import com.loop.fidelicard.dto.hybrid.ClientIdAndEnterpriseOwnerEmailDTO;
import com.loop.fidelicard.dto.stamp.StampDTO;
import com.loop.fidelicard.model.Card;
import com.loop.fidelicard.model.Enterprise;
import com.loop.fidelicard.model.FinalClient;
import com.loop.fidelicard.model.Stamp;
import com.loop.fidelicard.repository.StampRepository;

@Service
public class StampService {
	@Autowired
	private StampRepository stampRepository;
	@Autowired
	private CardService cardService;
	@Autowired
	private FinalClientService finalClientService;
	@Autowired
	private ErrorsService eS;
	@Autowired
	private EnterpriseService enterpriseService;

	public Iterable<Stamp> findAll() {
		return stampRepository.findAll();
	}

	public Stamp save(StampDTO stampDTO) {
		Card card = cardService.findById(stampDTO.getCardId());
		Stamp stamp = new Stamp(card);
		stamp.setCard(card);
		stampRepository.save(stamp);
		return stamp;
	}

	public Stamp addNewStamp(Card card) {
		Stamp stamp = new Stamp();
		stamp.setCard(card);
		stamp = stampRepository.save(stamp);
		if (card.getStamps() == null) {
			card.setStamps(new ArrayList<Stamp>());
		}
		card.getStamps().add(stamp);
		return stamp;
	}

	public Card addStampAndSave(ClientIDAndEnterpriseIdDTO clientIDAndEnterpriseIdDTO) {
		Card card = getCardByClientIDAndEnterpriseIdDTO(clientIDAndEnterpriseIdDTO);
		return addStampAndSave(card);
	}

	public Card addStampAndSave(ClientIdAndEnterpriseOwnerEmailDTO dto) {
		Card card = getCardByClientIdAndEnterpriseOwnerEmailDTO(dto);
		return addStampAndSave(card);
	}

	public Card addStampAndSave(Card card) {
		addNewStamp(card);
		return cardService.save(card);
	}

	private Card getCardByClientIDAndEnterpriseIdDTO(ClientIDAndEnterpriseIdDTO clientIDAndEnterpriseIdDTO) {
		FinalClient finalClient = finalClientService.findById(clientIDAndEnterpriseIdDTO.getFinalClientId());
		Enterprise enterprise = enterpriseService.findById(clientIDAndEnterpriseIdDTO.getEnterpriseId());
		Card card = finalClient.getCardByEnterprise(enterprise);
		return card;
	}

	private Card getCardByClientIdAndEnterpriseOwnerEmailDTO(ClientIdAndEnterpriseOwnerEmailDTO dto) {
		FinalClient finalClient = finalClientService.findById(dto.getFinalClientId());
		Enterprise enterprise = enterpriseService.findByOwnerEmail(dto.getEnterpriseOwnerEmail());
		Card card = finalClient.getCardByEnterprise(enterprise);
		return card;
	}

	public Card cleanCard(ClientIDAndEnterpriseIdDTO clientIDAndEnterpriseIdDTO) {
		Card card = getCardByClientIDAndEnterpriseIdDTO(clientIDAndEnterpriseIdDTO);
		card = cardService.removeAllStamps(card);
		return card;
	}

	public void delete(Stamp stamp) {
		stampRepository.delete(stamp);
	}

	public List<String> errorsToAddStampByFinalClientIdAndEnterpriseOwnerEmail(
			@Valid ClientIdAndEnterpriseOwnerEmailDTO dto) {
		List<String> errors = new ArrayList<String>();

		eS.addErrosIfFinalClientByIdNotExist(dto.getFinalClientId(), errors);
		eS.addErrorsIfEnterprisByOwnerEmailFinalClientNotExist(dto.getEnterpriseOwnerEmail(), errors);

		return errors;
	}

}
