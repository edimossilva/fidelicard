package com.loop.fidelicard.dto.enterprise;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class EnterpriseDTO {
	private @NonNull String name;
	private @NonNull Long loginUserId;

}
