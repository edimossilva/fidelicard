package com.loop.fidelicard.dto.stamp;

import java.time.LocalDateTime;

import com.loop.fidelicard.model.Stamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ResponseStampDTO {
	private Long cardId;
	private Long clientId;
	private LocalDateTime date;

	public ResponseStampDTO(Stamp stamp) {
		setCardId(stamp.getCard().getId());
		setDate(stamp.getDate());
		setClientId(stamp.getCard().getFinalClient().getId());
	}

}
