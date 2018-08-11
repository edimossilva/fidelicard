package com.loop.fidelicard.service;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loop.fidelicard.dto.offer.OfferDTO;
import com.loop.fidelicard.model.Enterprise;
import com.loop.fidelicard.model.Offer;
import com.loop.fidelicard.repository.OfferRepository;

@Service
public class OfferService {
	@Autowired
	private OfferRepository offerRepository;
	@Autowired
	private EnterpriseService enterpriseService;
	@Autowired
	private ErrorsService eS;

	public Iterable<Offer> findAll() {
		return offerRepository.findAll();
	}

	public List<Offer> findAllByEnterprise(Enterprise enterprise) {
		return offerRepository.findAllByEnterprise(enterprise);
	}

	public Offer save(OfferDTO offerDTO) {
		Enterprise enterprise = enterpriseService.findById(offerDTO.getEnterpriseId());
		Offer offer = new Offer(offerDTO);
		offer.setEnterprise(enterprise);
		offerRepository.save(offer);
		return offer;
	}

	public Offer findById(Long offerId) {
		return offerRepository.findById(offerId);
	}

	public Offer findByDescriprion(String offerDescription) {
		return offerRepository.findByDescription(offerDescription);
	}

	public Offer findByEnterprise(Enterprise enterprise) {
		if (enterprise != null) {
			if (enterprise.getOffers().size() > 0) {
				return enterprise.getOffers().get(0);
			}
		}
		return null;
	}

	public Offer findByEnterpriseId(Long enterpriseId) {
		Enterprise enterprise = enterpriseService.findById(enterpriseId);
		if (enterprise != null) {
			if (enterprise.getOffers().size() > 0) {
				return enterprise.getOffers().get(0);
			}
		}
		return null;
	}

	public void save(Offer offer) {
		offerRepository.save(offer);
	}

	public List<String> errorsToSave(@Valid OfferDTO offerDTO) {
		List<String> errors = new ArrayList<String>();

		eS.addErrorsIfEnterpriseByIdNotExist(offerDTO.getEnterpriseId(), errors);
		eS.addErrorsIfOfferByEnterpriseIdExist(offerDTO.getEnterpriseId(), errors);

		return errors;
	}

}
