package com.loop.fidelicard.security.dto.loginuser;

import com.loop.fidelicard.security.model.LoginUser;
import com.loop.fidelicard.security.model.UserRole;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class ResponseLoginUserDTO {
	private @NonNull Long id;
	private @NonNull String email;
	private @NonNull UserRole userRole;

	public ResponseLoginUserDTO(LoginUser lu) {
		setId(lu.getId());
		setEmail(lu.getEmail());
		setUserRole(lu.getUserRole());
	}
	
}
