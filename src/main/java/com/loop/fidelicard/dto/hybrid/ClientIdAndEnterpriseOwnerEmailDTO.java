package com.loop.fidelicard.dto.hybrid;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class ClientIdAndEnterpriseOwnerEmailDTO {
	@NotNull(message = "O atributo [finalClientId] nao pode ser vazio")
	private Long finalClientId;
	@NotEmpty(message = "O atributo [enterpriseOwnerEmail] nao pode ser vazio")
	private String enterpriseOwnerEmail;
}
