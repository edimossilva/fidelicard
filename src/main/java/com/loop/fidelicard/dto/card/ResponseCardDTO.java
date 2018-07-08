package com.loop.fidelicard.dto.card;

import com.loop.fidelicard.dto.finalclient.ResponseFinalClientDTO;
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
	private @NonNull Long enterpriseId;
	private @NonNull Integer atualQuantity;
	private @NonNull Integer maxQuantity;
	private @NonNull ResponseFinalClientDTO finalClient;

	public ResponseCardDTO(Card card) {
		setId(card.getId());
		setOfferId(card.getOffer().getId());
		setEnterpriseId(card.getOffer().getEnterprise().getId());
		setAtualQuantity(card.getNormalizedQuantity());
		setMaxQuantity(card.getOffer().getQuantity());
		setFinalClient(card.getFinalClient().toResponseFinalClientDTO());
	}
}
