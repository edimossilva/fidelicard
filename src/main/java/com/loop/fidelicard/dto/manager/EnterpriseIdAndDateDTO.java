package com.loop.fidelicard.dto.manager;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnterpriseIdAndDateDTO {
	@NotNull(message = "O atributo [enterpriseId] nao pode ser vazio")
	private Long enterpriseId;

	@NotNull(message = "O atributo [day] nao pode ser vazio")
	private Long day;

	@NotNull(message = "O atributo [month] nao pode ser vazio")
	private Long month;

	@NotNull(message = "O atributo [year] nao pode ser vazio")
	private Long year;
}
