package com.loop.fidelicard.dto.enterprise;

import com.loop.fidelicard.dto.DefaultDTO;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class EnterpriseDTO extends DefaultDTO{
	private @NonNull String name;
}
