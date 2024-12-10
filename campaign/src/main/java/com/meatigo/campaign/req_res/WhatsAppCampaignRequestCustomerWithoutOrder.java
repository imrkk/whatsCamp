package com.meatigo.campaign.req_res;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WhatsAppCampaignRequestCustomerWithoutOrder {
	
	  private Integer totalOrder;
	  private Integer totalOrderLessThan;
	  private Integer totalOrderGreaterThan;
	  
	  
	  private Double totalOrderValue;
	  private Double totalOrderValueLessThan;
	  private Double totalOrderValueGreaterThan;
	  
	  
	  private String signUpDate;
	  private String signUpDateBefore;
	  private String signUpDateAfter;
	  
	  

	  private Integer walletPoints;
	  private Integer walletPointsLessThan;
	  private Integer walletPointsGreaterThan;
	  private String appVersion;
	  private Boolean whatsAppStatus;
	  private Boolean loyaltyMember;

}