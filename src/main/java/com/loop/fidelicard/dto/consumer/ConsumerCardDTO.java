package com.loop.fidelicard.dto.consumer;

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
public class ConsumerCardDTO {
	private Long cardId;
	private Long finalClientId;
	private Integer atualStampQuantity;
	private Integer maxStampQuantity;
	private ConsumerEnterpriseDTO enterprise;
	private ConsumerOfferDTO offer;
	private DateDTO createdAt;

	public ConsumerCardDTO(Card card) {
		setCardId(card.getId());
		setFinalClientId(card.getFinalClient().getId());
		setAtualStampQuantity(card.getNormalizedQuantity());
		setMaxStampQuantity(card.getOffer().getQuantity());
		setEnterprise(card.getOffer().getEnterprise().toConsumerEnterpriseDTO());
		setOffer(card.getOffer().toConsumerOfferDTO());
		setCreatedAt(new DateDTO(card.getCreatedAt()));

	}

}
