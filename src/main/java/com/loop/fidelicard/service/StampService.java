package com.loop.fidelicard.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loop.fidelicard.dto.hybrid.FinalClientIdAndEnterpriseIdDTO;
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

	public Stamp addNewStamp(Card card) {
		Stamp stamp = new Stamp();
		stamp.setCard(card);
		stamp = stampRepository.save(stamp);
		if (card.getStamps() == null) {
			card.setStamps(new ArrayList<Stamp>());
		}
		card.getStamps().add(stamp);
		card.setRewardReceived(false);
		return stamp;
	}

	public Card addStampAndSave(FinalClientIdAndEnterpriseIdDTO clientIDAndEnterpriseIdDTO) {
		Card card = getCardByClientIDAndEnterpriseIdDTO(clientIDAndEnterpriseIdDTO);
		return addStampAndSave(card);
	}

	public Card addStampAndSave(Card card) {
		addNewStamp(card);
		return cardService.save(card);
	}

	private Card getCardByClientIDAndEnterpriseIdDTO(FinalClientIdAndEnterpriseIdDTO clientIDAndEnterpriseIdDTO) {
		FinalClient finalClient = finalClientService.findById(clientIDAndEnterpriseIdDTO.getFinalClientId());
		Enterprise enterprise = enterpriseService.findById(clientIDAndEnterpriseIdDTO.getEnterpriseId());
		Card card = finalClient.getCardByEnterprise(enterprise);
		return card;
	}

	public Card cleanCard(FinalClientIdAndEnterpriseIdDTO clientIDAndEnterpriseIdDTO) {
		Card card = getCardByClientIDAndEnterpriseIdDTO(clientIDAndEnterpriseIdDTO);
		card = cardService.removeAllStampsAndSave(card);
		return card;
	}

	public void delete(Stamp stamp) {
		stampRepository.delete(stamp);
	}

	public List<String> errorsToAddStampByFinalClientIdAndEnterpriseId(FinalClientIdAndEnterpriseIdDTO dto) {
		List<String> errors = new ArrayList<String>();

		eS.addErrorsIfFinalClientByIdNotExist(dto.getFinalClientId(), errors);
		eS.addErrorsIfEnterpriseByIdNotExist(dto.getEnterpriseId(), errors);
		eS.addErrorsIfOfferByEnterpriseIdNotExist(dto.getEnterpriseId(), errors);
		if (cardService.isFull(dto)) {
			errors.add("O cartao do cliente com id [" + dto.getFinalClientId() + "] esta cheio para a empresa com id ["
					+ dto.getEnterpriseId() + "]");
		}

		return errors;
	}

}
