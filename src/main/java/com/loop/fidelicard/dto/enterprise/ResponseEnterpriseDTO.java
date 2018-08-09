package com.loop.fidelicard.dto.enterprise;

import com.loop.fidelicard.dto.date.DateDTO;
import com.loop.fidelicard.model.Enterprise;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class ResponseEnterpriseDTO {
	private Long id;
	private String name;
	private DateDTO createdAt;

	public ResponseEnterpriseDTO(Enterprise enterprise) {
		setId(enterprise.getId());
		setName(enterprise.getName());
		setCreatedAt(new DateDTO(enterprise.getCreatedAt()));
	}

}
