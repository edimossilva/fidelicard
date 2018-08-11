package com.loop.fidelicard.service;

import java.util.ArrayList;
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

	public List<FinalClient> findAllByEnterpriseId(long enterpriseId) {
		Enterprise enterprise = enterpriseService.findById(enterpriseId);

		return enterprise.getFinalClients();
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

	public FinalClient joinFinalClientAndEnterprise(FinalClient finalClient, Enterprise enterprise) {
		enterprise.addFinalClient(finalClient);
		finalClient.addEnterprise(enterprise);
		finalClient = finalClientRepository.save(finalClient);
		enterpriseService.save(enterprise);

		return finalClient;
	}

	public ResponseFinalClientDTO findFinalClientResponseDTOByUIAndEnterpriseId(ClientUIAndEnterpriseIdDTO dto) {
		FinalClient finalClient = findByUI(dto.getFinalClientUI());
		Enterprise enterprise = enterpriseService.findById(dto.getEnterpriseId());
		ResponseFinalClientDTO responseFinalClientDTO = new ResponseFinalClientDTO(finalClient, enterprise);
		return responseFinalClientDTO;
	}

	public Card findFinalClientCardByUIAndEnterpriseId(ClientUIAndEnterpriseIdDTO dto) {
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
		Enterprise enterprise = enterpriseService.findById(dto.getEnterpriseId());
		Offer offer = offerService.findByEnterprise(enterprise);

		FinalClient finalClient = new FinalClient(dto);
		finalClient = joinFinalClientAndEnterprise(finalClient, enterprise);
		Card card = cardService.createWithStampFromFinalClientAndOffer(finalClient, offer);

		ResponseFinalClientDTO responseFinalClientDTO = new ResponseFinalClientDTO(card);

		return responseFinalClientDTO;
	}

	public List<String> errorsToCreateWithStamp(FinalClientAndEnterpriseIdDTO dto) {
		List<String> errors = new ArrayList<String>();

		eS.addErrorsIfFinalClientByUIExist(dto.getFinalClientUI(), errors);
		// eS.addErrorsIfFinalClientByEmailExist(dto.getFinalClientEmail(), errors);
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

}
