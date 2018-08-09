package com.loop.fidelicard.dto.consumer;

import com.loop.fidelicard.dto.date.DateDTO;
import com.loop.fidelicard.model.Offer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConsumerOfferDTO {
	private Long offerId;
	private String offerName;
	private String offerDescription;
	private Integer offerQuantity;
	private DateDTO createdAt;

	public ConsumerOfferDTO(Offer offer) {
		setOfferId(offer.getId());
		setOfferName(offer.getName());
		setOfferDescription(offer.getDescription());
		setOfferQuantity(offer.getQuantity());
		setCreatedAt(new DateDTO(offer.getCreatedAt()));

	}

}
