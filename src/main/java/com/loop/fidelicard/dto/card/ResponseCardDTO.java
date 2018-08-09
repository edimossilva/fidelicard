package com.loop.fidelicard.dto.card;

import com.loop.fidelicard.dto.date.DateDTO;
import com.loop.fidelicard.model.Card;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class ResponseCardDTO {
	private Long cardId;
	private Long offerId;
	private Long enterpriseId;
	private Long finalClientId;
	private Integer currentStampQuantity;
	private Integer maxStampQuantity;
	private DateDTO createdAt;

	public ResponseCardDTO(Card card) {
		setCardId(card.getId());
		setOfferId(card.getOffer().getId());
		setEnterpriseId(card.getOffer().getEnterprise().getId());
		setFinalClientId(card.getFinalClient().getId());
		setCurrentStampQuantity(card.getNormalizedQuantity());
		setMaxStampQuantity(card.getOffer().getQuantity());
		setCreatedAt(new DateDTO(card.getCreatedAt()));
	}
}
