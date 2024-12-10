package com.meatigo.campaign.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.meatigo.campaign.req_res.ApiResponse;
import com.meatigo.campaign.service.WhatsAppTemplateService;

@RestController
@RequestMapping("v1/whatsApp")
public class WhatsAppTemplateController {
	
	@Autowired
	private WhatsAppTemplateService whatsAppTemplateService;
	
    @GetMapping("/allWhatsAppTemplate")
    public ResponseEntity<ApiResponse> getAllGupshupWhatsAppTemplate() {
        return new ResponseEntity<>(whatsAppTemplateService.getApiResponse(),HttpStatus.OK);
    }

}
