package com.bitcoin.assignment.response;

public class APIResponse {
	private String timestamp;
	private int status;
	private String message;
	private Object data;

	public APIResponse(String timestamp, int status, String message, Object data) {
		this.timestamp = timestamp;
		this.status = status;
		this.message = message;
		this.data = data;
	}

	public APIResponse() {

	}

	public APIResponse(String timestamp, int status, String message) {
		this.timestamp = timestamp;
		this.status = status;
		this.message = message;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
