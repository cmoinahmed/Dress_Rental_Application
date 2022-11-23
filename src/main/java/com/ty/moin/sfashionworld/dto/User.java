package com.ty.moin.sfashionworld.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(value = "AutoGenerated")
	private int id;

	@NotBlank(message = "please enter the name")
	@ApiModelProperty(required = true)
	private String userName;
	

	public User(int id,
			@NotBlank(message = "Please Enter the email") @Pattern(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$", message = "Enter a Valid Email") String email,
			@NotBlank String password,@NotNull(message = "please Enter the phone") long phone,  String role, @NotBlank(message = "please enter the name") String userName) {
		super();
		this.id = id;
		this.userName = userName;
		this.email = email;
		this.phone = phone;
		this.password = password;
		this.role = role;
	}

	@Column(unique = true)
	@NotBlank(message = "Please Enter the email")
	@Pattern(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$", message = "Enter a Valid Email")
	@ApiModelProperty(required = true)
	private String email;

	@Column(unique = true)
	@NotNull(message = "please Enter the phone")
	@ApiModelProperty(required = true)
	private long phone;

	@NotBlank
	@ApiModelProperty(required = true)
	private String password;
	
	@ApiModelProperty(required = true)
	@JsonIgnore
	private String role;

}
