package com.ty.moin.sfashionworld.customer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.ty.moin.sfashionworld.dao.CustomerDao;
import com.ty.moin.sfashionworld.dto.Customer;
import com.ty.moin.sfashionworld.repository.CustomerRepository;

@SpringBootTest
public class TestCustomerDao {

	@MockBean
	CustomerRepository customerRepository;

	@Autowired
	CustomerDao customerDao;

	 static Optional<Customer> optional;

	@BeforeAll
	public static void set() {
		Customer customer = new Customer();
		customer.setId(0);
		customer.setUserName("Riya");
		customer.setEmail("riya@gmail.com");
		customer.setPhone(98765432134l);
		customer.setPassword("riya123");
		customer.setRents(null);

		optional = Optional.of(customer);
	}

	@Test
	public void testSaveCustomer() {
		Customer customer = optional.get();
		when(customerRepository.save(customer)).thenReturn(customer);
		assertEquals(customer, customerDao.saveCustomer(customer));

	}

	@Test
	public void testFindCustomerById() {
		when(customerRepository.findById(0)).thenReturn(optional);
		assertEquals(optional.get(), customerDao.findCustomerById(0));
	}

	@Test
	public void testGetAllCustomers() {
		List<Customer> customers = new ArrayList<>();
		customers.add(optional.get());
		customers.add(optional.get());

		when(customerRepository.findAll(30)).thenReturn(customers);
		assertEquals(customers, customerDao.getAllCustomers(30));
	}

	@Test
	public void testFindCustomerByEmail() {
		when(customerRepository.findByEmail("riya@gmail.com")).thenReturn(optional);
		assertEquals(optional.get(), customerDao.findCustomerByEmail("riya@gmail.com"));
	}

	@Test
	public void testFindCustomerByPhone() {
		when(customerRepository.findByPhone(98765432134l)).thenReturn(optional);
		assertEquals(optional.get(), customerDao.findCustomerByPhone(98765432134l));

	}
}
