package com.loop.fidelicard.dto.hybrid;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class ClientUIAndEnterpriseOwnerEmailDTO {
	@NotEmpty(message = "O atributo [finalClientUI] nao pode ser vazio")
	private String finalClientUI;
	@NotEmpty(message = "O atributo [enterpriseOwnerEmail] nao pode ser vazio")
	private String enterpriseOwnerEmail;

}
