package com.ty.moin.sfashionworld.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Dress {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(value = "Auto Generated")
	private int id;

	@ApiModelProperty(required = true)
	@NotBlank
	private String name;

	@ApiModelProperty(required = true)
	@NotBlank
	private String size;

	@ApiModelProperty(required = true)
	@NotNull
	private double mrpPrice;

	@ApiModelProperty(required = true)
	@NotNull
	private double rentPrice;

	@ApiModelProperty(required = true)
	@NotBlank
	private String brandName;

	@ApiModelProperty(required = true)
	@NotNull
	private double depositPrice;

	@Lob
	private byte[] image;
	
	@NotNull
	@ApiModelProperty(required = true)
	private int quantity;
	
	@ManyToOne
	@JoinColumn
	@JsonIgnore
	private Merchant merchant;
	

}
