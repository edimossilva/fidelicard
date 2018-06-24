package com.loop.fidelicard.dto;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class FullCardDTO extends CardDTO{
	private @NonNull Long cardId;
}
