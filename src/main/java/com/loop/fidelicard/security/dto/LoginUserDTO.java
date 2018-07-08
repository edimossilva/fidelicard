package com.loop.fidelicard.security.dto;

import com.loop.fidelicard.security.model.UserRole;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class LoginUserDTO {
	protected @NonNull String email;
	protected @NonNull String password;
	protected @NonNull UserRole userRole;
}
