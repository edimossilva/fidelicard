package com.loop.fidelicard.dto.enterprise;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnterpriseDTO {
	@NotEmpty(message = "O atributo [name] da empresa nao pode ser vazio")
	private String name;
	@NotNull(message = "O atributo [loginUserId] da empresa nao pode ser vazio")
	private Long loginUserId;

}
