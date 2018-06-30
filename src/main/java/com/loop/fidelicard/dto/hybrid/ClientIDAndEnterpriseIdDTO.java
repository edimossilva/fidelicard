package com.loop.fidelicard.dto.hybrid;

import javax.validation.constraints.NotNull;

import lombok.Getter;

@Getter
public class ClientIDAndEnterpriseIdDTO {
	private @NotNull Long finalClientId;
	private @NotNull Long enterpriseId;
}
