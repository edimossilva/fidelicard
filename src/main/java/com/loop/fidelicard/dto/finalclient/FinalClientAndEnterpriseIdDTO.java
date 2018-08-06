package com.loop.fidelicard.dto.finalclient;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FinalClientAndEnterpriseIdDTO {
	@NotEmpty(message = "O atributo [finalClientUI]  nao pode ser vazio")
	private String finalClientUI;

	@NotEmpty(message = "O atributo [finalClientEmail] nao pode ser vazio")
	private String finalClientEmail;

	@NotNull(message = "O atributo [enterpriseId] nao pode ser vazio")
	private Long enterpriseId;
}
