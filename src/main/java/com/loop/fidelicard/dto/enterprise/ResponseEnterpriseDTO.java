package com.loop.fidelicard.dto.enterprise;

import com.loop.fidelicard.model.Enterprise;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class ResponseEnterpriseDTO {
	private @NonNull Long id;
	private @NonNull String name;

	public ResponseEnterpriseDTO(Enterprise enterprise) {
		setId(enterprise.getId());
		setName(enterprise.getName());
	}

}
