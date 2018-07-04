package com.loop.fidelicard.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loop.fidelicard.dto.hybrid.ClientIDAndEnterpriseIdDTO;
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
	private EnterpriseService enterpriseService;

	public Iterable<Stamp> findAll() {
		return stampRepository.findAll();
	}

	public Stamp save(StampDTO stampDTO) {
		Card card = cardService.findById(stampDTO.getCardId()).get();
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

	public Card addStamp(ClientIDAndEnterpriseIdDTO clientIDAndEnterpriseIdDTO) {
		Card card = getCardByClientIDAndEnterpriseIdDTO(clientIDAndEnterpriseIdDTO);

		addNewStamp(card);
		return cardService.save(card);
	}

	private Card getCardByClientIDAndEnterpriseIdDTO(ClientIDAndEnterpriseIdDTO clientIDAndEnterpriseIdDTO) {
		FinalClient finalClient = finalClientService.findById(clientIDAndEnterpriseIdDTO.getFinalClientId());
		Enterprise enterprise = enterpriseService.findById(clientIDAndEnterpriseIdDTO.getEnterpriseId());
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

}
