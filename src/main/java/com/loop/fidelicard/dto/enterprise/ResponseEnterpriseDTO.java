package com.loop.fidelicard.dto.enterprise;

import com.loop.fidelicard.model.Enterprise;
import com.loop.fidelicard.security.dto.ResponseLoginUserDTO;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class ResponseEnterpriseDTO {
	private @NonNull Long id;
	private @NonNull String name;
	private @NonNull ResponseLoginUserDTO loginUser;

	public ResponseEnterpriseDTO(Enterprise enterprise) {
		setId(enterprise.getId());
		setName(enterprise.getName());
		System.out.println(enterprise);
		setLoginUser(new ResponseLoginUserDTO(enterprise.getOwnerLoginUser()));
	}

}
