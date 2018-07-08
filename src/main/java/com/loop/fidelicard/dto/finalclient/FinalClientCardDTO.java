package com.loop.fidelicard.dto.finalclient;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class FinalClientCardDTO {
	private @NonNull Long cardId;
	private @NonNull Long offerId;
	private @NonNull Long finalClientId;
}
