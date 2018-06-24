package com.loop.fidelicard.dto;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class CardDTO {
	protected @NonNull Long finalClientId;
	protected @NonNull Long offerId;
}
