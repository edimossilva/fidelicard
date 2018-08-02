package com.loop.fidelicard.dto.enterprise;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnterpriseDTO {
	@NotEmpty(message = "O atributo [enterpriseName] da empresa nao pode ser vazio")
	private String enterpriseName;
	@NotNull(message = "O atributo [loginUserId] da empresa nao pode ser vazio")
	private Long loginUserId;

}
