package com.loop.fidelicard.security.dto;

import javax.validation.constraints.NotNull;

import lombok.Getter;

@Getter
public class LoginUserEmailDTO {
	private @NotNull String loginUserEmail;
}
