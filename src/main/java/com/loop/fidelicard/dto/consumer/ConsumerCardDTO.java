package com.loop.fidelicard.dto.consumer;

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
	private Long id;
	private Long finalClientId;
	private Integer atualStampQuantity;
	private Integer maxStampQuantity;
	private ConsumerEnterpriseDTO enterprise;
	private ConsumerOfferDTO offer;

	public ConsumerCardDTO(Card card) {
		setId(card.getId());
		setFinalClientId(card.getFinalClient().getId());
		setAtualStampQuantity(card.getNormalizedQuantity());
		setMaxStampQuantity(card.getOffer().getQuantity());
		setEnterprise(card.getOffer().getEnterprise().toConsumerEnterpriseDTO());
		setOffer(card.getOffer().toConsumerOfferDTO());

	}

}
