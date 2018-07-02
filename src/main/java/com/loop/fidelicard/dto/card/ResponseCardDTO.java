package com.loop.fidelicard.dto.card;

import com.loop.fidelicard.model.Card;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class ResponseCardDTO {
	private @NonNull Long id;
	private @NonNull Long offerId;
	private @NonNull Long finalClientId;
	private @NonNull Long quantity;

	public ResponseCardDTO(Card card) {
		setId(card.getId());
		setOfferId(card.getOffer().getId());
		setFinalClientId(card.getFinalClient().getId());
		setQuantity((long) card.getNormalizedQuantity());
	}
}
