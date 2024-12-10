package com.meatigo.campaign.secondaryDbEntity;

import java.util.Date;

import com.meatigo.campaign.primaryDbEntity.BaseVO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "WhatsAppCampaign")
public class WhatsAppCampaign extends BaseVO{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long whatsAppCampaignId;
	private String campaignName;
	private String sheduledcampaignRequest;
	private String messageName;
	private Boolean campaignStatus;
	private Boolean isSheduledCampaign;
	private Boolean sheduledCampaignStatus;
	private Boolean isSheduleCampaignActive;
	private Date sheduledCampaignTime;


}
