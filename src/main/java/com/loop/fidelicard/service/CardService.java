package com.loop.fidelicard.service;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loop.fidelicard.dto.card.CardDTO;
import com.loop.fidelicard.dto.finalclient.ResponseFinalClientDTO;
import com.loop.fidelicard.dto.hybrid.ClientIdAndEnterpriseIdDTO;
import com.loop.fidelicard.dto.hybrid.ClientUIAndEnterpriseIdDTO;
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

	@Autowired
	private ErrorsService eS;

	public Iterable<Card> findAll() {
		return cardRepository.findAll();
	}

	public Card save() {
		return cardRepository.save(new Card());
	}

	public Card findById(Long id) {
		return cardRepository.findById(id);
	}

	public Card findByFinalClientAndOffer(FinalClient finalClient, Offer offer) {
		return cardRepository.findByFinalClientAndOffer(finalClient, offer);
	}

	public Card findByFinalClientAndEnterprise(FinalClient finalClient, Enterprise enterprise) {
		List<Offer> offers =enterprise.getOffers();
		Offer offer = offers.get(0);

		return findByFinalClientAndOffer(finalClient, offer);
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

	public Card createCardWithStampFromClientIDAndEnterpriseIdDTO(
			ClientIdAndEnterpriseIdDTO clientIDAndEnterpriseIdDTO) {
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

	public Boolean isBeforeLastStamp(ClientIdAndEnterpriseIdDTO clientIDAndEnterpriseIdDTO) {
		Card card = findByClientIdAndEnterpriseIdDTO(clientIDAndEnterpriseIdDTO);
		return card.isAlmostFull();
	}

	public Card save(Card card) {
		return cardRepository.save(card);
	}

	public Boolean isFull(ClientIdAndEnterpriseIdDTO clientIDAndEnterpriseIdDTO) {
		Card card = findByClientIdAndEnterpriseIdDTO(clientIDAndEnterpriseIdDTO);
		if (card == null) {
			return false;
		}
		return card.isFull();
	}

	public void delete(Long id) {
		cardRepository.delete(findById(id));
	}

	public Card removeAllStampsAndSave(Card card) {
		card.getStamps().clear();
		return cardRepository.save(card);
	}

	private Card setRewardReceivedCardAndSave(Card card) {
		card.setRewardReceived(true);
		cardRepository.save(card);
		return card;
	}

	public Card cleanCard(ClientIdAndEnterpriseIdDTO dto) {
		Card card = findByClientIdAndEnterpriseIdDTO(dto);
		card = removeAllStampsAndSave(card);
		return card;
	}

	public Card setRewardReceivedCard(ClientIdAndEnterpriseIdDTO dto) {
		Card card = findByClientIdAndEnterpriseIdDTO(dto);
		card = setRewardReceivedCardAndSave(card);
		return card;
	}

	public Card findByClientIdAndEnterpriseIdDTO(ClientIdAndEnterpriseIdDTO clientIDAndEnterpriseIdDTO) {
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
		System.out.println(enterpriseId);
		Card card = findByFinalClientAndEnterprise(finalClient, enterprise);
		return card;
	}

	public Card createWithStampFromFinalClientAndOffer(FinalClient finalClient, Offer offer) {
		Card card = new Card(finalClient, offer);
		card = cardRepository.save(card);
		stampService.addNewStamp(card);

		return card;
	}

	public ResponseFinalClientDTO createWithStamp(ClientUIAndEnterpriseIdDTO dto) {
		FinalClient finalClient = finalClientService.findByUI(dto.getFinalClienteUniqueIdentifier());
		Enterprise enterprise = enterpriseService.findById(dto.getEnterpriseId());
		Offer offer = enterprise.getOffer();
		Card card = createWithStampFromFinalClientAndOffer(finalClient, offer);
		ResponseFinalClientDTO responseFinalClientDTO = new ResponseFinalClientDTO(card.getFinalClient(), card);
		return responseFinalClientDTO;
	}

	public List<String> errorsTocleanCard(@Valid ClientIdAndEnterpriseIdDTO dto) {
		List<String> errors = new ArrayList<String>();

		eS.addErrorsIfFinalClientByIdNotExist(dto.getFinalClientId(), errors);
		eS.addErrorsIfEnterpriseByIdNotExist(dto.getEnterpriseId(), errors);

		return errors;
	}

	public List<String> errorsToGetReward(ClientIdAndEnterpriseIdDTO dto) {
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

	public List<String> errorsToCreateWithStamp(ClientUIAndEnterpriseIdDTO dto) {
		List<String> errors = new ArrayList<String>();

		eS.addErrorsIfEnterpriseByIdNotExist(dto.getEnterpriseId(), errors);
		eS.addErrorsIfOfferByEnterpriseIdNotExist(dto.getEnterpriseId(), errors);
		eS.addErrorsIfFinalClientByUINotExist(dto.getFinalClienteUniqueIdentifier(), errors);
		eS.addErrorsIfCardByFinalClientUIAndEnterpriseIdExist(dto.getFinalClienteUniqueIdentifier(),
				dto.getEnterpriseId(), errors);

		return errors;
	}

}
