package com.loop.fidelicard.dto.consumer;

import java.util.List;

import com.loop.fidelicard.model.FinalClient;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConsumerFinalClientDTO {
	private Long finalClientId;
	private String finalClientUI;
	private String finalClientEmail;
	private List<ConsumerCardDTO> cards;

	public ConsumerFinalClientDTO(FinalClient fc) {
		setFinalClientId(fc.getId());
		setFinalClientUI(fc.getUniqueIdentifier());
		setFinalClientEmail(fc.getEmail());
		setCards(fc.getConsumerCardsDTO());
	}

}
