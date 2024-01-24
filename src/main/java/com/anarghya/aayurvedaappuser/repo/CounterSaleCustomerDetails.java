package com.anarghya.aayurvedaappuser.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anarghya.aayurvedaappuser.entity.CounterSaleOrderedCustomerDetailsEnity;


@Repository
public interface CounterSaleCustomerDetails  extends JpaRepository<CounterSaleOrderedCustomerDetailsEnity, Long>{

	Optional<CounterSaleOrderedCustomerDetailsEnity> findByEmailAndMobileNo(String email, Long mobileNo);
	CounterSaleOrderedCustomerDetailsEnity findByEmail(String email);
}
