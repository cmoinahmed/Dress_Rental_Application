package com.ty.moin.sfashionworld.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ty.moin.sfashionworld.dto.Dress;

public interface DressRepository extends JpaRepository<Dress, Integer> {

	List<Dress> findBySize(String size);

	List<Dress> findByName(String name);

	List<Dress> findByBrandName(String brandName);

	@Query(value = "SELECT d FROM Dress d WHERE rentPrice BETWEEN :lowprice AND :highprice")
	List<Dress> findByRentPrice(@Param("lowprice") double lowPrice,@Param("highprice") double highPrice);

}
