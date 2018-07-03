package com.loop.fidelicard.dto.finalclient;

import com.loop.fidelicard.model.FinalClient;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
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
