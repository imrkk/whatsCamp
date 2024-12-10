package com.meatigo.campaign.security;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class JwtResponse {
	
	private String jwtToken;
	private String userName;

}
