package com.loop.fidelicard.dto.card;

import com.loop.fidelicard.model.Card;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class ResponseCardDTO {
	private @NonNull Long id;
	private @NonNull Long offerId;
	private @NonNull Long finalClientId;
	private @NonNull Integer atualQuantity;
	private @NonNull Integer maxQuantity;

	public ResponseCardDTO(Card card) {
		setId(card.getId());
		setOfferId(card.getOffer().getId());
		setFinalClientId(card.getFinalClient().getId());
		setAtualQuantity(card.getNormalizedQuantity());
		setMaxQuantity(card.getOffer().getQuantity());
	}
}
