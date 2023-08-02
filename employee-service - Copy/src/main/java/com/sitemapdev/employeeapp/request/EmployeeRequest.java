package com.sitemapdev.employeeapp.request;



import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeRequest {
	private String uuid;


    private String name;


    private String email;

    private String bloodgroup;
    
    private AddressRequest addressRequest;

}
