package com.loop.fidelicard.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loop.fidelicard.dto.hybrid.FinalClientIdAndEnterpriseIdDTO;
import com.loop.fidelicard.dto.manager.EnterpriseIdAndDateDTO;
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

	public List<Stamp> findAll() {
		return stampRepository.findAll();
	}

	public List<Stamp> findAllByEnterpriseId(long enterpriseId) {
		Enterprise enterprise = enterpriseService.findById(enterpriseId);
		return findAllByEnterprise(enterprise);
	}

	public List<Stamp> findAllByEnterpriseIdAndDate(EnterpriseIdAndDateDTO dto) {
		Enterprise enterprise = enterpriseService.findById(dto.getEnterpriseId());
		List<Stamp> stamps = findAllByEnterprise(enterprise);
		stamps = findByStampsAndDayMonthYear(stamps, dto.getDay(), dto.getMonth(), dto.getYear());
		return stamps;
	}

	private List<Stamp> findByStampsAndDayMonthYear(List<Stamp> stamps, Long day, Long month, Long year) {
		List<Stamp> selectedStamps = new ArrayList<>();
		for (Stamp stamp : stamps) {
			LocalDateTime createdAt = stamp.getCreatedAt();
			if (createdAt.getYear() == year) {
				if (createdAt.getMonthValue() == month) {
					if (createdAt.getDayOfMonth() == day) {
						selectedStamps.add(stamp);
					}
				}
			}
		}
		return selectedStamps;
	}

	private List<Stamp> findAllByEnterprise(Enterprise enterprise) {
		List<Card> cards = cardService.findAllByEnterprise(enterprise);
		List<Stamp> stamps = new ArrayList<>();
		cards.forEach(c -> stamps.addAll(c.getStamps()));
		return stamps;
	}

	public Card addStampAndSave(FinalClientIdAndEnterpriseIdDTO dto) {
		Card card = getCardByClientIDAndEnterpriseIdDTO(dto);
		return addStampAndSave(card);
	}

	public Card addStampAndSave(Card card) {
		Stamp stamp = new Stamp(card);
		stamp = stampRepository.save(stamp);

		card.addStamp(stamp);

		enterpriseService.save(card.getEnterprise());
		return cardService.save(card);
	}

	private Card getCardByClientIDAndEnterpriseIdDTO(FinalClientIdAndEnterpriseIdDTO clientIDAndEnterpriseIdDTO) {
		FinalClient finalClient = finalClientService.findById(clientIDAndEnterpriseIdDTO.getFinalClientId());
		Enterprise enterprise = enterpriseService.findById(clientIDAndEnterpriseIdDTO.getEnterpriseId());
		Card card = finalClient.getCardByEnterprise(enterprise);
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
