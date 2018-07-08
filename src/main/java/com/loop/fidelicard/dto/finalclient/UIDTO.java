package com.loop.fidelicard.dto.finalclient;

import javax.validation.constraints.NotNull;

import lombok.Getter;

@Getter
public class UIDTO {
	private @NotNull String uniqueIdentifier;
}
