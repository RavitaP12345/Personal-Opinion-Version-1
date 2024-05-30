package com.example.PersonalOpinionVersion1.models;

import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class SharedModel {
	private String createdBy;
	private String updatedBy;
	private String status;
	private String createionDate;
	private String updateDate;
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCreateionDate() {
		return createionDate;
	}
	public void setCreateionDate(String createionDate) {
		this.createionDate = createionDate;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	

}
