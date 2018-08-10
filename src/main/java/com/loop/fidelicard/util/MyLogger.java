package com.loop.fidelicard.util;

public class MyLogger {

	public static String getMessage(String resource, Object dto) {
		String url = "URL = " + resource;
		String data = " ,data = " + dto.toString();
		return url + data;
	}

}
