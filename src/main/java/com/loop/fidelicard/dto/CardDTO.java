package com.loop.fidelicard.dto;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class CardDTO {
	private @NonNull Long finalClientId;
	private @NonNull Long offerId;
}
