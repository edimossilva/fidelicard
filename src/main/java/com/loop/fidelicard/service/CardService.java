package com.loop.fidelicard.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loop.fidelicard.dto.card.CardDTO;
import com.loop.fidelicard.dto.hybrid.ClientIDAndEnterpriseIdDTO;
import com.loop.fidelicard.model.Card;
import com.loop.fidelicard.model.Enterprise;
import com.loop.fidelicard.model.FinalClient;
import com.loop.fidelicard.model.Offer;
import com.loop.fidelicard.repository.CardRepository;

@Service
public class CardService {
	@Autowired
	private CardRepository cardRepository;

	@Autowired
	private FinalClientService finalClientService;

	@Autowired
	private EnterpriseService enterpriseService;

	@Autowired
	private OfferService offerService;

	@Autowired
	private StampService stampService;

	public Iterable<Card> findAll() {
		return cardRepository.findAll();
	}

	public Card save() {
		return cardRepository.save(new Card());
	}

	public Optional<Card> findById(Long id) {
		return cardRepository.findById(id);
	}

	public Optional<Card> findByFinalClientAndOffer(FinalClient finalClient, Offer offer) {
		return cardRepository.findByFinalClientAndOffer(finalClient, offer);
	}

	public Card createCardFromCardDTO(CardDTO cardDTO) {
		FinalClient finalClient = finalClientService.findById(cardDTO.getFinalClientId());
		Offer offer = offerService.findById(cardDTO.getOfferId());
		Card card = new Card(finalClient, offer);
		return cardRepository.save(card);
	}

	public Card findCardByFinalClient(List<Card> cards, FinalClient finalClient) {
		for (Card card : cards) {
			if (card.getFinalClient().equals(finalClient)) {
				return card;
			}
		}
		return null;
	}

	public Card createCardFromClientIDAndEnterpriseIdDTO(ClientIDAndEnterpriseIdDTO clientIDAndEnterpriseIdDTO) {
		Long enterpriseId = clientIDAndEnterpriseIdDTO.getEnterpriseId();
		Long finalClientId = clientIDAndEnterpriseIdDTO.getFinalClientId();
		Enterprise enterprise = enterpriseService.findById(enterpriseId);
		Offer offer = offerService.findAllByEnterprise(enterprise).get(0);
		FinalClient finalClient = finalClientService.findById(finalClientId);

		Card card = new Card(finalClient, offer);
		card = cardRepository.save(card);
		stampService.addNewStamp(card);

		return card;
	}

	public Boolean isBeforeLastStamp(ClientIDAndEnterpriseIdDTO clientIDAndEnterpriseIdDTO) {
		Long enterpriseId = clientIDAndEnterpriseIdDTO.getEnterpriseId();
		Long finalClientId = clientIDAndEnterpriseIdDTO.getFinalClientId();
		Enterprise enterprise = enterpriseService.findById(enterpriseId);
		Offer offer = offerService.findAllByEnterprise(enterprise).get(0);
		FinalClient finalClient = finalClientService.findById(finalClientId);
		boolean isCardFull = finalClient.isCardAlmostFull(offer);
		return isCardFull;
	}

	public Card save(Card card) {
		return cardRepository.save(card);
	}

	public Boolean isFull(ClientIDAndEnterpriseIdDTO clientIDAndEnterpriseIdDTO) {
		Long enterpriseId = clientIDAndEnterpriseIdDTO.getEnterpriseId();
		Long finalClientId = clientIDAndEnterpriseIdDTO.getFinalClientId();
		Enterprise enterprise = enterpriseService.findById(enterpriseId);
		Offer offer = offerService.findAllByEnterprise(enterprise).get(0);
		FinalClient finalClient = finalClientService.findById(finalClientId);
		boolean isCardFull = finalClient.isCardFull(offer);
		return isCardFull;
	}

	public void delete(Long id) {
		cardRepository.delete(findById(id).get());
	}

}
