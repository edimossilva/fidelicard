package com.loop.fidelicard.dto.finalclient;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class UIDTO {
	@NotEmpty(message = "O atributo [finalClientUI]  nao pode ser vazio")
	private String finalClientUI;
}
