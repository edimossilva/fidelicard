package com.loop.fidelicard.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loop.fidelicard.dto.finalclient.ResponseFinalClientDTO;
import com.loop.fidelicard.dto.hybrid.FinalClientIdAndEnterpriseIdDTO;
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
	private StampService stampService;

	@Autowired
	private OfferService offerService;

	@Autowired
	private ErrorsService eS;

	public Card save() {
		return cardRepository.save(new Card());
	}

	public Iterable<Card> findAll() {
		return cardRepository.findAll();
	}

	public List<Card> findAllByEnterprise(Enterprise enterprise) {
		List<Offer> offers = offerService.findAllByEnterprise(enterprise);
		List<Card> cards = new ArrayList<>();
		for (Offer offer : offers) {
			cards.addAll(offer.getCards());
		}
		return cards;
	}

	public Card findById(Long id) {
		return cardRepository.findById(id);
	}

	public Card findByFinalClientAndOffer(FinalClient finalClient, Offer offer) {
		return cardRepository.findByFinalClientAndOffer(finalClient, offer);
	}

	public Card findByFinalClientAndEnterprise(FinalClient finalClient, Enterprise enterprise) {
		List<Offer> offers = enterprise.getOffers();
		Offer offer = offers.get(0);

		return findByFinalClientAndOffer(finalClient, offer);
	}

	public Card findCardByFinalClient(List<Card> cards, FinalClient finalClient) {
		for (Card card : cards) {
			if (card.getFinalClient().equals(finalClient)) {
				return card;
			}
		}
		return null;
	}

	public Card save(Card card) {
		return cardRepository.save(card);
	}

	public Boolean isFull(FinalClientIdAndEnterpriseIdDTO clientIDAndEnterpriseIdDTO) {
		Card card = findByClientIdAndEnterpriseIdDTO(clientIDAndEnterpriseIdDTO);
		if (card == null) {
			return false;
		}
		return card.isFull();
	}

	public void delete(Long id) {
		cardRepository.delete(findById(id));
	}

	private Card setRewardReceivedCardAndSave(Card card) {
		card.setRewardReceived(true);
		return cardRepository.save(card);
	}

	public Card setRewardReceivedCard(FinalClientIdAndEnterpriseIdDTO dto) {
		Card card = findByClientIdAndEnterpriseIdDTO(dto);
		return setRewardReceivedCardAndSave(card);
	}

	private Card findByClientIdAndEnterpriseIdDTO(FinalClientIdAndEnterpriseIdDTO clientIDAndEnterpriseIdDTO) {
		FinalClient finalClient = finalClientService.findById(clientIDAndEnterpriseIdDTO.getFinalClientId());
		Enterprise enterprise = enterpriseService.findById(clientIDAndEnterpriseIdDTO.getEnterpriseId());
		if (finalClient == null) {
			return null;
		}
		return finalClient.getCardByEnterprise(enterprise);
	}

	public Card findByFinalClientUIAndEnterpriseId(String finalClientUI, Long enterpriseId) {
		FinalClient finalClient = finalClientService.findByUI(finalClientUI);
		Enterprise enterprise = enterpriseService.findById(enterpriseId);
		Card card = findByFinalClientAndEnterprise(finalClient, enterprise);
		return card;
	}

	public Card findByFinalClientIdAndEnterpriseId(Long finalClientId, Long enterpriseId) {
		FinalClient finalClient = finalClientService.findById(finalClientId);
		Enterprise enterprise = enterpriseService.findById(enterpriseId);
		Card card = findByFinalClientAndEnterprise(finalClient, enterprise);
		return card;
	}

	public Card createWithStampFromFinalClientAndOffer(FinalClient finalClient, Offer offer) {
		Card card = new Card(finalClient, offer);
		card = cardRepository.save(card);
		stampService.addNewStamp(card);

		return card;
	}

	public ResponseFinalClientDTO createWithStamp(FinalClientIdAndEnterpriseIdDTO dto) {
		FinalClient finalClient = finalClientService.findById(dto.getFinalClientId());
		Enterprise enterprise = enterpriseService.findById(dto.getEnterpriseId());
		Offer offer = enterprise.getOffer();
		Card card = createWithStampFromFinalClientAndOffer(finalClient, offer);
		ResponseFinalClientDTO responseFinalClientDTO = new ResponseFinalClientDTO(card.getFinalClient(), card);
		return responseFinalClientDTO;
	}

	public List<String> errorsToGetReward(FinalClientIdAndEnterpriseIdDTO dto) {
		List<String> errors = new ArrayList<String>();

		eS.addErrorsIfFinalClientByIdNotExist(dto.getFinalClientId(), errors);
		eS.addErrorsIfEnterpriseByIdNotExist(dto.getEnterpriseId(), errors);
		eS.addErrorsIfOfferByEnterpriseIdNotExist(dto.getEnterpriseId(), errors);
		if (!isFull(dto)) {
			errors.add(
					"O cartao do cliente com id [" + dto.getFinalClientId() + "] NAO esta cheio para a empresa com id ["
							+ dto.getEnterpriseId() + "], para receber o premio eh preciso completa-lo");
		}
		return errors;
	}

	public List<String> errorsToCreateWithStamp(FinalClientIdAndEnterpriseIdDTO dto) {
		List<String> errors = new ArrayList<String>();

		eS.addErrorsIfEnterpriseByIdNotExist(dto.getEnterpriseId(), errors);
		eS.addErrorsIfOfferByEnterpriseIdNotExist(dto.getEnterpriseId(), errors);
		eS.addErrorsIfFinalClientByIdNotExist(dto.getFinalClientId(), errors);
		eS.addErrorsIfCardByFinalClientIdAndEnterpriseIdExist(dto.getFinalClientId(), dto.getEnterpriseId(), errors);

		return errors;
	}

}
