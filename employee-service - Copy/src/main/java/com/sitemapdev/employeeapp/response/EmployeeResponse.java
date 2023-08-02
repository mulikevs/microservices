package com.sitemapdev.employeeapp.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeResponse {

    private Long id;


    private String uuid;


    private String name;


    private String email;

    private String bloodgroup;

    private AddressResponse addressResponse;
}
