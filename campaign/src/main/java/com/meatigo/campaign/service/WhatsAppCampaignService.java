package com.meatigo.campaign.service;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meatigo.campaign.exception.CampaignException;
import com.meatigo.campaign.primaryDbEntity.WhatsappSmsTemplate;
import com.meatigo.campaign.primaryDbRepo.WhatsappSmsTemplateRepo;
import com.meatigo.campaign.req_res.AllCampaignDataResponse;
import com.meatigo.campaign.req_res.CamIsActiveUpdateResonse;
import com.meatigo.campaign.req_res.WhatsAppCampaignRequest;
import com.meatigo.campaign.req_res.WhatsAppCampaignRequestCustomerWithoutOrder;
import com.meatigo.campaign.secondaryDbEntity.WhatsAppCampaign;
import com.meatigo.campaign.secondaryDbRepo.WhatsAppCampaignRepo;

import jakarta.annotation.PostConstruct;

@Service
public class WhatsAppCampaignService {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	private SimpleJdbcCall simpleJdbcCallForCustomerMobile;
	
	private SimpleJdbcCall simpleJdbcCallForCustomerWithoutOrder;
	
	@Autowired
	private WhatsAppMessageService whatsAppMessageService;
	
	@Autowired
	private WhatsappSmsTemplateRepo whatsappSmsTemplateRepo;
	
	@Autowired
	private WhatsAppCampaignRepo whatsAppCampaignRepo;
	
	
	@PostConstruct
    public void init() {
		simpleJdbcCallForCustomerMobile = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("GetCustomerMobiles")
                .declareParameters(new SqlParameter("orderStatus", Types.VARCHAR),
                        new SqlParameter("categoryName", Types.VARCHAR),
                        new SqlParameter("itemNamePurchased", Types.VARCHAR),
                        new SqlParameter("orderChannel", Types.VARCHAR),
                        new SqlParameter("totalOrder", Types.INTEGER),
                        new SqlParameter("totalOrderLessThan", Types.INTEGER),
                        new SqlParameter("totalOrderGreaterThan", Types.INTEGER),
                        new SqlParameter("totalOrderValue", Types.DOUBLE),
                        new SqlParameter("totalOrderValueLessThan", Types.DOUBLE),
                        new SqlParameter("totalOrderValueGreaterThan", Types.DOUBLE),
                        new SqlParameter("lastOrderDate", Types.VARCHAR),
                        new SqlParameter("lastOrderDateAfter", Types.VARCHAR),
                        new SqlParameter("lastOrderDateBefore", Types.VARCHAR),
                        new SqlParameter("signUpDate", Types.VARCHAR),
                        new SqlParameter("signUpDateBefore", Types.VARCHAR),
                        new SqlParameter("signUpDateAfter", Types.VARCHAR),
                        new SqlParameter("cityName", Types.VARCHAR),
                        new SqlParameter("productName", Types.VARCHAR),
                        new SqlParameter("storeName", Types.VARCHAR),
                        new SqlParameter("zoneName", Types.VARCHAR),
                        new SqlParameter("walletPoints", Types.INTEGER),
                        new SqlParameter("walletPointsLessThan", Types.INTEGER),
                        new SqlParameter("walletPointsGreaterThan", Types.INTEGER),
                        new SqlParameter("appVersion", Types.VARCHAR),
                        new SqlParameter("whatsAppStatus", Types.INTEGER),
                        new SqlParameter("loyaltyMember", Types.INTEGER)
                		);
		
		
		simpleJdbcCallForCustomerWithoutOrder =  new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("GetCustomerWithoutOrderMobiles")
                .declareParameters(
                        new SqlParameter("totalOrder", Types.INTEGER),
                        new SqlParameter("totalOrderLessThan", Types.INTEGER),
                        new SqlParameter("totalOrderGreaterThan", Types.INTEGER),
                        new SqlParameter("totalOrderValue", Types.DOUBLE),
                        new SqlParameter("totalOrderValueLessThan", Types.DOUBLE),
                        new SqlParameter("totalOrderValueGreaterThan", Types.DOUBLE),
                        new SqlParameter("signUpDate", Types.VARCHAR),
                        new SqlParameter("signUpDateBefore", Types.VARCHAR),
                        new SqlParameter("signUpDateAfter", Types.VARCHAR),
                        new SqlParameter("walletPoints", Types.INTEGER),
                        new SqlParameter("walletPointsLessThan", Types.INTEGER),
                        new SqlParameter("walletPointsGreaterThan", Types.INTEGER),
                        new SqlParameter("appVersion", Types.VARCHAR),
                        new SqlParameter("whatsAppStatus", Types.INTEGER),
                        new SqlParameter("loyaltyMember", Types.INTEGER)
                		);
    }
	
    @SuppressWarnings("unchecked")
	public List<Map<String, Object>> getCustomerMobiles(WhatsAppCampaignRequest appCampaignRequest) {
        Map<String, Object> inParams = new HashMap<>();
        inParams.put("orderStatus", ObjectUtils.isEmpty(appCampaignRequest.getOrderStatus()) ? null : appCampaignRequest.getOrderStatus());
        inParams.put("categoryName", ObjectUtils.isEmpty(appCampaignRequest.getCategoryName()) ? null : appCampaignRequest.getCategoryName());
        inParams.put("itemNamePurchased", ObjectUtils.isEmpty(appCampaignRequest.getItemNamePurchased()) ? null : appCampaignRequest.getItemNamePurchased());
        inParams.put("orderChannel", ObjectUtils.isEmpty(appCampaignRequest.getOrderChannel()) ? null : appCampaignRequest.getOrderChannel());
        inParams.put("totalOrder", ObjectUtils.isEmpty(appCampaignRequest.getTotalOrder()) ? null : appCampaignRequest.getTotalOrder());
        inParams.put("totalOrderLessThan", ObjectUtils.isEmpty(appCampaignRequest.getTotalOrderLessThan()) ? null : appCampaignRequest.getTotalOrderLessThan());
        inParams.put("totalOrderGreaterThan", ObjectUtils.isEmpty(appCampaignRequest.getTotalOrderGreaterThan()) ? null : appCampaignRequest.getTotalOrderGreaterThan());
        inParams.put("totalOrderValue", ObjectUtils.isEmpty(appCampaignRequest.getTotalOrderValue()) ? null : appCampaignRequest.getTotalOrderValue());
        inParams.put("totalOrderValueLessThan", ObjectUtils.isEmpty(appCampaignRequest.getTotalOrderValueLessThan()) ? null : appCampaignRequest.getTotalOrderValueLessThan());
        inParams.put("totalOrderValueGreaterThan", ObjectUtils.isEmpty(appCampaignRequest.getTotalOrderValueGreaterThan()) ? null : appCampaignRequest.getTotalOrderValueGreaterThan());
        inParams.put("lastOrderDate", ObjectUtils.isEmpty(appCampaignRequest.getLastOrderDate()) ? null : appCampaignRequest.getLastOrderDate());
        inParams.put("lastOrderDateAfter", ObjectUtils.isEmpty(appCampaignRequest.getLastOrderDateAfter()) ? null : appCampaignRequest.getLastOrderDateAfter());
        inParams.put("lastOrderDateBefore", ObjectUtils.isEmpty(appCampaignRequest.getLastOrderDateBefore()) ? null : appCampaignRequest.getLastOrderDateBefore());
        inParams.put("signUpDate", ObjectUtils.isEmpty(appCampaignRequest.getSignUpDate()) ? null : appCampaignRequest.getSignUpDate());
        inParams.put("signUpDateBefore", ObjectUtils.isEmpty(appCampaignRequest.getSignUpDateBefore()) ? null : appCampaignRequest.getSignUpDateBefore());
        inParams.put("signUpDateAfter", ObjectUtils.isEmpty(appCampaignRequest.getSignUpDateAfter()) ? null : appCampaignRequest.getSignUpDateAfter());
        inParams.put("cityName", ObjectUtils.isEmpty(appCampaignRequest.getCityName()) ? null : appCampaignRequest.getCityName());
        inParams.put("productName", ObjectUtils.isEmpty(appCampaignRequest.getProductName()) ? null : appCampaignRequest.getProductName());
        inParams.put("storeName", ObjectUtils.isEmpty(appCampaignRequest.getStoreName()) ? null : appCampaignRequest.getStoreName());
        inParams.put("zoneName", ObjectUtils.isEmpty(appCampaignRequest.getZoneName()) ? null : appCampaignRequest.getZoneName());
        inParams.put("walletPoints", ObjectUtils.isEmpty(appCampaignRequest.getWalletPoints()) ? null : appCampaignRequest.getWalletPoints());
        inParams.put("walletPointsLessThan", ObjectUtils.isEmpty(appCampaignRequest.getWalletPointsLessThan()) ? null : appCampaignRequest.getWalletPointsLessThan());
        inParams.put("walletPointsGreaterThan", ObjectUtils.isEmpty(appCampaignRequest.getWalletPointsGreaterThan()) ? null : appCampaignRequest.getWalletPointsGreaterThan());
        inParams.put("appVersion", ObjectUtils.isEmpty(appCampaignRequest.getAppVersion()) ? null : appCampaignRequest.getAppVersion());
        inParams.put("whatsAppStatus", ObjectUtils.isEmpty(appCampaignRequest.getWhatsAppStatus()) ? null : appCampaignRequest.getWhatsAppStatus());
        inParams.put("loyaltyMember", ObjectUtils.isEmpty(appCampaignRequest.getLoyaltyMember()) ? null : appCampaignRequest.getLoyaltyMember());

        Map<String, Object> result = simpleJdbcCallForCustomerMobile.execute(inParams);
        return (List<Map<String, Object>>) result.get("#result-set-1");
    }
    
    
    @SuppressWarnings("unchecked")
	public List<Map<String, Object>> getCustomerWithoutOrderMobile(WhatsAppCampaignRequestCustomerWithoutOrder appCampaignRequest) {
        Map<String, Object> inParams = new HashMap<>();
        inParams.put("totalOrder", ObjectUtils.isEmpty(appCampaignRequest.getTotalOrder()) ? null : appCampaignRequest.getTotalOrder());
        inParams.put("totalOrderLessThan", ObjectUtils.isEmpty(appCampaignRequest.getTotalOrderLessThan()) ? null : appCampaignRequest.getTotalOrderLessThan());
        inParams.put("totalOrderGreaterThan", ObjectUtils.isEmpty(appCampaignRequest.getTotalOrderGreaterThan()) ? null : appCampaignRequest.getTotalOrderGreaterThan());
        inParams.put("totalOrderValue", ObjectUtils.isEmpty(appCampaignRequest.getTotalOrderValue()) ? null : appCampaignRequest.getTotalOrderValue());
        inParams.put("totalOrderValueLessThan", ObjectUtils.isEmpty(appCampaignRequest.getTotalOrderValueLessThan()) ? null : appCampaignRequest.getTotalOrderValueLessThan());
        inParams.put("totalOrderValueGreaterThan", ObjectUtils.isEmpty(appCampaignRequest.getTotalOrderValueGreaterThan()) ? null : appCampaignRequest.getTotalOrderValueGreaterThan());
        inParams.put("signUpDate", ObjectUtils.isEmpty(appCampaignRequest.getSignUpDate()) ? null : appCampaignRequest.getSignUpDate());
        inParams.put("signUpDateBefore", ObjectUtils.isEmpty(appCampaignRequest.getSignUpDateBefore()) ? null : appCampaignRequest.getSignUpDateBefore());
        inParams.put("signUpDateAfter", ObjectUtils.isEmpty(appCampaignRequest.getSignUpDateAfter()) ? null : appCampaignRequest.getSignUpDateAfter());
        inParams.put("walletPoints", ObjectUtils.isEmpty(appCampaignRequest.getWalletPoints()) ? null : appCampaignRequest.getWalletPoints());
        inParams.put("walletPointsLessThan", ObjectUtils.isEmpty(appCampaignRequest.getWalletPointsLessThan()) ? null : appCampaignRequest.getWalletPointsLessThan());
        inParams.put("walletPointsGreaterThan", ObjectUtils.isEmpty(appCampaignRequest.getWalletPointsGreaterThan()) ? null : appCampaignRequest.getWalletPointsGreaterThan());
        inParams.put("appVersion", ObjectUtils.isEmpty(appCampaignRequest.getAppVersion()) ? null : appCampaignRequest.getAppVersion());
        inParams.put("whatsAppStatus", ObjectUtils.isEmpty(appCampaignRequest.getWhatsAppStatus()) ? null : appCampaignRequest.getWhatsAppStatus());
        inParams.put("loyaltyMember", ObjectUtils.isEmpty(appCampaignRequest.getLoyaltyMember()) ? null : appCampaignRequest.getLoyaltyMember());

        Map<String, Object> result = simpleJdbcCallForCustomerWithoutOrder.execute(inParams);
        return (List<Map<String, Object>>) result.get("#result-set-1");
    }
    
    
    
    
    public String whatsAppCampaignMessagingSystem(
            WhatsAppCampaignRequest appCampaignRequest,
            String messageName,
            String campaignName,
            Boolean isSheduledCampaign,
            Date sheduledTime) throws JsonProcessingException {

        String status = null;
        ExecutorService execService = Executors.newFixedThreadPool(10);

        try {
            if (!ObjectUtils.isEmpty(whatsAppCampaignRepo.findCampaignByCampaignName(campaignName))) {
                throw new CampaignException("Campaign name already exists, try another one!");
            }

            if (Boolean.FALSE.equals(isSheduledCampaign)) {
                List<Map<String, Object>> customerMobiles = getCustomerMobiles(appCampaignRequest);
                if (!ObjectUtils.isEmpty(customerMobiles)) {
                    execService.execute(() -> {
                        WhatsappSmsTemplate smsTemplate = whatsappSmsTemplateRepo.findByCode(messageName);
                        customerMobiles.parallelStream().forEach(customerMobile -> {
                            whatsAppMessageService.whatsAppCampaignMessage(
                                    smsTemplate.getBody(),
                                    customerMobile.get("mobile").toString(),
                                    smsTemplate.getHeader(),
                                    smsTemplate.getFooter(),
                                    smsTemplate.getImageUrl());
                        });

                        WhatsAppCampaign appCampaign = new WhatsAppCampaign();
                        appCampaign.setCampaignName(campaignName);
                        appCampaign.setMessageName(messageName);
                        appCampaign.setCampaignStatus(true);
                        appCampaign.setIsSheduledCampaign(false);
                        appCampaign.setSheduledCampaignStatus(false);
                        appCampaign.setIsSheduleCampaignActive(false);
                        appCampaign.setSheduledCampaignTime(null);
                        whatsAppCampaignRepo.save(appCampaign);
                    });

                    status = "Data from customer with orders!";

                } else {
                    WhatsAppCampaignRequestCustomerWithoutOrder customerWithoutOrder = new WhatsAppCampaignRequestCustomerWithoutOrder();
                    BeanUtils.copyProperties(appCampaignRequest, customerWithoutOrder);
                    List<Map<String, Object>> customerMobileWithoutOrder = getCustomerWithoutOrderMobile(customerWithoutOrder);

                    if (!ObjectUtils.isEmpty(customerMobileWithoutOrder)) {
                        execService.execute(() -> {
                            WhatsappSmsTemplate smsTemplate = whatsappSmsTemplateRepo.findByCode(messageName);
                            customerMobileWithoutOrder.parallelStream().forEach(customerWithoutOrderMobile -> {
                                whatsAppMessageService.whatsAppCampaignMessage(
                                        smsTemplate.getBody(),
                                        customerWithoutOrderMobile.get("mobile").toString(),
                                        smsTemplate.getHeader(),
                                        smsTemplate.getFooter(),
                                        smsTemplate.getImageUrl());
                                System.out.println("Mob : ====> " + customerWithoutOrderMobile.get("mobile").toString());
                            });

                            WhatsAppCampaign appCampaign = new WhatsAppCampaign();
                            appCampaign.setCampaignName(campaignName);
                            appCampaign.setMessageName(messageName);
                            appCampaign.setCampaignStatus(true);
                            appCampaign.setIsSheduledCampaign(false);
                            appCampaign.setSheduledCampaignStatus(false);
                            appCampaign.setIsSheduleCampaignActive(false);
                            appCampaign.setSheduledCampaignTime(null);
                            whatsAppCampaignRepo.save(appCampaign);
                        });

                        status = "Data from customer without order!";

                    } else {
                        throw new CampaignException("Data Does Not Exist For This Request!");
                    }
                }
            } else {
                status = inserDataForSheduledMessage(appCampaignRequest, campaignName, messageName, sheduledTime);
            }
        } finally {
            execService.shutdown();
            try {
                if (!execService.awaitTermination(60, TimeUnit.SECONDS)) {
                    execService.shutdownNow();
                }
            } catch (InterruptedException e) {
                execService.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }

        return status;
    }
    

    
    
    public String inserDataForSheduledMessage(WhatsAppCampaignRequest appCampaignRequest,String campaignName,String messageName,Date sheduledTime)  throws JsonProcessingException{
    	ObjectMapper map = new ObjectMapper();
    	WhatsAppCampaign appCampaign = new WhatsAppCampaign();
    	appCampaign.setCampaignName(campaignName);
    	appCampaign.setSheduledcampaignRequest(map.writeValueAsString(appCampaignRequest));
    	appCampaign.setMessageName(messageName);
    	appCampaign.setCampaignStatus(false);
    	appCampaign.setIsSheduledCampaign(true);
    	appCampaign.setSheduledCampaignStatus(false);
    	appCampaign.setIsSheduleCampaignActive(true);
    	appCampaign.setSheduledCampaignTime(sheduledTime);
    	whatsAppCampaignRepo.save(appCampaign);
    	return "WhatsApp Campaign Sheduled! ";
    }
    
    
    
    
        
    	public void messagingSystemForSheduledCampaign() throws JsonMappingException, JsonProcessingException {   		
    		List<WhatsAppCampaign> allSheduledDatas = whatsAppCampaignRepo.getAllSheduledData();
    		for(WhatsAppCampaign allSheduledData : allSheduledDatas) {   			
    			WhatsappSmsTemplate smsTemplate = null;        
                if (!ObjectUtils.isEmpty(allSheduledData.getMessageName())) {
                	smsTemplate = whatsappSmsTemplateRepo.findByCode(allSheduledData.getMessageName());
            	}                
                ObjectMapper map = new ObjectMapper();
                WhatsAppCampaignRequest appCampaignRequest = map.readValue(allSheduledData.getSheduledcampaignRequest(), WhatsAppCampaignRequest.class);
                List<Map<String, Object>> customerMobiles = getCustomerMobiles(appCampaignRequest);
                if (!ObjectUtils.isEmpty(customerMobiles)) {	
                	Boolean whatsAppMessageStatus = null;
                	for(Map<String, Object> customerMobile : customerMobiles) {
                		whatsAppMessageStatus = whatsAppMessageService.whatsAppCampaignMessage(smsTemplate.getBody(),customerMobile.get("mobile").toString(),smsTemplate.getHeader(),smsTemplate.getFooter(),smsTemplate.getImageUrl());
                		
                	}
                	if(!ObjectUtils.isEmpty(whatsAppMessageStatus) && Boolean.TRUE.equals(whatsAppMessageStatus)) {
                	WhatsAppCampaign appCampaign = whatsAppCampaignRepo.findCampaignByCampaignName(allSheduledData.getCampaignName());
                	appCampaign.setSheduledCampaignStatus(true);
                	whatsAppCampaignRepo.save(appCampaign);
                	}
            	}else {
            		WhatsAppCampaignRequestCustomerWithoutOrder customerWithoutOrder = new WhatsAppCampaignRequestCustomerWithoutOrder();
            		BeanUtils.copyProperties(appCampaignRequest, customerWithoutOrder);
            		List<Map<String, Object>> customerMobileWithotOrder = getCustomerWithoutOrderMobile(customerWithoutOrder);	     
            		     if (!ObjectUtils.isEmpty(customerMobileWithotOrder)) {
            		    	 Boolean whatsAppMessageStatus = null;
            		     	for(Map<String, Object> customerMobile : customerMobileWithotOrder) {
            		     		whatsAppMessageStatus = whatsAppMessageService.whatsAppCampaignMessage(smsTemplate.getBody(),customerMobile.get("mobile").toString(),smsTemplate.getHeader(),smsTemplate.getFooter(),smsTemplate.getImageUrl());
            		    	}
                        	if(!ObjectUtils.isEmpty(whatsAppMessageStatus) && Boolean.TRUE.equals(whatsAppMessageStatus)) {
                            	WhatsAppCampaign appCampaign = whatsAppCampaignRepo.findCampaignByCampaignName(allSheduledData.getCampaignName());
                            	appCampaign.setSheduledCampaignStatus(true);
                            	whatsAppCampaignRepo.save(appCampaign);
                            	}
            			}else {
            				 throw new CampaignException("Data Does Not Exist For This Request! ");
            			}
            		}
    			
    		}
               
       }
    	
    	
    	
      public List<AllCampaignDataResponse> getAllCampaignData(boolean isSheduledCampaign){
    	  List<AllCampaignDataResponse> camaignDatas = new ArrayList<>(); 	     			  
    	  if(ObjectUtils.isEmpty(isSheduledCampaign) && !ObjectUtils.isEmpty(whatsAppCampaignRepo.getAllCampaignData())) {
    		  List<WhatsAppCampaign> allCampaignData = whatsAppCampaignRepo.getAllCampaignData();
    		  allCampaignData.stream().forEach(data -> {
    			  AllCampaignDataResponse camaignData = new AllCampaignDataResponse();
    			  BeanUtils.copyProperties(data, camaignData);
    			  camaignDatas.add(camaignData);
    		  });
    		  
    	  }else {
    		  List<WhatsAppCampaign> campaignDataSheduledOrNotSheduled  = whatsAppCampaignRepo.findDataByIsSheduledCampaignFlag(isSheduledCampaign);
    		  campaignDataSheduledOrNotSheduled.stream().forEach(data -> {
    			  AllCampaignDataResponse camaignData = new AllCampaignDataResponse();
    			  BeanUtils.copyProperties(data,camaignData);
    			  camaignDatas.add(camaignData);
    		  });
    		      		  
    	  }
    	  return camaignDatas;
      }
      
      
      

	public String deleteCampaignByCampaignName(String campaignName) {
    	  String status = null; 
    	  if(!ObjectUtils.isEmpty(campaignName)) {
    		 Integer deleteCampaign = whatsAppCampaignRepo.deleteByCampaignName(campaignName);
    		 if(deleteCampaign > 0) {
    			 status = "Campaign Deleted Successfully!"; 
    		 }else {
    			 throw new CampaignException("Campaign Name Not Found!");
    		 }
    	  }
    	  return status;  	  
      }
      
      
      public CamIsActiveUpdateResonse campaignIsActiveUpdate(String campaignName,boolean isActive) {
    	  CamIsActiveUpdateResonse isActiveUpdateResonse = new CamIsActiveUpdateResonse();
    	  
    	  if(!ObjectUtils.isEmpty(campaignName) && !ObjectUtils.isEmpty(isActive)) {
    		  Boolean isCampaignExists = whatsAppCampaignRepo.existsByCampaignName(campaignName);
    		  if(!ObjectUtils.isEmpty(isCampaignExists) && Boolean.TRUE.equals(isCampaignExists)) {
        		  WhatsAppCampaign campaign = whatsAppCampaignRepo.findAllByCampaignName(campaignName);
        		  campaign.setIsSheduleCampaignActive(isActive);		  
        		  WhatsAppCampaign savedData = whatsAppCampaignRepo.save(campaign);
        		  BeanUtils.copyProperties(savedData, isActiveUpdateResonse);
    		  }else {
    			  throw new CampaignException("This Campaign Name Not Exist!. Try Another One!!");
    		  }
  
    	  }
    	  return isActiveUpdateResonse;
    	 
      }
    	
    	
       	

}
