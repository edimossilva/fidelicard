package com.loop.fidelicard.dto.finalclient;

import com.loop.fidelicard.model.FinalClient;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class ResponseFinalClientDTO {
	private @NonNull Long id;
	private @NonNull String uniqueIdentifier;
	private @NonNull String email;

	public ResponseFinalClientDTO(FinalClient fc) {
		setId(fc.getId());
		setUniqueIdentifier(fc.getUniqueIdentifier());
		setEmail(fc.getEmail());

	}

}
