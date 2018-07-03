package com.loop.fidelicard.dto.enterprise;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class EnterpriseDTO {
	private @NonNull String name;
	private @NonNull Long loginUserId;

}
