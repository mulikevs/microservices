package com.sitemapdev.addressservice.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressRequest {
	
	   private String uuid;


	    private String lane1;


	    private String lane2;


	    private String state;


	    private String zip;
	    private Long employeeId;

}
