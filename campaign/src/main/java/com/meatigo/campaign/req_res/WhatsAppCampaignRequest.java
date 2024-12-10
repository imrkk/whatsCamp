package com.meatigo.campaign.req_res;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WhatsAppCampaignRequest {

  private String orderStatus;
  private String categoryName;
  private String itemNamePurchased;
  private String orderChannel;
  private Integer totalOrder;
  private Integer totalOrderLessThan;
  private Integer totalOrderGreaterThan;
  
  
  private Double totalOrderValue;
  private Double totalOrderValueLessThan;
  private Double totalOrderValueGreaterThan;
  
  private String lastOrderDate;
  private String lastOrderDateAfter;
  private String lastOrderDateBefore;
  
  private String signUpDate;
  private String signUpDateBefore;
  private String signUpDateAfter;
  
  
  private String cityName;
  private String productName;
  private String storeName;
  private String zoneName;
  private Integer walletPoints;
  private Integer walletPointsLessThan;
  private Integer walletPointsGreaterThan;
  private String appVersion;
  private Boolean whatsAppStatus;
  private Boolean loyaltyMember;

}