package com.loop.fidelicard.dto.offer;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OfferDTO {
	@NotEmpty(message = "O atributo [offerName] da oferta nao pode ser vazio")
	private String offerName;
	@NotEmpty(message = "O atributo [offerDescription] da oferta nao pode ser vazio")
	private String offerDescription;
	@NotNull(message = "O atributo [offerQuantity] da oferta nao pode ser vazio")
	private Integer offerQuantity;
	@NotNull(message = "O atributo [enterpriseId] da oferta nao pode ser vazio")
	private Long enterpriseId;

}
