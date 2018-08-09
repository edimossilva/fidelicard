package com.loop.fidelicard.dto.offer;

import com.loop.fidelicard.dto.date.DateDTO;
import com.loop.fidelicard.model.Offer;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class ResponseOfferDTO {
	private Long id;
	private String name;
	private String description;
	private Integer quantity;
	private DateDTO createdAt;

	public ResponseOfferDTO(Offer offer) {
		setId(offer.getId());
		setName(offer.getName());
		setDescription(offer.getDescription());
		setQuantity(offer.getQuantity());
		setCreatedAt(new DateDTO(offer.getCreatedAt()));

	}

}
