package com.meatigo.campaign.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.meatigo.campaign.req_res.AllCampaignDataResponse;
import com.meatigo.campaign.req_res.CamIsActiveUpdateResonse;
import com.meatigo.campaign.req_res.WhatsAppCampaignRequest;
import com.meatigo.campaign.service.WhatsAppCampaignService;


@RestController
@RequestMapping("/api")
public class WhatsAppCampaignController {
	
	@Autowired
	private WhatsAppCampaignService whatsAppCampaignService;
	
	
    @PostMapping("/campaign")
    public String campaignApi(@RequestBody WhatsAppCampaignRequest appCampaignRequest,@RequestParam String messageName,@RequestParam String campaignName,@RequestParam(required = false) boolean isSheduledCampaign,@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")@RequestParam(required = false) Date sheduledTime) throws JsonProcessingException {
    	String messagingResult = whatsAppCampaignService.whatsAppCampaignMessagingSystem(appCampaignRequest,messageName,campaignName,isSheduledCampaign,sheduledTime); 
    	return messagingResult;
    
    }
    
    @PostMapping("/campainData")
    public ResponseEntity<List<AllCampaignDataResponse>> getAllCampaignData(@RequestParam(required = false) boolean isSheduledCampaign){
    	return new ResponseEntity<>(whatsAppCampaignService.getAllCampaignData(isSheduledCampaign),HttpStatus.OK);
    }
    
    @PostMapping("/updateIsActive")
    public ResponseEntity<CamIsActiveUpdateResonse> campaignIsActiveUpdate(@RequestParam(required = true) String campaignName,@RequestParam(required = true) boolean isActive){
    	return new ResponseEntity<>(whatsAppCampaignService.campaignIsActiveUpdate(campaignName,isActive),HttpStatus.OK);
    }
    
    @PostMapping("/deleteCampain")
    public ResponseEntity<String> deleteCampaignByCampaignName(@RequestParam String campaignName){
    	return new ResponseEntity<>(whatsAppCampaignService.deleteCampaignByCampaignName(campaignName),HttpStatus.OK);
    }
    
    
    @PostMapping("/testSheduledData")
    public void testSheduledMessage() throws JsonMappingException, JsonProcessingException {
    	whatsAppCampaignService.messagingSystemForSheduledCampaign();
    }
    
    
    

}
