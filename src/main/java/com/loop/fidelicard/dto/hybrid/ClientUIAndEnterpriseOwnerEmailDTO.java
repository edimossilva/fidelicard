package com.loop.fidelicard.dto.hybrid;

import javax.validation.constraints.NotNull;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class ClientUIAndEnterpriseOwnerEmailDTO {
	private @NotNull String finalClientUI;
	private @NotNull String enterpriseOwnerEmail;
}
