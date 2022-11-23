package com.ty.moin.sfashionworld.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ty.moin.sfashionworld.dao.MerchantDao;
import com.ty.moin.sfashionworld.dto.Dress;
import com.ty.moin.sfashionworld.dto.ResponseStructure;
import com.ty.moin.sfashionworld.service.DressService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/dresses")
@Validated
public class DressController {

	@Autowired
	private DressService dressService;
	
	@Autowired
	private MerchantDao merchantDao;

	@ApiOperation(value="Save the Dress Obj",notes="Input is dress obj and return dress obj with id")
	@ApiResponses(value= {@ApiResponse(code=201,message = "dress obj created")})
	@PostMapping(value="/{id}",consumes = { MediaType.ALL_VALUE}, produces = { MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<ResponseStructure<Dress>> saveDress(@RequestPart("dress") String jsonDress,@PathVariable("id") int merchantId,
			@RequestPart("file") MultipartFile file) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		Dress dress = mapper.readValue(jsonDress, Dress.class);
		dress.setImage(file.getBytes());
		dress.setMerchant(merchantDao.getMerchantById(merchantId));
		return dressService.saveDress(dress);
	}

	@ApiOperation(value="fetch Dress By Id",notes="Input is an integer(id) and return dress obj")
	@ApiResponses(value= {@ApiResponse(code=302,message = "Dress obj Found"),
			@ApiResponse(code=404,message = "Dress obj Not Found")})
	@GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE })
	@PreAuthorize("hasAnyRole('CUSTOMER','MERCHANT')")
	public ResponseEntity<ResponseStructure<Dress>> getDressById(@PathVariable int id) {
		return dressService.getDressById(id);
	}
	
	
	@ApiOperation(value="fetch All dress obj",notes="return list of dress Obj")
	@ApiResponses(value= {@ApiResponse(code=200,message = "Fetched all dress objects")})
	@GetMapping(produces= {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})
	@PreAuthorize("hasRole('CUSTOMER','MERCHANT')")
	public ResponseEntity<ResponseStructure<List<Dress>>> getAllDresses() {
		return dressService.getAllDresses();
	}
	
	
	@ApiOperation(value="Delete the dress obj by id",notes="input is int(id) and return dress obj")
	@ApiResponses(value= {@ApiResponse(code=200,message = "dress obj deleted successfull"),
			@ApiResponse(code=404,message="dress obj with id not found")})
	@DeleteMapping(value="/{id}",produces= {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})
	@PreAuthorize("hasRole('MERCHANT')")
	public ResponseEntity<ResponseStructure<Dress>> deleteDressById(@PathVariable int id) {
		return dressService.deleteDressById(id);
	}
	
	@ApiOperation(value="Fetch Dresses By Price Range",notes="input is double lowprice and highprice and return list of dress obj")
	@ApiResponses(value= {@ApiResponse(code=200,message="fetched list of dresses with pricerange"),
			@ApiResponse(code=404,message = "Dress is not found for the specified price range")})
	@GetMapping(value="/getbyrange",produces = {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})
	@PreAuthorize("hasRole('CUSTOMER','MERCHANT')")
	public ResponseEntity<ResponseStructure<List<Dress>>> getDressesByPriceRange(@RequestParam("value1") double lowPrice,@RequestParam("value2") double highPrice) {
		return dressService.getDressesByRange(lowPrice, highPrice);
	}
	
	@ApiOperation(value="Fetch Dresses By Brand Name",notes="input is String brandname and return list of dresses")
	@ApiResponses(value= {@ApiResponse(code=200,message = "fetched list of dresses with brandname"),
			@ApiResponse(code=404,message = "dress with the specified brand name is not found")})
	@GetMapping(value="/getbybrand/{brand}",produces= {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})
	@PreAuthorize("hasRole('CUSTOMER','MERCHANT')")
	public ResponseEntity<ResponseStructure<List<Dress>>> getDressesByBrandName(@PathVariable String brand) {
		return dressService.getDressesByBrandName(brand);
	}
	
	@ApiOperation(value="Fetch Dresses By size",notes="input is String dress size and return list of dresses")
	@ApiResponses(value= {@ApiResponse(code=200,message = "fetched list of dress with specified size successfully"),
			@ApiResponse(code=400,message="Dress is not found for the specified size")})
	@GetMapping(value="/getbysize/{size}",produces = {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})
	@PreAuthorize("hasRole('CUSTOMER')")
	public ResponseEntity<ResponseStructure<List<Dress>>> getDressesBySize(@PathVariable String size) {
		return dressService.getDressesBySize(size);
	}
	
	
	@ApiOperation(value="Fetch Dresses By name",notes="input is String dressname and return list of dresses")
	@ApiResponses(value= {@ApiResponse(code=200,message = "fetched list of dress with specified dress name successfully"),
			@ApiResponse(code=400,message="Dress is not found for the specified dress name")})
	@GetMapping(value="/getbyname/{name}",produces = {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})
	@PreAuthorize("hasRole('CUSTOMER')")
	public ResponseEntity<ResponseStructure<List<Dress>>> getDressesByName(@PathVariable String name) {
		return dressService.getDressesByName(name);
	}
	
	
	@ApiOperation(value="Update the Dress Obj",notes="Input is dress obj and return dress obj with id")
	@ApiResponses(value= {@ApiResponse(code=200,message = "dress obj Updated")})
	@PutMapping(value="/{id}",consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }, produces = { MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE })
	@PreAuthorize("hasRole('MERCHANT')")
	public ResponseEntity<ResponseStructure<Dress>> updateDress(@PathVariable("id") int id,@RequestPart("dress") String jsondress,
			@RequestPart("file") MultipartFile file) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		Dress dress = mapper.readValue(jsondress, Dress.class);
		dress.setMerchant(merchantDao.getMerchantById(id));
		dress.setImage(file.getBytes());
		return dressService.updateDress(dress);
	}
	
	

}
