package com.example.PersonalOpinionVersion1.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name = "contact_details")
public class ContactEntity extends SharedEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long contactId;
	private String name;
	private String email;
	@Lob
	private String message;
	public Long getContactId() {
		return contactId;
	}
	public void setContactId(Long contactId) {
		this.contactId = contactId;
	}
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
	@Override
	public String toString() {
		return "ContactEntity [contactId=" + contactId + ", name=" + name + ", email=" + email + ", message=" + message
				+ "]";
	}
	
	

}
