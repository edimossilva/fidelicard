package com.loop.fidelicard.dto.consumer;

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

	public ConsumerEnterpriseDTO(Enterprise enterprise) {
		setEnterpriseId(enterprise.getId());
		setEnterpriseName(enterprise.getName());
	}
}
