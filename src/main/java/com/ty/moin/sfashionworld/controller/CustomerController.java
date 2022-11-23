package com.ty.moin.sfashionworld.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ty.moin.sfashionworld.dto.Customer;
import com.ty.moin.sfashionworld.dto.ResponseStructure;
import com.ty.moin.sfashionworld.dto.User;
import com.ty.moin.sfashionworld.service.CustomerService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/customers")
public class CustomerController {
	@Autowired
	private CustomerService customerService;

	@ApiOperation(value = "save customer", notes = "input is Customer obj and return Customer obj with id")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Successfully saved")})
			
	@PostMapping(consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	@PreAuthorize("permitAll()")
	public ResponseEntity<ResponseStructure<Customer>> saveCustomer(@RequestBody Customer customer) {
		return customerService.saveCustomer(customer);
	}

	@ApiOperation(value = "fetch Customer by id", notes = "input is id of the customer obj and return customer obj with id")
	@ApiResponses(value = { @ApiResponse(code = 302, message = "Successfully found"),
			@ApiResponse(code = 404, message = "Not Found") })
	@GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })

	public ResponseEntity<ResponseStructure<Customer>> findCustomerById(@PathVariable int id) {
		return customerService.findCustomerById(id);

	}

	@ApiOperation(value = "fetch all Customer", notes = "return the list of customers")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Fetched all the customers obj") })
	@GetMapping(value = "/getall", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })

	public ResponseEntity<ResponseStructure<List<Customer>>> getAllCustomers(@RequestParam int merchantId) {
		return customerService.getAllCustomers(merchantId);
	}

	@ApiOperation(value = "delete Customer obj", notes = "input is id of the customer obj and output is String")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully deleted"),
			@ApiResponse(code = 404, message = "Not Found") })
	@DeleteMapping(value = "/{id}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })

	public ResponseEntity<ResponseStructure<Customer>> deleteCustomerById(@PathVariable int id) {
		return customerService.deleteCustomerById(id);
	}

	@ApiOperation(value = "fetch Customer by email", notes = "input is email of the customer obj and return customer obj with id")
	@ApiResponses(value = { @ApiResponse(code = 302, message = "Found"),
			@ApiResponse(code = 404, message = "Not Found") })
	@GetMapping(value = "/email/{email}", produces = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<Customer>> findCustomerByEmail(@PathVariable String email) {
		return customerService.findCustomerByEmail(email);
	}

	@ApiOperation(value = "fetch Customer by phone", notes = "input is phoneNumber of the customer obj and return customer obj with id")
	@ApiResponses(value = { @ApiResponse(code = 302, message = "Success"),
			@ApiResponse(code = 404, message = "Not Found") })
	@GetMapping(value = "/phone/{phone}", produces = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<Customer>> findCustomerByPhone(@PathVariable long phone) {
		return customerService.findCustomerByPhone(phone);
	}

	@ApiOperation(value = "Validate Customer By Email", notes = "Inputs are Customer's email id and password and return customer object")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 404, message = "Not Found") })
	@GetMapping(value = "/{email}/{password}", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	@PreAuthorize("hasRole('CUSTOMER')")
	public ResponseEntity<ResponseStructure<Object>> validateCustomerByEmail(@PathVariable String email,
			@PathVariable String password) {
		return customerService.validateCustomerByEmail(email, password);
	}
	
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	@PreAuthorize("hasRole('CUSTOMER')")
	public ResponseEntity<ResponseStructure<Customer>> validateOtp(@RequestParam int otp){
		return customerService.validateOtp(otp);
	}
	

	@ApiOperation(value = "Validate Customer By Phone", notes = "Inputs are Customers phone number and password and return customer obj")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 404, message = "Not Fond") })
	@PutMapping(value = "/{phone}/{password}", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	@PreAuthorize("hasRole('CUSTOMER')")
	public ResponseEntity<ResponseStructure<Customer>> validateCustomerByPhone(@PathVariable long phone,
			@PathVariable String password) {
		return customerService.validateCustomerByPhone(phone, password);
	}

	@ApiOperation(value = "Update Customer", notes = "input is Customer obj and return Customer obj with id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 404, message = "Not Found") })
	@PutMapping(consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	@PreAuthorize("hasRole('CUSTOMER')")
	public ResponseEntity<ResponseStructure<Customer>> updateCustomer(@RequestBody Customer customer) {
		return customerService.updateCustomer(customer);
	}

}
