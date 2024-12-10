package com.meatigo.campaign.primaryDbEntity;

import java.util.Date;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseVO {
	
	@LastModifiedDate
	Date modifiedOn;
	@LastModifiedBy
	String modifiedBy;
	@CreatedDate
	@Column(updatable = false)
	Date createdOn;
	@CreatedBy
	@Column(updatable = false)
	String createdBy;

}