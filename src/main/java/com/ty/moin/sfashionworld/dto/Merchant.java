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
public class Merchant extends User {
	
	{
		super.setRole("ROLE_MERCHENT");
	}
	
	@JsonIgnore
	private int otp;

	@OneToMany(mappedBy = "merchant")
	@JsonIgnore
	private List<Dress> dresses;

}
