 
package com.ty.moin.sfashionworld.dto;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer extends User{

	{
		super.setRole("ROLE_CUSTOMER");
	}
	

	private String address;

	@JsonIgnore
	@OneToMany(mappedBy = "customer")
	private List<Rent> rents;
	
	@JsonIgnore
	private int otp;

}
