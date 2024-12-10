package com.meatigo.campaign.service;

import java.sql.Types;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import com.meatigo.campaign.primaryDbEntity.PrimaryEntity;
import com.meatigo.campaign.primaryDbEntity.WhatsappSmsTemplate;
import com.meatigo.campaign.primaryDbRepo.PrimaryRepository;
import com.meatigo.campaign.primaryDbRepo.UserRepo;
import com.meatigo.campaign.primaryDbRepo.WhatsappSmsTemplateRepo;
import com.meatigo.campaign.req_res.WhatsAppCampaignRequest;
import com.meatigo.campaign.req_res.WhatsAppCampaignRequestCustomerWithoutOrder;
import com.meatigo.campaign.secondaryDbEntity.SecondaryEntity;
import com.meatigo.campaign.secondaryDbRepo.SecondaryRepository;
import jakarta.annotation.PostConstruct;


@Service
public class TestService {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private SimpleJdbcCall simpleJdbcCallForCustomerMobile;
	
	private SimpleJdbcCall simpleJdbcCallForCustomerWithoutOrder;
	
	@Autowired
	private WhatsAppMessageService whatsAppMessageService;
	
	@Autowired
	private WhatsappSmsTemplateRepo whatsappSmsTemplateRepo;
	
	@Autowired
	private SecondaryRepository secondaryRepository;

	@Autowired
	private PrimaryRepository primaryRepository;


	@Autowired
	private UserRepo userRepo;

	public List<PrimaryEntity> getPrimaryDbData() {
		List<PrimaryEntity> datas = primaryRepository.getAllData();
		return datas;
	}

	public List<SecondaryEntity> getSecondaryDbData() {
		List<SecondaryEntity> datas = secondaryRepository.getAllData();
		return datas;
	}

	public List<String> getAllUser() {
		return userRepo.getAllUser();
	}

	
	
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
    
    
    
    
    public String whatsAppCampaignMessagingSystem(WhatsAppCampaignRequest appCampaignRequest,String whatsAppMessageType,Boolean isSheduledCampaign,Date sheduledTime) throws JsonProcessingException {    	
    	String status = null;
        WhatsappSmsTemplate smsTemplate = null;        
        if (!ObjectUtils.isEmpty(whatsAppMessageType)) {
        	smsTemplate = whatsappSmsTemplateRepo.findByCode(whatsAppMessageType);
    	}
        
        if(Boolean.FALSE.equals(isSheduledCampaign)) {      
        List<Map<String, Object>> customerMobiles = getCustomerMobiles(appCampaignRequest);
        if (!ObjectUtils.isEmpty(customerMobiles)) {	
        	for(Map<String, Object> customerMobile : customerMobiles) {
        		whatsAppMessageService.whatsAppCampaignMessage(smsTemplate.getBody(),customerMobile.get("mobile").toString(),smsTemplate.getHeader(),smsTemplate.getFooter(),smsTemplate.getImageUrl());
        		SecondaryEntity entity = new SecondaryEntity();
        		entity.setDescription(customerMobile.get("mobile").toString());
        		secondaryRepository.save(entity);
        	}
            status = "Data from customer with orders! ";
    	}else {
    		WhatsAppCampaignRequestCustomerWithoutOrder customerWithoutOrder = new WhatsAppCampaignRequestCustomerWithoutOrder();
    		BeanUtils.copyProperties(appCampaignRequest, customerWithoutOrder);
    		List<Map<String, Object>> customerMobileWithotOrder = getCustomerWithoutOrderMobile(customerWithoutOrder);	     
    		     if (!ObjectUtils.isEmpty(customerMobileWithotOrder)) {
    		     	for(Map<String, Object> customerMobile : customerMobileWithotOrder) {
    		    		whatsAppMessageService.whatsAppCampaignMessage(smsTemplate.getBody(),customerMobile.get("mobile").toString(),smsTemplate.getHeader(),smsTemplate.getFooter(),smsTemplate.getImageUrl());
    		    		SecondaryEntity entity = new SecondaryEntity();
    		    		entity.setDescription(customerMobile.get("mobile").toString());
    		    		secondaryRepository.save(entity);
    		    	}
    			     status = "Data from customer without order! ";
    			}else {
    				 throw new CampaignException("Data Does Not Exist For This Request! ");
    			}
    		}  
        
        }else {
        	status = inserDataForSheduledMessage(appCampaignRequest,whatsAppMessageType,sheduledTime);
        }
        		return status;
        
   }
    
    
    public String inserDataForSheduledMessage(WhatsAppCampaignRequest appCampaignRequest,String whatsAppMessageType,Date sheduledTime)  throws JsonProcessingException{
    	ObjectMapper map = new ObjectMapper();
    	SecondaryEntity entity = new SecondaryEntity();
    	entity.setDescription(map.writeValueAsString(appCampaignRequest));
    	entity.setWhatsAppSheduledMessageType(whatsAppMessageType);
    	entity.setIsSheduledCampaign(true);
    	entity.setSheduledCampaignStatus(false);
    	entity.setSheduledCampaignTime(sheduledTime);
    	secondaryRepository.save(entity);
    	return "WhatsApp Campaign Sheduled! ";
    }
    
    
    
    
        
    	public void messagingSystemForSheduledCampaign() throws JsonMappingException, JsonProcessingException {   		
    		List<SecondaryEntity> allSheduledDatas = secondaryRepository.getAllSheduledData();
    		for(SecondaryEntity allSheduledData : allSheduledDatas) {   			
    			WhatsappSmsTemplate smsTemplate = null;        
                if (!ObjectUtils.isEmpty(allSheduledData.getWhatsAppSheduledMessageType())) {
                	smsTemplate = whatsappSmsTemplateRepo.findByCode(allSheduledData.getWhatsAppSheduledMessageType());
            	}                
                ObjectMapper map = new ObjectMapper();
                WhatsAppCampaignRequest appCampaignRequest = map.readValue(allSheduledData.getDescription(), WhatsAppCampaignRequest.class);
                List<Map<String, Object>> customerMobiles = getCustomerMobiles(appCampaignRequest);
                if (!ObjectUtils.isEmpty(customerMobiles)) {	
                	for(Map<String, Object> customerMobile : customerMobiles) {
                		whatsAppMessageService.whatsAppCampaignMessage(smsTemplate.getBody(),customerMobile.get("mobile").toString(),smsTemplate.getHeader(),smsTemplate.getFooter(),smsTemplate.getImageUrl());
                		SecondaryEntity entity = new SecondaryEntity();
                		entity.setDescription(customerMobile.get("mobile").toString());
                		secondaryRepository.save(entity);
                	}
            	}else {
            		WhatsAppCampaignRequestCustomerWithoutOrder customerWithoutOrder = new WhatsAppCampaignRequestCustomerWithoutOrder();
            		BeanUtils.copyProperties(appCampaignRequest, customerWithoutOrder);
            		List<Map<String, Object>> customerMobileWithotOrder = getCustomerWithoutOrderMobile(customerWithoutOrder);	     
            		     if (!ObjectUtils.isEmpty(customerMobileWithotOrder)) {
            		     	for(Map<String, Object> customerMobile : customerMobileWithotOrder) {
            		    		whatsAppMessageService.whatsAppCampaignMessage(smsTemplate.getBody(),customerMobile.get("mobile").toString(),smsTemplate.getHeader(),smsTemplate.getFooter(),smsTemplate.getImageUrl());
            		    		SecondaryEntity entity = new SecondaryEntity();
            		    		entity.setDescription(customerMobile.get("mobile").toString());
            		    		secondaryRepository.save(entity);
            		    	}

            			}else {
            				 throw new CampaignException("Data Does Not Exist For This Request! ");
            			}
            		}
    			
    		}
               
       }
    		
    	
    	
    	
  
    	
 }
	

	
	

