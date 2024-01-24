package com.anarghya.aayurvedaappuser.entity;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class UserResponse {
	
	public UserEntity getUser() {
		return user;
	}
	public void setUser(UserEntity user) {
		this.user = user;
	}
	public List<MedicineModuleEntity> getMedicine() {
		return medicine;
	}
	public void setMedicine(List<MedicineModuleEntity> medicine) {
		this.medicine = medicine;
	}
	private UserEntity user;
	private List<MedicineModuleEntity> medicine;

}
