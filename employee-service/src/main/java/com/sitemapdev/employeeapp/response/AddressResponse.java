package com.sitemapdev.employeeapp.response;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressResponse {


    private Long id;


    private String uuid;


    private String lane1;


    private String lane2;


    private String state;


    private String zip;
}
