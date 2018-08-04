package com.loop.fidelicard.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loop.fidelicard.controller.validator.MyValidator;
import com.loop.fidelicard.model.Card;
import com.loop.fidelicard.model.Enterprise;
import com.loop.fidelicard.model.FinalClient;
import com.loop.fidelicard.model.Offer;
import com.loop.fidelicard.security.model.LoginUser;
import com.loop.fidelicard.security.service.LoginUserService;

@Service
public class ErrorsService {
	@Autowired
	private EnterpriseService enterpriseService;
	@Autowired
	private FinalClientService finalClientService;
	@Autowired
	private LoginUserService loginUserService;
	@Autowired
	private CardService cardService;

	@Autowired
	OfferService offerService;

	public void addErrorsIfLoginUserByIdNotExist(Long loginUserId, List<String> errors) {
		String errorMessage = "nao existe loginUser com o id [" + loginUserId + "]";
		LoginUser loginUser = loginUserService.findById(loginUserId);
		MyValidator.addErrorsWhenNull(errors, errorMessage, loginUser);
	}

	public void addErrorsIfLoginUserByEmailExist(String loginUserEmail, List<String> errors) {
		String errorMessage = "Ja existe loginUser com o email [" + loginUserEmail + "]";
		LoginUser loginUser = loginUserService.findByEmail(loginUserEmail);
		MyValidator.addErrorsWhenNotNull(errors, errorMessage, loginUser);
	}

	public void addErrorsIfEnterpriseByNameExist(String enterpriseName, List<String> errors) {
		String errorMessage = "Ja existe empresa com o nome [" + enterpriseName + "]";
		Enterprise enterprise = enterpriseService.findByName(enterpriseName);
		MyValidator.addErrorsWhenNotNull(errors, errorMessage, enterprise);
	}

	public void addErrosIfEnterprisByOwnerEmailFinalClientExist(String enterpriseOwnerEmail, List<String> errors) {
		String errorMessage = "O loginUser com email [" + enterpriseOwnerEmail + "] ja possui uma empresa";
		Enterprise enterprise = enterpriseService.findByOwnerEmail(enterpriseOwnerEmail);
		MyValidator.addErrorsWhenNotNull(errors, errorMessage, enterprise);

	}

	public void addErrosIfEnterprisByOwnerIdFinalClientExist(Long loginUserId, List<String> errors) {
		LoginUser ownerLoginUser = loginUserService.findById(loginUserId);
		String errorMessage = "O loginUser com id [" + loginUserId + "] ja possui uma empresa";
		Enterprise enterprise = ownerLoginUser.getEnterprise();
		MyValidator.addErrorsWhenNotNull(errors, errorMessage, enterprise);

	}

	public void addErrorsIfEnterprisByOwnerEmailFinalClientNotExist(String enterpriseOwnerEmail, List<String> errors) {
		String errorMessage = "Nao existe empresa com dono [" + enterpriseOwnerEmail + "]";
		Enterprise enterprise = enterpriseService.findByOwnerEmail(enterpriseOwnerEmail);
		MyValidator.addErrorsWhenNull(errors, errorMessage, enterprise);
	}

	public void addErrorsIfEnterpriseByIdNotExist(Long enterpriseId, List<String> errors) {
		String errorMessage = "Nao existe empresa com id [" + enterpriseId + "]";
		Enterprise enterprise = enterpriseService.findById(enterpriseId);
		MyValidator.addErrorsWhenNull(errors, errorMessage, enterprise);

	}

	public void addErrorsIfOfferByDescriptionExist(String offerDescription, List<String> errors) {
		String errorMessage = "Ja existe oferta com o descricao [" + offerDescription + "]";
		Offer offer = offerService.findByDescriprion(offerDescription);
		MyValidator.addErrorsWhenNotNull(errors, errorMessage, offer);
	}

	public void addErrorsIfOfferByEnterpriseIdExist(Long enterpriseId, List<String> errors) {
		String errorMessage = "Ja existe oferta para a empresa com id [" + enterpriseId
				+ "], atualmente cada empresa so pode ter uma oferta";
		Offer offer = offerService.findByEnterpriseId(enterpriseId);
		MyValidator.addErrorsWhenNotNull(errors, errorMessage, offer);
	}

	public void addErrorsIfOfferByEnterpriseIdNotExist(Long enterpriseId, List<String> errors) {
		String errorMessage = "Nao existe oferta para a empresa com id [" + enterpriseId + "]";
		Offer offer = offerService.findByEnterpriseId(enterpriseId);
		MyValidator.addErrorsWhenNull(errors, errorMessage, offer);
	}

	public void addErrorsIfFinalClientByUIExist(String finalClientUI, List<String> errors) {
		String errorMessage = "Ja existe cliente com o identificador [" + finalClientUI + "]";
		FinalClient finalClient = finalClientService.findByUI(finalClientUI);
		MyValidator.addErrorsWhenNotNull(errors, errorMessage, finalClient);
	}

	public void addErrorsIfFinalClientByUINotExist(String finalClientUI, List<String> errors) {
		String errorMessage = "Nao existe cliente com o identificador [" + finalClientUI + "]";
		FinalClient finalClient = finalClientService.findByUI(finalClientUI);
		MyValidator.addErrorsWhenNull(errors, errorMessage, finalClient);

	}

	public void addErrorsIfFinalClientByIdNotExist(Long finalClientId, List<String> errors) {
		String errorMessage = "Nao existe cliente com o id [" + finalClientId + "]";
		FinalClient finalClient = finalClientService.findById(finalClientId);
		MyValidator.addErrorsWhenNull(errors, errorMessage, finalClient);
	}

	public void addErrorsIfFinalClientByEmailExist(String email, List<String> errors) {
		String errorMessage = "Ja existe cliente com o email [" + email + "]";
		FinalClient finalClient = finalClientService.findByEmail(email);
		MyValidator.addErrorsWhenNotNull(errors, errorMessage, finalClient);
	}

	public void addErrorsIfCardByFinalClientUIAndEnterpriseIdExist(String finalClientUI, Long enterpriseId,
			List<String> errors) {
		String errorMessage = "Ja existe cartao para o cliente com UI [" + finalClientUI + "] na empresa com id ["
				+ enterpriseId + "]";
		Card card = cardService.findByFinalClientUIAndEnterpriseId(finalClientUI, enterpriseId);
		MyValidator.addErrorsWhenNotNull(errors, errorMessage, card);

	}

	public void addErrorsIfCardByFinalClientUIAndEnterpriseIdNotExist(String finalClientUI,
			Long enterpriseId, List<String> errors) {
		String errorMessage = "Nao existe cartao para o cliente com UI [" + finalClientUI + "] na empresa com id ["
				+ enterpriseId + "]";
		Card card = cardService.findByFinalClientUIAndEnterpriseId(finalClientUI, enterpriseId);
		MyValidator.addErrorsWhenNull(errors, errorMessage, card);

	}

}
