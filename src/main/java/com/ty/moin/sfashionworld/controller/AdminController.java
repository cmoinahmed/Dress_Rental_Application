package com.ty.moin.sfashionworld.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ty.moin.sfashionworld.dto.Admin;
import com.ty.moin.sfashionworld.dto.ResponseStructure;
import com.ty.moin.sfashionworld.service.AdminService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/admins")
public class AdminController {

	@Autowired
	private AdminService adminService;

	@ApiOperation(value = "save Admin details ", notes = "input admin object and output admit object created")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "SUCCESSFULLY CREATED"),
			@ApiResponse(code = 404, message = "ADMIN DETAILS NOT FOUND") })

	@PostMapping(consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<Admin>> saveAdmin(@RequestBody @Valid Admin admin) {
		return adminService.saveService(admin);
	}

	@ApiOperation(value = "find Admin details by id", notes = "input int admin id and the output admin object ")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "SUCCESS"),
			@ApiResponse(code = 404, message = "ADMIN DETAILS NOT FOUND") })
	@GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<ResponseStructure<Admin>> findAdminById(@PathVariable int id) {
		return adminService.findAdminById(id);
	}

	@ApiOperation(value = "delete Admin by id ", notes = "input int admin id and the output Admin object deleted")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "SUCCESS"),
			@ApiResponse(code = 404, message = "ADMIN DETAILS NOT FOUND") })
	@DeleteMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<ResponseStructure<Admin>> deleteAdminById(@PathVariable int id) {
		return adminService.deleteAdminById(id);
	}

	@ApiOperation(value = "find all Admin", notes = "fetch all the admin details")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "SUCCESS"),
			@ApiResponse(code = 404, message = "ADMIN DETAILS NOT FOUND") })
	@GetMapping(value = "/getall", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<ResponseStructure<List<Admin>>> findAllAdmin() {
		return adminService.getAllAdmins();
	}

	@ApiOperation(value = "find Admin by email ", notes = "input String email and output is admin object")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "SUCCESS"),
			@ApiResponse(code = 404, message = "ADMIN DETAILS NOT FOUND") })
	@GetMapping(value = "/email", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<ResponseStructure<Admin>> findAdminByEmail(@RequestParam String email) {
		return adminService.findAdminByEmail(email);
	}

	@ApiOperation(value = "find Admin by phone number", notes = "input double phone and output is admin object")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "SUCCESS"),
			@ApiResponse(code = 404, message = "ADMIN DETAILS NOT FOUND") })
	@GetMapping(value = "/phone/{phone}", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<ResponseStructure<Admin>> findAdminByPhone(@PathVariable long phone) {
		return adminService.findAdminByPhone(phone);

	}

	@ApiOperation(value = "update Admin ", notes = "update the Admin details by using admin object")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "SUCCESS"),
			@ApiResponse(code = 404, message = "ADMIN DETAILS NOT FOUND") })
	@PutMapping(consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<Admin>> updateAdmin(@RequestBody Admin admin) {
		return adminService.updateAdmin(admin);
	}

	@ApiOperation(value = "Validate Admin By Email and password", notes = "input String email, String password ---output Admin obj")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "SUCCESS"),
			@ApiResponse(code = 404, message = "ADMIN Email NOT FOUND"),
			@ApiResponse(code = 401, message = "Invalid Crendentials") })
	@PostMapping(value = "/email", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<Admin>> adminValidateByEmail(@RequestParam String email,
			@RequestParam String password) {
		return adminService.adminValidateByEmail(email, password);
	}

	@ApiOperation(value = "Validate Admin By Phone and password", notes = "input long phone,String password ---output Admin obj")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "SUCCESS"),
			@ApiResponse(code = 404, message = "ADMIN Phone NOT FOUND"),
			@ApiResponse(code = 401, message = "Invalid Crendentials") })
	@PostMapping(value = "/phone", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<Admin>> adminValidateByPhone(@RequestParam long phone,
			@RequestParam String password) {
		return adminService.adminValidateByPhone(phone, password);
	}

}
