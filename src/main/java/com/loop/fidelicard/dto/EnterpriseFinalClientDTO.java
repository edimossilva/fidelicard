package com.loop.fidelicard.dto;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class EnterpriseFinalClientDTO {
	private @NonNull Long finalClientId;
	private @NonNull Long enterpriseId;
}
