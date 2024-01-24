package com.anarghya.aayurvedaappuser.repo;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anarghya.aayurvedaappuser.entity.OrderDetails;

public interface OrderDetailsRepo extends JpaRepository<OrderDetails, Serializable>{

}
