package com.loop.fidelicard.util;

import org.springframework.http.ResponseEntity;

import com.loop.fidelicard.response.Response;

public class GenericsUtil {
	@SuppressWarnings("rawtypes")
	public static <T> ResponseEntity dTOToResponse(T t) {
		Response<T> response = new Response<>();
		response.setData(t);
		return ResponseEntity.ok(response);
	}
}
