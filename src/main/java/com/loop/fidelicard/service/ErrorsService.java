package com.loop.fidelicard.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loop.fidelicard.controller.validator.MyValidator;
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

	public void addErrosIfEnterprisByOwnerEmailFinalClientNotExist(String enterpriseOwnerEmail, List<String> errors) {
		String errorMessage = "Nao existe empresa com dono [" + enterpriseOwnerEmail + "]";
		Enterprise enterprise = enterpriseService.findByOwnerEmail(enterpriseOwnerEmail);
		MyValidator.addErrorsWhenNull(errors, errorMessage, enterprise);
	}

	public void addErrosIfEnterprisByIdNotExist(Long enterpriseId, List<String> errors) {
		String errorMessage = "Nao existe empresa com id [" + enterpriseId + "]";
		Enterprise enterprise = enterpriseService.findById(enterpriseId);
		MyValidator.addErrorsWhenNull(errors, errorMessage, enterprise);

	}

	public void addErrorsIfOfferByDescriptionExist(String offerDescription, List<String> errors) {
		String errorMessage = "Ja existe oferta com o descricao [" + offerDescription + "]";
		Offer offer = offerService.findByDescriprion(offerDescription);
		MyValidator.addErrorsWhenNotNull(errors, errorMessage, offer);
	}

	public void addErrosIfFinalClientByUIExist(String finalClientUI, List<String> errors) {
		String errorMessage = "Ja existe cliente com o identificador [" + finalClientUI + "]";
		FinalClient finalClient = finalClientService.findByUI(finalClientUI);
		MyValidator.addErrorsWhenNotNull(errors, errorMessage, finalClient);
	}

	public void addErrosIfFinalClientByUINotExist(String finalClientUI, List<String> errors) {
		String errorMessage = "Nao existe cliente com o identificador [" + finalClientUI + "]";
		FinalClient finalClient = finalClientService.findByUI(finalClientUI);
		MyValidator.addErrorsWhenNull(errors, errorMessage, finalClient);

	}

	public void addErrosIfFinalClientByIdNotExist(Long finalClientId, List<String> errors) {
		String errorMessage = "Nao existe cliente com o id [" + finalClientId + "]";
		FinalClient finalClient = finalClientService.findById(finalClientId);
		MyValidator.addErrorsWhenNull(errors, errorMessage, finalClient);
	}

	public void addErrosIfFinalClientByEmailExist(String email, List<String> errors) {
		String errorMessage = "Ja existe cliente com o email [" + email + "]";
		FinalClient finalClient = finalClientService.findByEmail(email);
		MyValidator.addErrorsWhenNotNull(errors, errorMessage, finalClient);
	}

}
