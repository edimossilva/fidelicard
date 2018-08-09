package com.loop.fidelicard.dto.consumer;

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
public class ConsumerEnterpriseDTO {
	private Long enterpriseId;
	private String enterpriseName;
	private DateDTO createdAt;

	public ConsumerEnterpriseDTO(Enterprise enterprise) {
		setEnterpriseId(enterprise.getId());
		setEnterpriseName(enterprise.getName());
		setCreatedAt(new DateDTO(enterprise.getCreatedAt()));

	}
}
