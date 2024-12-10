package com.meatigo.campaign.exception;

@SuppressWarnings("serial")
public class CampaignException extends RuntimeException{
	
	public CampaignException() {
		super();
	}

	public CampaignException(String message) {
		super(message);
	}

	public CampaignException(String message, Throwable e) {
		super(message, e);
	}


}
