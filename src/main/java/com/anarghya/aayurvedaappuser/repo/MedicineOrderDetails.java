package com.anarghya.aayurvedaappuser.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anarghya.aayurvedaappuser.entity.MedicineModuleEntity;



@Repository
public interface MedicineOrderDetails extends JpaRepository<MedicineModuleEntity, Long> {

//	Optional<MedicineModuleEntity> findByCustomerDetailsAndId(CounterSaleOrderedCustomerDetailsEnity customer, Long id);
	}
