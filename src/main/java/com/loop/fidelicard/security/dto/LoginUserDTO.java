package com.loop.fidelicard.security.dto;

import com.loop.fidelicard.security.model.UserRole;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
@Builder
public class LoginUserDTO {
	protected @NonNull String email;
	protected @NonNull String password;
	protected @NonNull UserRole userRole;
}
