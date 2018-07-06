package com.loop.fidelicard.dto.hybrid;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ClientIDAndEnterpriseIdDTO {
	@NotNull(message = "O atributo [finalClientId] nao pode ser vazio")
	private Long finalClientId;
	@NotNull(message = "O atributo [enterpriseId] nao pode ser vazio")
	private Long enterpriseId;
}
