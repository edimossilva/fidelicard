package com.loop.fidelicard.security.dto;

import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginUserEmailDTO {
	private @NotNull String loginUserEmail;
}
