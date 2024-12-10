package com.meatigo.campaign.req_res;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Data {
	
    private long id;
    private String name;
    private String category;
    private String language;
    private String type;
    private String header;
    private String body;
    private String footer;
    private String button_type;
    private String quality_score;
    private String status;
    private long creation_time;
    private long updation_time;
    
}
