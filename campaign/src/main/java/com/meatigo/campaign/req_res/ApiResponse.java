package com.meatigo.campaign.req_res;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse {
	
    private List<Data> data;
    private Meta meta;

}
