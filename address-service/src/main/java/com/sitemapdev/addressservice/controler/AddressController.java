package com.sitemapdev.addressservice.controler;

import com.sitemapdev.addressservice.request.AddressRequest;
import com.sitemapdev.addressservice.response.AddressResponse;
import com.sitemapdev.addressservice.service.AddressService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private AddressService addressService;
//    @GetMapping("/{id}")
//    public ResponseEntity<AddressResponse> getAddressById(@PathVariable Long id){
//        AddressResponse addressResponse = addressService.getAddressById(id);
//        return ResponseEntity.status(HttpStatus.OK).body(addressResponse);
//    }

//    @GetMapping("/{employeeId}")
//    public ResponseEntity<AddressResponse> getAddressByEmployeeId(@PathVariable("employeeId") Long id){
//        AddressResponse addressResponse = addressService.findAddressbyEployeeId(id);
//        return ResponseEntity.status(HttpStatus.OK).body(addressResponse);
//    }
    
    @GetMapping("/")
    public ResponseEntity<List<AddressResponse>> getAllAddress(){
    	List<AddressResponse> addressList = addressService.getAllAddress();
    	return ResponseEntity.status(HttpStatus.OK).body(addressList);
    	
    }
    
  

    @GetMapping("/{employeeId}")
    public ResponseEntity<AddressResponse> getAddressByEmployeeId(@PathVariable("employeeId") Long id) {
        AddressResponse addressResponse = addressService.findAddressByEmployeeId(id);
        return ResponseEntity.status(HttpStatus.OK).body(addressResponse);
    }
    
    @PostMapping("/")
    public ResponseEntity<AddressResponse> addAddress(@RequestBody AddressRequest addressRequest) {
    	System.out.println(addressRequest.getEmployeeId());
        AddressResponse addedAddress = addressService.addAddress(addressRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedAddress);
    }


}
