package com.example.PersonalOpinionVersion1.entities;

import java.util.Date;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@MappedSuperclass
public class SharedEntity {
	private String createdBy;
	private String updatedBy;
	private String status;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createionDate;
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateDate;
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
	public Date getCreateionDate() {
		return createionDate;
	}
	public void setCreateionDate(Date createionDate) {
		this.createionDate = createionDate;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	
	

}
