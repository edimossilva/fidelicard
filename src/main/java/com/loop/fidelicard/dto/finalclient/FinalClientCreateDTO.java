package com.loop.fidelicard.dto.finalclient;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class FinalClientCreateDTO {
	private @NonNull String uniqueIdentifier;
	private @NonNull String email;

}