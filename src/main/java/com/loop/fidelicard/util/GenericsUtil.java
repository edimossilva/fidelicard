package com.loop.fidelicard.util;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import com.loop.fidelicard.response.Response;

public class GenericsUtil {
	@SuppressWarnings("rawtypes")
	public static <T> ResponseEntity objectToResponse(T t) {
		Response<T> response = new Response<>();
		response.setData(t);
		return ResponseEntity.ok(response);
	}

	@SuppressWarnings("rawtypes")
	public static ResponseEntity errorsToResponse(BindingResult result) {
		Response<String> response = new Response<String>();
		result.getAllErrors().forEach(e -> response.addErrors(e.getDefaultMessage()));
		return ResponseEntity.badRequest().body(response);
	}

	@SuppressWarnings("rawtypes")
	public static ResponseEntity errorsToResponse(List<String> errors) {
		Response<String> response = new Response<String>();
		errors.forEach(e -> response.addErrors(e));
		return ResponseEntity.badRequest().body(response);
	}
}
