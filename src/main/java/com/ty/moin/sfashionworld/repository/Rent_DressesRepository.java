package com.ty.moin.sfashionworld.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ty.moin.sfashionworld.dto.RentDress;

@Repository
public interface Rent_DressesRepository extends JpaRepository<RentDress, Integer>{

}
