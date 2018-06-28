package com.loop.fidelicard.dto.enterprise;

import com.loop.fidelicard.model.Enterprise;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class ResponseEnterpriseDTO {
	private @NonNull Long id;
	private @NonNull String name;

	public ResponseEnterpriseDTO(Enterprise enterprise) {
		setId(enterprise.getId());
		setName(enterprise.getName());
	}

}
