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
	private @NonNull Long cardId;
	private @NonNull Long offerId;
	private @NonNull Long enterpriseId;
	private @NonNull Long finalClientId;
	private @NonNull Integer currentStampQuantity;
	private @NonNull Integer maxStampQuantity;
	// private @NonNull Integer totalStampQuantity;

	// private @NonNull ResponseFinalClientDTO finalClient;

	public ResponseCardDTO(Card card) {
		setCardId(card.getId());
		setOfferId(card.getOffer().getId());
		setEnterpriseId(card.getOffer().getEnterprise().getId());
		setFinalClientId(card.getFinalClient().getId());
		setCurrentStampQuantity(card.getNormalizedQuantity());
		setMaxStampQuantity(card.getOffer().getQuantity());
		// setTotalStampQuantity(card.getStamps().size());
		// setFinalClient(card.getFinalClient().toResponseFinalClientDTO());
	}
}
