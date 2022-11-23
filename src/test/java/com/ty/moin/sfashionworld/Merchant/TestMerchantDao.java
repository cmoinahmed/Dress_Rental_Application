package com.ty.moin.sfashionworld.Merchant;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.ty.moin.sfashionworld.dao.MerchantDao;
import com.ty.moin.sfashionworld.dto.Merchant;
import com.ty.moin.sfashionworld.repository.MerchantRepository;

@SpringBootTest
public class TestMerchantDao {

	@MockBean
	private MerchantRepository merchantRepository;

	@Autowired
	private MerchantDao merchantDao;

	Optional<Merchant> optional;

	@BeforeEach
	public void set() {

		optional = Optional.of(new Merchant(1, null));
	}

	@Test
	public void saveAdminTest() {
		Merchant merchant = optional.get();
		when(merchantRepository.save(merchant)).thenReturn(merchant);
		assertEquals(merchant, merchantDao.saveMerchant(merchant));
	}

	@Test
	public void findAdminByPhoneTest() {
		Merchant merchant = optional.get();
		when(merchantRepository.findByPhone(7896541238l)).thenReturn(optional);
		assertEquals(merchant, merchantDao.getMerchantByPhone(7896541238l));
	}

	@Test
	public void findAdminByEmailTest() {
		Merchant merchant = optional.get();
		when(merchantRepository.findByEmail("javid123@gmail.com")).thenReturn(optional);
		assertEquals(merchant, merchantDao.getMerchantByEmail("javid123@gmail.com"));
	}

	@Test
	public void getAdminByIdTest() {
		Merchant merchant = optional.get();
		when(merchantRepository.findById(1)).thenReturn(optional);
		assertEquals(merchant, merchantDao.getMerchantById(1));
	}

	@Test
	public void getAllAdmin() {
		List<Merchant> list = new ArrayList<>();
		list.add(optional.get());
		when(merchantRepository.findAll()).thenReturn(list);
		assertEquals(list, merchantDao.getAllMerchant());
	}
}
