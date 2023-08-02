package com.sitemapdev.employeeapp.openfeignclients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.sitemapdev.employeeapp.request.AddressRequest;
import com.sitemapdev.employeeapp.response.AddressResponse;


//
@FeignClient(name = "ADDRESS-APP", path="/address-app/api")
public interface AddressClient {
	 @GetMapping("/address/{employeeId}")
	    public ResponseEntity<AddressResponse> getAddressByEmployeeId(@PathVariable("employeeId") Long id);
	 
	 @GetMapping("/address/")
	    public ResponseEntity<List<AddressResponse>> getAllAddress();
	 
	 @PostMapping("/address/")
	    public ResponseEntity<AddressResponse> addAddress(@RequestBody AddressRequest addressRequest);

}
