package com.meatigo.campaign.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class WhatsAppMessageService {
	
	
	  @Value("${whatsapp.sms.url}")
	  private String whatsappSmsUrl;
	  
	  @Value("${whatsapp.sms.user-id}")
	  private String userId;
	  
	  @Value("${whatsapp.sms.password}")
	  private String password;
	
		private static final String ENCODING = "UTF-8";

		private static final Logger logger = LoggerFactory.getLogger(WhatsAppMessageService.class);
	
	
		public Boolean whatsAppCampaignMessage(String message, String mobile,String header, String footer,String imageUrl) {
		
		try {
					
			String data = "";	
			if(!ObjectUtils.isEmpty(imageUrl)) {
				data += "method=SENDMEDIAMESSAGE";
				data += "&format=json";
				data += "&userid=" + userId;
				data += "&password=" + URLEncoder.encode(password, ENCODING);
				data += "&send_to=" + URLEncoder.encode(mobile, ENCODING);
				data += "&v=1.1";
				data += "&auth_scheme=plain";
				data += "&msg_type=IMAGE";
				data += "&media_url="+ URLEncoder.encode(imageUrl, ENCODING);
				data += "&caption=" + URLEncoder.encode(message, ENCODING);
				if(!ObjectUtils.isEmpty(footer)) {
					data += "&footer=" + URLEncoder.encode(footer, ENCODING);
				}
				data += "&isHSM=" + true;
				data += "&isTemplate=" + true;
			}

			if(!ObjectUtils.isEmpty(header)) {
				data += "method=SendMessage";
				data += "&format=json";
				data += "&userid=" + userId;
				data += "&password=" + URLEncoder.encode(password, ENCODING);
				data += "&send_to=" + URLEncoder.encode(mobile, ENCODING);
				data += "&v=1.1";
				data += "&auth_scheme=plain";
				data += "&msg_type=HSM";
				data += "&msg=" + URLEncoder.encode(message, ENCODING);
				data += "&header=" + URLEncoder.encode(header, ENCODING);
				if(!ObjectUtils.isEmpty(footer)) {
					data += "&footer=" + URLEncoder.encode(footer, ENCODING);
				}
				data += "&isHSM=" + true;
				data += "&isTemplate=" + true;
				}
			
			if(ObjectUtils.isEmpty(header) && ObjectUtils.isEmpty(footer) && ObjectUtils.isEmpty(imageUrl)) {
				data += "method=SendMessage";
				data += "&format=json";
				data += "&userid=" + userId;
				data += "&password=" + URLEncoder.encode(password, ENCODING);
				data += "&send_to=" + URLEncoder.encode(mobile, ENCODING);
				data += "&v=1.1";
				data += "&auth_scheme=plain";
				data += "&msg_type=HSM";
				data += "&msg=" + URLEncoder.encode(message, ENCODING);
				data += "&isHSM=" + true;
				data += "&isTemplate=" + true;
			}

			URL url1 = new URL(whatsappSmsUrl + data);
			HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
			conn.setRequestMethod("GET");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			StringBuilder buffer = new StringBuilder();
			while ((line = rd.readLine()) != null) {
				buffer.append(line).append("\n");
			}
			
			conn.connect();
			conn.disconnect();
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}
	

}
