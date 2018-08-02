package com.loop.fidelicard.dto.hybrid;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class ClientUIAndEnterpriseIdDTO {
	@NotEmpty(message = "O atributo [finalClientUI] nao pode ser vazio")
	private String finalClientUI;
	@NotNull(message = "O atributo [enterpriseId] nao pode ser vazio")
	private Long enterpriseId;

}
