package com.loop.fidelicard.dto.stamp;

import com.loop.fidelicard.dto.date.DateDTO;
import com.loop.fidelicard.model.Stamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ResponseStampDTO {
	private Long cardId;
	private Long clientId;
	private DateDTO createdAt;

	public ResponseStampDTO(Stamp stamp) {
		setCardId(stamp.getCard().getId());
		setClientId(stamp.getCard().getFinalClient().getId());
		setCreatedAt(new DateDTO(stamp.getCreatedAt()));
	}

}
