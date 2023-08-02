package com.sitemapdev.employeeapp.service;

import ch.qos.logback.classic.spi.TurboFilterList;
import com.sitemapdev.employeeapp.entity.Employees;
import com.sitemapdev.employeeapp.openfeignclients.AddressClient;
import com.sitemapdev.employeeapp.repository.EmployeesRepository;
import com.sitemapdev.employeeapp.request.AddressRequest;
import com.sitemapdev.employeeapp.request.EmployeeRequest;
import com.sitemapdev.employeeapp.response.AddressResponse;
import com.sitemapdev.employeeapp.response.EmployeeResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
@Autowired
    private EmployeesRepository employeesRepository;


	@Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private WebClient webclient;
    
//    @Autowired
//    private DiscoveryClient discoveryClient;
    
    @Autowired
    private LoadBalancerClient loadBalancerClient;
    
    @Autowired
    private AddressClient addressClient;



//    public EmployeeService(@Value("${addressservice.base.url}")String addressServiceBaseURL,
//                           RestTemplateBuilder builder) {
//
//        this.restTemplate = builder
//                .rootUri(addressServiceBaseURL)
//                .build();
//    }

    // Create a new employee
    public Employees createEmployee(Employees employee) {
        return employeesRepository.save(employee);
    }

    // Read all employees
    public List<EmployeeResponse> getAllEmployees() {
         List<Employees> employeeList = employeesRepository.findAll();
         List<EmployeeResponse> employeeResponses = Arrays.asList(modelMapper.map(employeeList, EmployeeResponse[].class));
//         employeeResponses.forEach(employee ->{
//        	 employee.setAddressResponse(addressClient.getAddressByEmployeeId(employee.getId()).getBody());
//         });
         ResponseEntity<List<AddressResponse>> allAddress = addressClient.getAllAddress();
         List<AddressResponse> addressBody = allAddress.getBody();
         employeeResponses.forEach(employee ->{
        	 for(AddressResponse addRespo:addressBody) {
        		 if(addRespo.getId()==employee.getId()) {
        			 employee.setAddressResponse(addRespo);
        		 }
        	 }
         });
         return employeeResponses;
         
    }

    // Read an employee by ID
//    public EmployeeResponse getEmployeeById(Long id) {
//
//        //AddressResponse addressResponse = new AddressResponse();
//        Optional<Employees> optionalEmployee = employeesRepository.findById(id);
//        EmployeeResponse employeeResponse= modelMapper.map(optionalEmployee, EmployeeResponse.class);
//
////        AddressResponse addressResponse = restTemplate.getForObject("/address/{id}",AddressResponse.class, id);
//        AddressResponse addressResponse = restTemplate.getForObject("/address/{id}",AddressResponse.class, id);
//        employeeResponse.setAddressResponse(addressResponse);
//        return employeeResponse;
//    }
    
    
    //Reactive 
    public EmployeeResponse getEmployeeById(Long id) {

        //AddressResponse addressResponse = new AddressResponse();
        Optional<Employees> optionalEmployee = employeesRepository.findById(id);
        EmployeeResponse employeeResponse= modelMapper.map(optionalEmployee, EmployeeResponse.class);

//        AddressResponse addressResponse = CallToAddressServiceWithRestTemplate(id);
//        AddressResponse addressResponse = CalltoAddressService(id);
        
        ResponseEntity<AddressResponse> addressResponseEntity = addressClient.getAddressByEmployeeId(id);
        
        AddressResponse addressResponse = addressResponseEntity.getBody();
        employeeResponse.setAddressResponse(addressResponse);
        return employeeResponse;
    }

	private AddressResponse CallToAddressServiceWithRestTemplate(Long id) {
		
		//Get IP details from the discoceryService
//		List<ServiceInstance> instances =discoveryClient.getInstances("address-app");
//		ServiceInstance serviceInstance =instances.get(0);
//		String uri =serviceInstance.getUri().toString();
		
		ServiceInstance serviceInstance= loadBalancerClient.choose("address-app");
		String uri =serviceInstance.getUri().toString();
		String contextRoot = serviceInstance.getMetadata().get("configPath");
		
		
		System.out.println("URI[.................]"+uri+contextRoot);
		
		//remove Coop Domain
		String coopDomain = "truugbcm3.CO-OPBANK.CO.KE";
		String localhostDomain = "localhost";

		String modifiedUri = uri.replace(coopDomain, localhostDomain);
		
		System.out.println("modifiedUri[.................]"+modifiedUri+contextRoot);
		
		
		return restTemplate.getForObject(modifiedUri+contextRoot+"/address/{id}",AddressResponse.class, id);
	}

	private AddressResponse CalltoAddressService(Long id) {
									return webclient
        										.get()
        										.uri("/address/"+id)
        										.retrieve()
        										.bodyToMono(AddressResponse.class)
        										.block();
	}
    
   

    // Update an employee
    public Employees updateEmployee(Long id, Employees updatedEmployee) {
        Optional<Employees> optionalEmployee = employeesRepository.findById(id);
        if (optionalEmployee.isPresent()) {
            Employees employee = optionalEmployee.get();
            employee.setName(updatedEmployee.getName());
            employee.setEmail(updatedEmployee.getEmail());
            employee.setBloodgroup(updatedEmployee.getBloodgroup());
            return employeesRepository.save(employee);
        }
        return null; // return null if not found
    }

    // Delete an employee
    public void deleteEmployee(Long id) {
        employeesRepository.deleteById(id);
    }
    
    public EmployeeResponse addEmployee(EmployeeRequest employeeRequest) {
        Employees employee = modelMapper.map(employeeRequest, Employees.class);
        Employees savedEmployee = employeesRepository.save(employee);
        EmployeeResponse employeeResponse= modelMapper.map(savedEmployee, EmployeeResponse.class);

        // Associate address with employee
        AddressRequest addressRequest = employeeRequest.getAddressRequest();
        addressRequest.setEmployeeId(savedEmployee.getId());
        System.out.println("###############################################################");
        System.out.println(savedEmployee.getId());
        System.out.println("###############################################################");
        ResponseEntity<AddressResponse> addressResponseEntity = addressClient.addAddress(addressRequest);
        
        AddressResponse addressResponse = addressResponseEntity.getBody();
        employeeResponse.setAddressResponse(addressResponse);
        return employeeResponse;

    }
}

