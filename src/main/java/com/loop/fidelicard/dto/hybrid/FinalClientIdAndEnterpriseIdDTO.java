package com.loop.fidelicard.dto.hybrid;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class FinalClientIdAndEnterpriseIdDTO {
	@NotNull(message = "O atributo [finalClientId] nao pode ser vazio")
	private Long finalClientId;
	@NotNull(message = "O atributo [enterpriseId] nao pode ser vazio")
	private Long enterpriseId;
}
