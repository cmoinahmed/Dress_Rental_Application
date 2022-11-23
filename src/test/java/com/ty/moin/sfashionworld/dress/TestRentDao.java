package com.ty.moin.sfashionworld.dress;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.ty.moin.sfashionworld.dao.RentDao;
import com.ty.moin.sfashionworld.dto.Customer;
import com.ty.moin.sfashionworld.dto.Rent;
import com.ty.moin.sfashionworld.repository.RentRepository;

@SpringBootTest
public class TestRentDao {
	@MockBean
	RentRepository rentRepository;

	@Autowired
	RentDao rentDao;

	Optional<Rent> optional;

	@BeforeEach
	public void set() {
		optional = Optional
				.of(new Rent(1, LocalDate.parse("2022-09-25"), LocalDate.parse("2022-09-25"), 5000, null, null,null));
	}

	@Test
	public void testSaveRent() {
		when(rentRepository.save(optional.get())).thenReturn(optional.get());
		assertEquals(5000, rentDao.saveRent(optional.get()).getTotalRentPrice());
	}

	@Test
	public void testGetRentById() {
		when(rentRepository.findById(1)).thenReturn(optional);
		assertEquals(optional.get(), rentDao.getRentById(1));
	}

	@Test
	public void testGetAllRents() {
		List<Rent> rents = new ArrayList<>();
		rents.add(optional.get());
		rents.add(optional.get());

		when(rentRepository.findAll()).thenReturn(rents);
		assertEquals(rents, rentDao.getAllRents());
	}

	@Test
	public void testFindRentByCustomer() {
		Customer customer = new Customer();
		List<Rent> rents = new ArrayList<>();
		rents.add(optional.get());
		rents.add(optional.get());

		when(rentRepository.findByCustomer(customer)).thenReturn(rents);
		assertEquals(rents, rentDao.findRentByCustomer(customer));
	}

	@Test
	public void testFindByRentDate() {
		List<Rent> rents = new ArrayList<>();
		rents.add(optional.get());
		rents.add(optional.get());
		when(rentRepository.findByRentDate(LocalDate.parse("2022-09-25"))).thenReturn(rents);
		assertEquals(rents, rentDao.findByRentDate(LocalDate.parse("2022-09-25")));
	}
}
