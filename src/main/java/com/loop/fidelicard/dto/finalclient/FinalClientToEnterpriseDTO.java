package com.loop.fidelicard.dto.finalclient;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class FinalClientToEnterpriseDTO {
	private @NonNull Long finalClientId;
	private @NonNull Long enterpriseId;

}
