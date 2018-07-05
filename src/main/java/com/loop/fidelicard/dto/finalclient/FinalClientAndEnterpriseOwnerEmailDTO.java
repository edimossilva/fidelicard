package com.loop.fidelicard.dto.finalclient;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FinalClientAndEnterpriseOwnerEmailDTO {
	@NotEmpty(message = "O atributo [uniqueIdentifier]  nao pode ser vazio")
	private String uniqueIdentifier;
	
	@NotEmpty(message = "O atributo [email] nao pode ser vazio")
	private String email;
	
	@NotEmpty(message = "O atributo [enterpriseOwnerEmail] nao pode ser vazio")
	private String enterpriseOwnerEmail;
}
