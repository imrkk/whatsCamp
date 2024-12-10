package com.meatigo.campaign.primaryDbEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class WhatsappSmsTemplate extends BaseVO{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long whatsappSmsTemplateId;
	private String whatsappTemplateId;
	private String code;
	private Boolean isActive;
	private String header;
	private String body;
	private String footer;
	private String imageUrl;
	
}