package com.loop.fidelicard.dto.enterprise;

import com.loop.fidelicard.model.Enterprise;
import com.loop.fidelicard.security.dto.loginuser.ResponseLoginUserDTO;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class ResponseEnterpriseWithLoginUserDTO {
	private Long enterpriseId;
	private String enterpriseName;
	private ResponseLoginUserDTO ownerLoginUser;

	public ResponseEnterpriseWithLoginUserDTO(Enterprise enterprise) {
		setEnterpriseId(enterprise.getId());
		setEnterpriseName(enterprise.getName());
		setOwnerLoginUser(new ResponseLoginUserDTO(enterprise.getOwnerLoginUser()));
	}

}
