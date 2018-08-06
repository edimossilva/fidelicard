package com.loop.fidelicard.dto.finalclient;

import com.loop.fidelicard.dto.card.ResponseCardDTO;
import com.loop.fidelicard.model.Card;
import com.loop.fidelicard.model.Enterprise;
import com.loop.fidelicard.model.FinalClient;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class ResponseFinalClientDTO {
	private @NonNull Long finalClientId;
	private @NonNull String finalClientUI;
	private @NonNull String finalClientEmail;
	private ResponseCardDTO card;

	public ResponseFinalClientDTO(FinalClient fc, Enterprise enterprise) {
		fillFinalClient(fc);
		Card card = fc.findCardByEnterpriseId(enterprise.getId());
		if (card != null) {
			setCard(card.toResponseCardDTO());
		}
	}

	public ResponseFinalClientDTO(FinalClient fc, Card card) {
		fillFinalClient(fc);
		setCard(card.toResponseCardDTO());
	}

	private void fillFinalClient(FinalClient fc) {
		setFinalClientId(fc.getId());
		setFinalClientUI(fc.getUniqueIdentifier());
		setFinalClientEmail(fc.getEmail());
	}

	public ResponseFinalClientDTO(Card card) {
		fillFinalClient(card.getFinalClient());
		setCard(card.toResponseCardDTO());
	}

}
