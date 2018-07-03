package com.loop.fidelicard.dto.offer;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class OfferDTO {
	private @NonNull String name;
	private @NonNull String description;
	private @NonNull Integer quantity;
	private @NonNull Long enterpriseId;
	
}
