package com.sitemapdev.addressservice.service;

import com.sitemapdev.addressservice.entity.Address;
import com.sitemapdev.addressservice.repository.AddressRepository;
import com.sitemapdev.addressservice.request.AddressRequest;
import com.sitemapdev.addressservice.response.AddressResponse;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class AddressService {


    private AddressRepository addressRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    AddressService(AddressRepository addressRepository){
        this.addressRepository =addressRepository;
    }

    public AddressResponse getAddressById(Long id){
        Optional<Address> addressOptional = addressRepository.findById(id);
        AddressResponse addressResponse = modelMapper.map(addressOptional, AddressResponse.class);
        return addressResponse;

    }

//    public AddressResponse findAddressbyEployeeId( Long employeeId){
//
//        Address address=  addressRepository.findAddressByEmployeeId(employeeId);
//        AddressResponse addressResponse=  modelMapper.map(address, AddressResponse.class);
//        return addressResponse;
//
//
//
//    }

    public AddressService(AddressRepository addressRepository, ModelMapper modelMapper) {
        this.addressRepository = addressRepository;
        this.modelMapper = modelMapper;
    }

    public AddressResponse findAddressByEmployeeId(Long employeeId) {
        Address address = addressRepository.findAddressByEmployeeId(employeeId);
        AddressResponse addressResponse=  modelMapper.map(address, AddressResponse.class);
        return addressResponse;
    }
    
    public List<AddressResponse> getAllAddress(){
    	List<Address> empAdress = addressRepository.findAll();
    	List<AddressResponse> employeeResponses = Arrays.asList(modelMapper.map(empAdress, AddressResponse[].class));
    	return employeeResponses;
    	}
    
    public AddressResponse addAddress(AddressRequest addressRequest) {
        Address address = modelMapper.map(addressRequest, Address.class);
        Address savedAddress = addressRepository.save(address);
        return modelMapper.map(savedAddress, AddressResponse.class);
    }

}
