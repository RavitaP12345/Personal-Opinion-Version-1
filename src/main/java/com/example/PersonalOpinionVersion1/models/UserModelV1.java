package com.example.PersonalOpinionVersion1.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserModelV1 {
	private Long userId;
	@NotNull(message = "Name must not be null.")
	@NotBlank(message = "Name must not be blank.")
	@Size(min = 2, max = 50, message = "Name must be in betwwen 2 to 50 characters.")
	private String name;
	@Email(message = "Email is incorrect.")
	@NotBlank(message = "Email must not be blank.")
	@NotNull(message = "Email must not be null.")
	private String email;
	@NotBlank(message = "Mobile number must not be blank.")
	@NotNull(message = "Mobile number must not be null.")
	@Pattern(regexp="^[0-9]{10}$", message="Mobile number must be 10 digits without any characters.")
	private String mobileNumber;
	private String password;
	private String profile;
	private String about;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
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
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getProfile() {
		return profile;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
	public String getAbout() {
		return about;
	}
	public void setAbout(String about) {
		this.about = about;
	}
	@Override
	public String toString() {
		return "UserModelV1 [userId=" + userId + ", name=" + name + ", email=" + email + ", mobileNumber="
				+ mobileNumber + ", password=" + password + ", profile=" + profile + ", about=" + about + "]";
	}
	

	
}
