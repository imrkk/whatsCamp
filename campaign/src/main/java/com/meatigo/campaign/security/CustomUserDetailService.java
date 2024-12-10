package com.meatigo.campaign.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.meatigo.campaign.exception.CampaignException;
import com.meatigo.campaign.primaryDbEntity.User;
import com.meatigo.campaign.primaryDbRepo.UserRepo;


public class CustomUserDetailService implements UserDetailsService{
	
	@Autowired
	private UserRepo userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub		
		User user = userRepo.findByUserName(username).orElseThrow(() -> new CampaignException("User not found !!"));
		return user;
	}

}
