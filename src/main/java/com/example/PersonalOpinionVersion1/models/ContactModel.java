package com.example.PersonalOpinionVersion1.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ContactModel extends SharedModel{
	private Long contactId;
	@NotNull(message = "Name must not be null.")
	@NotBlank(message = "Name must not be blank.")
	@NotBlank(message = "Name must not be empty.")
	@Size(min = 2, max = 50, message = "Name must be in between 2 to 50 characters.")
	private String name;
	@Email(message = "Email is incorrect.")
	@NotBlank(message = "Email must not be blank.")
	@NotNull(message = "Email must not be null.")
	private String email;
	@NotBlank(message = "Message must not be blank.")
	@NotNull(message = "Message must not be null.")
	@NotEmpty(message = "Message must not be empty.")
	private String message;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
	
	public Long getContactId() {
		return contactId;
	}
	public void setContactId(Long contactId) {
		this.contactId = contactId;
	}
	@Override
	public String toString() {
		return "ContactModel [name=" + name + ", email=" + email + ", message=" + message + "]";
	}
	
	

}
