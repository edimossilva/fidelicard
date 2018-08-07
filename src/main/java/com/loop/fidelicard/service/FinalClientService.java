package com.loop.fidelicard.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loop.fidelicard.dto.consumer.ConsumerFinalClientDTO;
import com.loop.fidelicard.dto.finalclient.FinalClientAndEnterpriseIdDTO;
import com.loop.fidelicard.dto.finalclient.ResponseFinalClientDTO;
import com.loop.fidelicard.dto.finalclient.UIDTO;
import com.loop.fidelicard.dto.hybrid.ClientUIAndEnterpriseIdDTO;
import com.loop.fidelicard.model.Card;
import com.loop.fidelicard.model.Enterprise;
import com.loop.fidelicard.model.FinalClient;
import com.loop.fidelicard.model.Offer;
import com.loop.fidelicard.repository.FinalClientRepository;

@Service
public class FinalClientService {
	@Autowired
	private FinalClientRepository finalClientRepository;
	@Autowired
	private OfferService offerService;
	@Autowired
	private EnterpriseService enterpriseService;
	@Autowired
	private CardService cardService;
	@Autowired
	private ErrorsService eS;

	public List<FinalClient> findAll() {
		return finalClientRepository.findAll();
	}

	public FinalClient findById(Long finalClientId) {

		return finalClientRepository.findById(finalClientId);
	}

	public FinalClient findByEmail(String email) {
		return finalClientRepository.findByEmail(email);
	}

	public FinalClient findByUI(String uniqueIdentifir) {

		return finalClientRepository.findByUniqueIdentifier(uniqueIdentifir);
	}

	// public FinalClient findClientByUICardInEnterprise(ClientUIAndEnterpriseIdDTO
	// clientUIAndEnterpriseIdDTO) {
	// FinalClient finalClient =
	// findByUI(clientUIAndEnterpriseIdDTO.getFinalClienteUniqueIdentifier());
	// Enterprise enterprise =
	// enterpriseService.findById(clientUIAndEnterpriseIdDTO.getEnterpriseId());
	// List<Offer> offers = offerService.findAllByEnterprise(enterprise);
	// Offer offer = offerService.findOfferByFinalClient(offers, finalClient);
	// if (offer != null) {
	// return finalClient;
	// }
	// return null;
	// }

	public void save(FinalClient finalClient) {
		finalClientRepository.save(finalClient);
	}

	public ResponseFinalClientDTO findClientResponseDTOByUIAndEnterpriseId(ClientUIAndEnterpriseIdDTO dto) {
		FinalClient finalClient = findByUI(dto.getFinalClientUI());
		Enterprise enterprise = enterpriseService.findById(dto.getEnterpriseId());
		ResponseFinalClientDTO responseFinalClientDTO = new ResponseFinalClientDTO(finalClient, enterprise);
		return responseFinalClientDTO;
	}

	public Card findClientCardByUIAndEnterpriseId(ClientUIAndEnterpriseIdDTO dto) {
		FinalClient finalClient = findByUI(dto.getFinalClientUI());
		Enterprise enterprise = enterpriseService.findById(dto.getEnterpriseId());
		Card card = cardService.findByFinalClientAndEnterprise(finalClient, enterprise);
		return card;
	}

	public ConsumerFinalClientDTO findConsumerFinalClientByUI(String uniqueIdentifier) {
		FinalClient finalClient = findByUI(uniqueIdentifier);
		return finalClient.toConsumerFinalClientDTO();
	}

	public ResponseFinalClientDTO createWithStamp(FinalClientAndEnterpriseIdDTO dto) {
		FinalClient finalClient = new FinalClient(dto);
		save(finalClient);

		Offer offer = offerService.findByEnterpriseId(dto.getEnterpriseId());
		Card card = cardService.createWithStampFromFinalClientAndOffer(finalClient, offer);

		ResponseFinalClientDTO responseFinalClientDTO = new ResponseFinalClientDTO(card);

		return responseFinalClientDTO;
	}

	public List<String> errorsToCreateWithStamp(FinalClientAndEnterpriseIdDTO dto) {
		List<String> errors = new ArrayList<String>();

		eS.addErrorsIfFinalClientByUIExist(dto.getFinalClientUI(), errors);
//		eS.addErrorsIfFinalClientByEmailExist(dto.getFinalClientEmail(), errors);
		eS.addErrorsIfEnterpriseByIdNotExist(dto.getEnterpriseId(), errors);
		eS.addErrorsIfOfferByEnterpriseIdNotExist(dto.getEnterpriseId(), errors);
		eS.addErrorsIfCardByFinalClientUIAndEnterpriseIdExist(dto.getFinalClientUI(), dto.getEnterpriseId(), errors);

		return errors;
	}

	public List<String> errorsToExistClientByUIAndEnterpriseId(ClientUIAndEnterpriseIdDTO dto) {
		List<String> errors = new ArrayList<String>();
		eS.addErrorsIfFinalClientByUINotExist(dto.getFinalClientUI(), errors);
		eS.addErrorsIfEnterpriseByIdNotExist(dto.getEnterpriseId(), errors);
		eS.addErrorsIfOfferByEnterpriseIdNotExist(dto.getEnterpriseId(), errors);
		// eS.addErrorsIfCardByFinalClientUIAndEnterpriseIdNotExist(dto.getFinalClienteUniqueIdentifier(),
		// dto.getEnterpriseId(), errors);
		return errors;
	}

	public List<String> errorsToGetAllCardsByUI(UIDTO dto) {
		List<String> errors = new ArrayList<String>();
		eS.addErrorsIfFinalClientByUINotExist(dto.getFinalClientUI(), errors);
		return errors;
	}

	public HashSet<FinalClient> findAllByEnterpriseId(long id) {
		List<FinalClient> finalClients = findAll();
		HashSet<FinalClient> finalClientsByEnterprise = new HashSet<>();
		for (FinalClient finalClient : finalClients) {
			for (Card card : finalClient.getCards()) {
				if (card.getEnterprise().getId() == id) {
					finalClientsByEnterprise.add(finalClient);
					break;
				}
			}
		}
		return finalClientsByEnterprise;
	}

}
