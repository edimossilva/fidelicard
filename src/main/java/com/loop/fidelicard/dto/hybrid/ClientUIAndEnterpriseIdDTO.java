package com.loop.fidelicard.dto.hybrid;

import javax.validation.constraints.NotNull;

import lombok.Getter;

@Getter
public class ClientUIAndEnterpriseIdDTO {
	private @NotNull String finalClientUI;
	private @NotNull Long enterpriseId;
	
}
