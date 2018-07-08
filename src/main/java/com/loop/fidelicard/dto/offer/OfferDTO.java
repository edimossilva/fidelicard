package com.loop.fidelicard.dto.offer;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OfferDTO {
	@NotEmpty(message = "O atributo [name] da oferta nao pode ser vazio")
	private String name;
	@NotEmpty(message = "O atributo [description] da oferta nao pode ser vazio")
	private String description;
	@NotNull(message = "O atributo [quantity] da oferta nao pode ser vazio")
	private Integer quantity;
	@NotNull(message = "O atributo [enterpriseId] da oferta nao pode ser vazio")
	private Long enterpriseId;

}
