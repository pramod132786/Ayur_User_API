package com.anarghya.aayurvedaappuser.service;

import java.util.List;

import com.anarghya.aayurvedaappuser.entity.CounterSaleOrderedCustomerDetailsEnity;
import com.anarghya.aayurvedaappuser.entity.LoginForm;
import com.anarghya.aayurvedaappuser.entity.MedicineModuleEntity;
import com.anarghya.aayurvedaappuser.entity.OrderDetails;
import com.anarghya.aayurvedaappuser.entity.UserEntity;
import com.anarghya.aayurvedaappuser.entity.UserResponse;
import com.anarghya.aayurvedaappuser.exception.UserNotFoundException;

public interface UserService {
	
	public UserEntity login(LoginForm loginForm) throws UserNotFoundException;
	
	public UserEntity createUser(UserEntity user);
	
	public UserResponse upsertMedicine(Long userId ,MedicineModuleEntity medicine) throws UserNotFoundException;
	
	public UserEntity deleteMedicine(Long userId, Long medicineId);
	
	public UserResponse viewMedicines(Long userId);
	
	public List<MedicineModuleEntity> viewAllMedicines();
	
	public UserEntity canelOrder(Long userId);
	
	public OrderDetails orderMedicine(Long userId, List<MedicineModuleEntity> medicine, CounterSaleOrderedCustomerDetailsEnity customer);
	
	public UserEntity unlockAccount(Long userId, String status);
	
	public List<UserEntity> viewAllUsers();
	
	public String notifyDelete(Long userId, Long medicineId,String message);
	
	public boolean forgotPazwd(String userEmail);
	
	public UserEntity viewAllOrder(Long userId);

	public Long getOrderedCountForUser(Long userId);
	
	

}
