package com.loop.fidelicard.response;

public class Response<T> {

	private T data;
	// private List<String> errors;

	public Response() {
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	// public List<String> getErrors() {
	// if (this.errors == null) {
	// this.errors = new ArrayList<String>();
	// }
	// return errors;
	// }
	//
	// public void setErrors(List<String> errors) {
	// this.errors = errors;
	// }
	//
	// public void addErrors(String error) {
	// getErrors().add(error);
	// }

}
