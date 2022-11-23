package com.ty.moin.sfashionworld.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ty.moin.sfashionworld.dto.Merchant;
import com.ty.moin.sfashionworld.dto.ResponseStructure;
import com.ty.moin.sfashionworld.service.MerchantService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/merchants")

public class MerchantController {

	@Autowired
	private MerchantService merchantService;

	@ApiOperation(value = "save Merchant", notes = "Input is Merchant Object and return merchant object")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "created") })
	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	@PreAuthorize("permitAll()")
	public ResponseEntity<ResponseStructure<Merchant>> saveMerchant(@RequestBody @Validated Merchant merchant) {
		return merchantService.saveMerchant(merchant);
	}

	@ApiOperation(value = "Get Merchant By Id", notes = "Input is Merchant Id and return Merchant Object")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 404, message = "Not Found") })
	@GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	@Secured("ROLE_ADMIN")
	public ResponseEntity<ResponseStructure<Merchant>> getMerchantById(@PathVariable int id) {
		return merchantService.getMerchantById(id);
	}

	@ApiOperation(value = "Get Merchant By Phone", notes = "Input is Merchant's Phone number and return merchant object")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 404, message = "Not Found") })
	@GetMapping(value = "/phone/{phone}", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	@Secured("ROLE_ADMIN")
	public ResponseEntity<ResponseStructure<Merchant>> findMerchantByPhone(@PathVariable long phone) {
		return merchantService.findMerchantByPhone(phone);
	}

	@ApiOperation(value = "Get Merchant By Email", notes = "Input is Merchant's email and return merchant objet")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 404, message = "Not Found") })
	@GetMapping(value = "/email/{email}", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	@Secured("ROLE_ADMIN")
	public ResponseEntity<ResponseStructure<Merchant>> findMerchantByEmail(@PathVariable String email) {
		return merchantService.findMerchantByEmail(email);
	}

	@ApiOperation(value = "Delete Merchant By Id", notes = "Input is Merchants Id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Not Found"),
			@ApiResponse(code = 404, message = "Not Found") })
	@DeleteMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	@Secured("ROLE_ADMIN")
	public ResponseEntity<ResponseStructure<Merchant>> deleteMerchantById(@PathVariable int id) {
		return merchantService.deleteMerchantById(id);
	}

	@ApiOperation(value = "get all merchant")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 404, message = "Data Not Found") })
	@GetMapping(value = "/getall", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	@Secured("ROLE_ADMIN")
	public ResponseEntity<ResponseStructure<List<Merchant>>> getAllMerchant() {
		return merchantService.getAllMerchant();
	}

	@ApiOperation(value = "Validate Merchant By Email", notes = "Inputs are Merchant's email id and password and return merchant object")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 404, message = "Not Found") })
	@PostMapping(value = "/{email}/{password}", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	@Secured("ROLE_MERCHANT")
	public ResponseEntity<ResponseStructure<String>> validateMerchantByEmail(@PathVariable String email,
			@PathVariable String password) {

		return merchantService.validateMerchantByEmail(email, password);
	}

	@ApiOperation(value = "Validate Merchant By Phone", notes = "Inputs are Merchants phone number and password and return merchant object")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 404, message = "Not Fond") })
	@PutMapping(value = "/{phone}/{password}", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	@Secured("ROLE_MERCHANT")
	public ResponseEntity<ResponseStructure<Merchant>> validateMerchantByPhone(@PathVariable long phone,
			@PathVariable String password) {
		return merchantService.validateMerchantByPhone(phone, password);
	}

	@ApiOperation(value = "Update Merchant", notes = "input is Merchants obj and return Merchant obj with id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 404, message = "Not Found") })
	@PutMapping(consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	@Secured("ROLE_MERCHANT")
	public ResponseEntity<ResponseStructure<Merchant>> updateMerchant(@RequestBody Merchant merchant) {
		return merchantService.updateMerchant(merchant);
	}

	@GetMapping(value = "/otp/{otp}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	@Secured("ROLE_MERCHANT")
	public ResponseEntity<ResponseStructure<Merchant>> validateOtp(@PathVariable int otp) {

		return merchantService.validateOtp(otp);
	}
}
