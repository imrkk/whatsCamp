package com.meatigo.campaign.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.meatigo.campaign.req_res.ApiResponse;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


@Service
public class WhatsAppTemplateService {
	
	@Value("${whatsapp.template.api-url}")
	private String apiUrl;
	
	@Value("${whatsapp.template.user-id}")
	private String userId;
	
	@Value("${whatsapp.template.password}")
	private String password;
	
	
	public ApiResponse getApiResponse() {
	    OkHttpClient client = new OkHttpClient();
	    String urlWithParams = apiUrl + "&userid=" + userId + "&password=" + password;
	    Request request = new Request.Builder()
	            .url(urlWithParams)
	            .build();

	    try (Response response = client.newCall(request).execute()) {
	        if (response.isSuccessful()) {
	            String responseBody = response.body().string();
	            ApiResponse apiResponse = new Gson().fromJson(responseBody, ApiResponse.class);
	            return apiResponse;
	        } else {
	            return null;
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }
	}

}
