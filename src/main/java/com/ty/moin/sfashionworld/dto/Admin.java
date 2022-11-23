package com.ty.moin.sfashionworld.dto;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
public class Admin extends User{

	{
		super.setRole("ROLE_ADMIN");
	}
}
