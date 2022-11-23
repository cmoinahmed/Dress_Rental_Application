package com.ty.moin.sfashionworld.dress;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.ty.moin.sfashionworld.dao.DressDao;
import com.ty.moin.sfashionworld.dto.Dress;
import com.ty.moin.sfashionworld.repository.DressRepository;

@SpringBootTest
public class TestDressDao {

//	DressRepository dressRepository= mock(DressRepository.class);

	@MockBean
	DressRepository dressRepository;

	@Autowired
	DressDao dressDao;

	 static Optional<Dress> optional;

	@BeforeAll
	public static void set() {
		Dress dress=new Dress(1, "langaha", "xxl", 30000, 5000, "polo", 10000, null,2,null);
		optional = Optional.of(dress);
	}

	@Test
	public void testSaveDress() {
		Dress dress=optional.get();
		when(dressRepository.save(dress)).thenReturn(dress);
		assertEquals(dress, dressDao.saveDress(dress));
	}
	
	@Test
	public void testGetDressById() {
		when(dressRepository.findById(1)).thenReturn(optional);
		assertEquals(optional.get(), dressDao.getDressById(1));
	}
	
	@Test
	public void testGetAllDresses() {
		List<Dress> dresses=new ArrayList<>();
		dresses.add(optional.get());
		dresses.add(optional.get());
		
		when(dressRepository.findAll()).thenReturn(dresses);
		assertEquals(dresses,dressDao.getAllDresses());
	}
	
	@Test
	public void testGetDressByName() {
		
		List<Dress> dresses=new ArrayList<>();
		dresses.add(optional.get());
		dresses.add(optional.get());
		
		when(dressRepository.findByName("langaha")).thenReturn(dresses);
		assertEquals(dresses, dressDao.getDressByName("langaha"));
	}
	
	@Test
	public void testGetDressBySize() {
		List<Dress> dresses=new ArrayList<>();
		dresses.add(optional.get());
		dresses.add(optional.get());
		
		when(dressRepository.findBySize("xxl")).thenReturn(dresses);
		assertEquals(dresses, dressDao.getDressesBySize("xxl"));
	}
	
	@Test
	public void testGetDressByBrandName() {
		List<Dress> dresses=new ArrayList<>();
		dresses.add(optional.get());
		dresses.add(optional.get());
		Dress dress=new Dress(2, "chudidhar", "xl", 20000, 4000, "buffalo", 1000, null,2,null);
		dresses.add(dress);
		
		List<Dress> dresses2=new ArrayList<>();
		for(Dress dress2:dresses) {
			if(dress2.getBrandName().equalsIgnoreCase("polo")) {
				dresses2.add(dress);
			}
		}
		
		when(dressRepository.findByBrandName("polo")).thenReturn(dresses2);
		assertEquals(dresses2, dressDao.getDressesByBrandName("polo"));
		
		System.out.println(dresses);
	}
	
	@Test
	public void testGetDressByPriceRange() {
		Dress dress=new Dress(2, "chudidhar", "xl", 20000, 4000, "buffalo", 10000, null,2,null);
		List<Dress> dresses=new ArrayList<>();
		dresses.add(dress);
		
		when(dressRepository.findByRentPrice(4000, 4999)).thenReturn(dresses);
		assertEquals(dresses, dressDao.getDressesByPriceRange(4000, 4999));
		
		System.out.println(dresses);
	}

}
