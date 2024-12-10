package com.meatigo.campaign.req_res;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CamIsActiveUpdateResonse {
	
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
