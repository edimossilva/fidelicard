package com.loop.fidelicard.dto.consumer;

import java.util.List;

import com.loop.fidelicard.model.FinalClient;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConsumerFinalClientDTO {
	private Long id;
	private String uniqueIdentifier;
	private String email;
	private List<ConsumerCardDTO> cards;

	public ConsumerFinalClientDTO(FinalClient fc) {
		setId(fc.getId());
		setUniqueIdentifier(fc.getUniqueIdentifier());
		setEmail(fc.getEmail());
		setCards(fc.getConsumerCardsDTO());
	}

}
