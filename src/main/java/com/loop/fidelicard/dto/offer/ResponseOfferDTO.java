package com.loop.fidelicard.dto.offer;

import com.loop.fidelicard.model.Offer;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class ResponseOfferDTO {
	private @NonNull Long id;
	private @NonNull String name;
	private @NonNull String description;
	private @NonNull Integer quantity;

	public ResponseOfferDTO(Offer offer) {
		setId(offer.getId());
		setName(offer.getName());
		setDescription(offer.getDescription());
		setQuantity(offer.getQuantity());
	}

}
