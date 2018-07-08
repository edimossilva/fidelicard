package com.loop.fidelicard.service;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loop.fidelicard.dto.enterprise.EnterpriseDTO;
import com.loop.fidelicard.dto.finalclient.FinalClientToEnterpriseDTO;
import com.loop.fidelicard.model.Enterprise;
import com.loop.fidelicard.model.FinalClient;
import com.loop.fidelicard.repository.EnterpriseRepository;
import com.loop.fidelicard.security.model.LoginUser;
import com.loop.fidelicard.security.service.LoginUserService;

@Service
public class EnterpriseService {
	@Autowired
	private EnterpriseRepository enterpriseRepository;
	@Autowired
	private FinalClientService finalClientService;
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

	public Enterprise addFinalClientToEnterprise(FinalClientToEnterpriseDTO enterpriseFinalClientDTO) {
		FinalClient finalCLient = finalClientService.findById(enterpriseFinalClientDTO.getFinalClientId());
		Enterprise enterprise = enterpriseRepository.findById(enterpriseFinalClientDTO.getEnterpriseId());
		enterprise.addFinalClient(finalCLient);
		enterpriseRepository.save(enterprise);
		return enterprise;
	}

	public Enterprise findById(Long enterpriseId) {

		return enterpriseRepository.findById(enterpriseId);
	}

	public Enterprise findByOwnerLoginUser(Long loginUserId) {
		LoginUser loginUser = loginUserService.findById(loginUserId);

		return enterpriseRepository.findByOwnerLoginUser(loginUser);
	}

	public Enterprise findByOwnerLoginUserEmail(String loginUserEmail) {
		LoginUser loginUser = loginUserService.findByEmail(loginUserEmail);
		return loginUser.getEnterprise();
	}

	public void save(Enterprise enterprise) {
		enterpriseRepository.save(enterprise);
	}

	public List<String> errorsToSave(@Valid EnterpriseDTO enterpriseDTO) {

		List<String> errors = new ArrayList<String>();

		eS.addErrorsIfEnterpriseByNameExist(enterpriseDTO.getName(), errors);
		eS.addErrorsIfLoginUserByIdNotExist(enterpriseDTO.getLoginUserId(), errors);

		return errors;
	}

	public Enterprise findByName(String name) {
		return enterpriseRepository.findByName(name);
	}

	public Enterprise findByOwnerEmail(String enterpriseOwnerEmail) {
		LoginUser loginUser = loginUserService.findByEmail(enterpriseOwnerEmail);
		if (loginUser == null) {
			return null;
		}
		return loginUser.getEnterprise();
	}
}
