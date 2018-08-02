package com.loop.fidelicard.security.dto.token;

import com.loop.fidelicard.dto.enterprise.ResponseEnterpriseDTO;
import com.loop.fidelicard.model.Enterprise;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@AllArgsConstructor
public class ResponseTokenAndEnterpriseDTO {
	private ResponseEnterpriseDTO enterprise;
	private String token;

	public ResponseTokenAndEnterpriseDTO(String token, Enterprise enterprise) {
		setEnterprise(new ResponseEnterpriseDTO(enterprise));
		setToken(token);
	}

}
