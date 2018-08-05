package com.loop.fidelicard.dto.consumer;

import com.loop.fidelicard.model.Offer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConsumerOfferDTO {
	private Long id;
	private String name;
	private String description;
	private Integer quantity;

	public ConsumerOfferDTO(Offer offer) {
		setId(offer.getId());
		setName(offer.getName());
		setDescription(offer.getDescription());
		setQuantity(offer.getQuantity());
	}

}
