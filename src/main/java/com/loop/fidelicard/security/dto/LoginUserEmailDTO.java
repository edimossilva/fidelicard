package com.loop.fidelicard.security.dto;

import javax.validation.constraints.NotNull;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class LoginUserEmailDTO {
	private @NotNull String loginUserEmail;
}
