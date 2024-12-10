package com.meatigo.campaign.security;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtRequest {
	
	private String userName;
	private String password;

}