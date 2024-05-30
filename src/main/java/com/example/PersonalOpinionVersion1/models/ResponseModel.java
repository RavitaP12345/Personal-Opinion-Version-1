package com.example.PersonalOpinionVersion1.models;

import java.util.LinkedHashMap;

public class ResponseModel {
	private String content;
	private String type;
	LinkedHashMap<String, String> errorMessage = new LinkedHashMap<>();
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	

	public LinkedHashMap<String, String> getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(LinkedHashMap<String, String> errorMessage) {
		this.errorMessage = errorMessage;
	}

	public ResponseModel(String content, String type) {
		super();
		this.content = content;
		this.type = type;
	}

	public ResponseModel(LinkedHashMap<String, String> errorMessage, String content) {
		super();
		this.content = content;
		this.errorMessage = errorMessage;
	}

	
	
	
	

}
