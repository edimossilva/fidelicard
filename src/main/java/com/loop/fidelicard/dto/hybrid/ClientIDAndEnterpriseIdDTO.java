package com.loop.fidelicard.dto.hybrid;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
public class ClientIDAndEnterpriseIdDTO {
	private @NotNull Long finalClientId;
	private @NotNull Long enterpriseId;
}
