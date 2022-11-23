package com.ty.moin.sfashionworld.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ty.moin.sfashionworld.dto.Customer;
import com.ty.moin.sfashionworld.dto.Rent;
import com.ty.moin.sfashionworld.dto.ResponseStructure;
import com.ty.moin.sfashionworld.service.RentService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/rents")
public class RentController {
	@Autowired
	private RentService rentService;

	@ApiOperation(value = "Save Rent", notes = "Input is Rent Object and Return Object")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Sucessfully Saved") })
	@PostMapping(value="/{id}",consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	@PreAuthorize("hasRole('CUSTOMER')")
	public ResponseEntity<ResponseStructure<Rent>> saveRent(@PathVariable int id,@RequestBody Rent rent) {
		return rentService.saveRent(id,rent);
	}

	@ApiOperation(value = "Get Rent By Id", notes = "Input Is Rent Object And Return Same Object With Id ")
	@ApiResponses(value = { @ApiResponse(code = 302, message = "Id Found "),
			@ApiResponse(code = 404, message = "Id Not Found ") })
	@GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	@PreAuthorize("hasRole('CUSTOMER')")
	public ResponseEntity<ResponseStructure<Rent>> getRentById(@PathVariable int id) {
		return rentService.getRentById(id);
	}

	@ApiOperation(value = "Get All Rents", notes = "Input Is Rent Object And Return List Of Same Objects")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success") })
	@GetMapping(value = "/all", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	@PreAuthorize("hasRole('CUSTOMER','MERCHANT')")
	public ResponseEntity<ResponseStructure<List<Rent>>> getAllRents() {
		return rentService.getAllRents();
	}

	@ApiOperation(value = "Delete Rent By Id", notes = "Input Is Rent Object And Delete Same Object With Id ")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 404, message = "Id Not Found") })
	@DeleteMapping(value = "/{id}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	@PreAuthorize("hasRole('CUSTOMER')")
	public ResponseEntity<ResponseStructure<Rent>> deleteRentById(@PathVariable int id) {
		return rentService.deleteRentById(id);
	}

	@ApiOperation(value = "Get Rent By RentDate", notes = "Input Is RentDate Object And Return Same Object ")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 404, message = "RentDate Not Found") })
	@GetMapping(produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<List<Rent>>> findRentByDate(@RequestParam LocalDate rentDate) {
		return rentService.findByRentDate(rentDate);
	}

	@ApiOperation(value = "Get All Rents By Customer", notes = "Input Is Customer Object And Return List Of Rents Object")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 404, message = "Input Mismatch") })
	@PostMapping(value = "/getrentsbycustomer", consumes = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_XML_VALUE,
					MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<List<Rent>>> findRentByCustomer(@RequestBody Customer customer) {
		return rentService.findRentByCustomer(customer);
	}
}
