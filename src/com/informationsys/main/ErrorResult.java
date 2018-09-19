package com.informationsys.main;

import org.json.JSONObject;

public class ErrorResult {
	private int code;
	private String message;
	private String response;
	
	public ErrorResult (int code, String message){
		setCode(code);
		setMessage(message);
		setResponse("");
	}
	
	public ErrorResult (int code, String message, String response){
		setCode(code);
		setMessage(message);
		setResponse(response);
	}

	public int getCode() {
		return code;
	}
	
	public void setCode(int code) {
		this.code = code;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public String getObject(){
		JSONObject objeto = new JSONObject();
		objeto.put("code", getCode());
		objeto.put("message", getMessage());
		objeto.put("response", getResponse());
		return objeto.toString();
	}
	
}
