package com.ty.moin.sfashionworld.dto;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Rent {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(value = "Auto Generated")
	private int id;

	@NotNull
	@ApiModelProperty(required = true)
	private LocalDate rentDate;

	@NotNull
	@ApiModelProperty(required = true)
	private LocalDate returnDate;

	@NotNull
	private double totalRentPrice;

	@ManyToOne
	@JoinColumn
	@JsonIgnore
	private Customer customer;

	@OneToMany(mappedBy="rent",cascade = CascadeType.ALL)
	@JsonIgnore
	private List<RentDress> rentDresses;
	
	@Transient
	private List<Dress> dresses;

}
