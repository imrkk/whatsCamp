package com.meatigo.campaign.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.meatigo.campaign.primaryDbEntity.PrimaryEntity;
import com.meatigo.campaign.secondaryDbEntity.SecondaryEntity;
import com.meatigo.campaign.service.TestService;


@RestController
@RequestMapping("/api")
public class TestController {
	
	@Autowired
	private TestService testService;
	
	@PostMapping("/test")
	public String getData() {
		return "done";
	}
	
	@PostMapping("/primary")
	public ResponseEntity<List<PrimaryEntity>> getPrimaryDbData(){
		return new ResponseEntity<>(testService.getPrimaryDbData(),HttpStatus.OK);
	}
	
	@PostMapping("/secondary")
	public ResponseEntity<List<SecondaryEntity>> getSecondaryDbData(){
		return new ResponseEntity<>(testService.getSecondaryDbData(),HttpStatus.OK);
	}
	
	@PostMapping("/userData")
	public ResponseEntity<List<String>> getAllUserName(){
		return new ResponseEntity<>(testService.getAllUser(),HttpStatus.OK);
	}

}
