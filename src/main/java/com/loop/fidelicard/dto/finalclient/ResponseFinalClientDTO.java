package com.loop.fidelicard.dto.finalclient;

import java.util.List;

import com.loop.fidelicard.dto.card.ResponseCardDTO;
import com.loop.fidelicard.model.Card;
import com.loop.fidelicard.model.Enterprise;
import com.loop.fidelicard.model.FinalClient;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class ResponseFinalClientDTO {
	private @NonNull Long id;
	private @NonNull String uniqueIdentifier;
	private @NonNull String email;
	private List<ResponseCardDTO> cards;

	public ResponseFinalClientDTO(FinalClient fc) {
		fillFinalClient(fc);
		setCards(fc.getResponseCardsDTO());
	}

	public ResponseFinalClientDTO(FinalClient fc, Enterprise enterprise) {
		fillFinalClient(fc);
		Card card = fc.findCardByEnterpriseId(enterprise.getId());
		if (card != null) {
			setCards(card.toResponseCardDTOList());
		}
	}

	public ResponseFinalClientDTO(FinalClient fc, Card card) {
		fillFinalClient(fc);
		setCards(card.toResponseCardDTOList());
	}

	private void fillFinalClient(FinalClient fc) {
		setId(fc.getId());
		setUniqueIdentifier(fc.getUniqueIdentifier());
		setEmail(fc.getEmail());
	}

	public ResponseFinalClientDTO(Card card) {
		fillFinalClient(card.getFinalClient());
		setCards(card.toResponseCardDTOList());
	}

}
