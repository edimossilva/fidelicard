package com.loop.fidelicard.service;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loop.fidelicard.dto.enterprise.EnterpriseDTO;
import com.loop.fidelicard.model.Card;
import com.loop.fidelicard.model.Enterprise;
import com.loop.fidelicard.repository.EnterpriseRepository;
import com.loop.fidelicard.security.model.LoginUser;
import com.loop.fidelicard.security.model.LoginUserService;

@Service
public class EnterpriseService {
	@Autowired
	private EnterpriseRepository enterpriseRepository;
	@Autowired
	private LoginUserService loginUserService;
	@Autowired
	private ErrorsService eS;

	public Iterable<Enterprise> findAll() {
		return enterpriseRepository.findAll();
	}

	public Enterprise save(EnterpriseDTO enterpriseDTO) {
		Enterprise enterprise = new Enterprise(enterpriseDTO);
		LoginUser loginUser = loginUserService.findById(enterpriseDTO.getLoginUserId());

		loginUser.setEnterprise(enterprise);
		enterprise.setOwnerLoginUser(loginUser);

		enterpriseRepository.save(enterprise);
		loginUserService.update(loginUser);
		return enterprise;
	}

	public Enterprise findById(Long enterpriseId) {
		return enterpriseRepository.findById(enterpriseId);
	}

	public Enterprise findByName(String name) {
		return enterpriseRepository.findByName(name);
	}

	public Enterprise findByOwnerLoginUserId(Long loginUserId) {
		LoginUser loginUser = loginUserService.findById(loginUserId);
		return enterpriseRepository.findByOwnerLoginUser(loginUser);
	}

	public Enterprise findByOwnerLoginUserEmail(String loginUserEmail) {
		LoginUser loginUser = loginUserService.findByEmail(loginUserEmail);
		return loginUser.getEnterprise();
	}

	public Enterprise save(Enterprise enterprise) {
		return enterpriseRepository.save(enterprise);
	}

	public List<String> errorsToSave(@Valid EnterpriseDTO enterpriseDTO) {

		List<String> errors = new ArrayList<String>();

		eS.addErrorsIfEnterpriseByNameExist(enterpriseDTO.getEnterpriseName(), errors);
		eS.addErrorsIfLoginUserByIdNotExist(enterpriseDTO.getLoginUserId(), errors);
		eS.addErrosIfEnterprisByOwnerIdFinalClientExist(enterpriseDTO.getLoginUserId(), errors);

		return errors;
	}

	public Enterprise findByOwnerEmail(String enterpriseOwnerEmail) {
		LoginUser loginUser = loginUserService.findByEmail(enterpriseOwnerEmail);
		if (loginUser == null) {
			return null;
		}
		return loginUser.getEnterprise();
	}

	public Enterprise addCardAndSave(Enterprise enterprise, Card card) {
		enterprise.addCard(card);
		return save(enterprise);
	}

}
