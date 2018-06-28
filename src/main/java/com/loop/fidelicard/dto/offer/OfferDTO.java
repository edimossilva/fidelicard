package com.loop.fidelicard.dto.offer;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class OfferDTO {
	private @NonNull String name;
	private @NonNull String description;
	private @NonNull Integer quantity;
	private @NonNull Long enterpriseId;
	
}
