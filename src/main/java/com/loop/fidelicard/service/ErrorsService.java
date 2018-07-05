package com.loop.fidelicard.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loop.fidelicard.controller.validator.MyValidator;
import com.loop.fidelicard.dto.enterprise.EnterpriseDTO;
import com.loop.fidelicard.model.Enterprise;
import com.loop.fidelicard.model.Offer;
import com.loop.fidelicard.security.model.LoginUser;
import com.loop.fidelicard.security.service.LoginUserService;

@Service
public class ErrorsService {
	@Autowired
	private EnterpriseService enterpriseService;
	// @Autowired
	// private FinalClientService finalClientService;
	@Autowired
	private LoginUserService loginUserService;

	@Autowired
	OfferService offerService;

	public void addErrorsIfLoginUserByIdNotExist(EnterpriseDTO enterpriseDTO, List<String> errors) {
		String loginUseNotFoundMessage = "nao existe loginUser com o id [" + enterpriseDTO.getLoginUserId() + "]";
		LoginUser loginUser = loginUserService.findById(enterpriseDTO.getLoginUserId());
		MyValidator.addErrorsWhenNull(errors, loginUseNotFoundMessage, loginUser);
	}

	public void addErrorsIfEnterpriseByNameExist(String enterpriseName, List<String> errors) {
		String enterpriseNotUniqueNameMessage = "Ja existe empresa com o nome [" + enterpriseName + "]";
		Enterprise enterprise = enterpriseService.findByName(enterpriseName);
		MyValidator.addErrorsWhenNotNull(errors, enterpriseNotUniqueNameMessage, enterprise);
	}

	public void addErrorsIfOfferByDescriptionExist(String offerDescription, List<String> errors) {
		String offerNotUniqueDescriptonMessage = "Ja existe oferta com o descricao [" + offerDescription + "]";
		Offer offer = offerService.findByDescriprion(offerDescription);
		MyValidator.addErrorsWhenNotNull(errors, offerNotUniqueDescriptonMessage, offer);
	}
}
