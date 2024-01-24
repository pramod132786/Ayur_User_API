package com.anarghya.aayurvedaappuser.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anarghya.aayurvedaappuser.entity.CounterSaleOrderedCustomerDetailsEnity;
import com.anarghya.aayurvedaappuser.entity.LoginForm;
import com.anarghya.aayurvedaappuser.entity.MedicineModuleEntity;
import com.anarghya.aayurvedaappuser.entity.OrderDetails;
import com.anarghya.aayurvedaappuser.entity.UserEntity;
import com.anarghya.aayurvedaappuser.entity.UserResponse;
import com.anarghya.aayurvedaappuser.exception.UserNotFoundException;
import com.anarghya.aayurvedaappuser.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1")
public class UserController {
	@Autowired
	private UserService userService;

	@Autowired
	private ObjectMapper objectMapper;

	@SuppressWarnings("unlikely-arg-type")
	@PostMapping("/user/login")
	public ResponseEntity<UserEntity> login(@RequestBody LoginForm loginForm) throws UserNotFoundException {
		UserEntity userLogin = userService.login(loginForm);
		try { 

			if (userLogin.equals("You have entered an incorrect Credentails.")) {
				return new ResponseEntity<>(userLogin, HttpStatus.UNAUTHORIZED);

			} else {
				return new ResponseEntity<>(userLogin, HttpStatus.OK);
			}
		} catch (UserNotFoundException e) {
			return new ResponseEntity<>(userLogin, HttpStatus.FORBIDDEN);

		}

	}

	@SuppressWarnings("unlikely-arg-type")
	@PostMapping("/user/unlock/{userId}/{status}")
	public ResponseEntity<UserEntity> unlockAccount(@PathVariable Long userId, @PathVariable String status) {
		if (userId != null || !"".equals(userId) && status != null || !"".equals(status)) {
			return new ResponseEntity<>(userService.unlockAccount(userId, status), HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
	}

	@SuppressWarnings("unlikely-arg-type")
	@PostMapping("/user/createUser")
	public ResponseEntity<UserEntity> createUser(@RequestBody UserEntity user) {
		if (user != null && !user.equals("")) {
			UserEntity userCreated = userService.createUser(user);
			return new ResponseEntity<UserEntity>(userCreated, HttpStatus.CREATED);
		}
		return new ResponseEntity<UserEntity>(HttpStatus.NO_CONTENT);
	}

	@PostMapping("/user/create/{userId}")
	public ResponseEntity<UserResponse> createMedicine(@PathVariable Long userId,
			@RequestBody MedicineModuleEntity medicine) throws UserNotFoundException {
		if (userId != null && medicine != null) {
			UserResponse upsertMedicine = userService.upsertMedicine(userId, medicine);
			return new ResponseEntity<>(upsertMedicine, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
		}
	}

	@GetMapping("/user/users")
	public ResponseEntity<List<UserEntity>> viewAllUsers() {
		List<UserEntity> allUsers = userService.viewAllUsers();
		if (allUsers != null) {
			return new ResponseEntity<>(allUsers, HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
	}

	@PutMapping("/user/update/{userId}")
	public ResponseEntity<UserResponse> updateMedicine(@PathVariable Long userId,
			@RequestBody MedicineModuleEntity medicine) throws UserNotFoundException {
		if (userId != null && medicine != null) {
			UserResponse upsertMedicine = userService.upsertMedicine(userId, medicine);
			return new ResponseEntity<>(upsertMedicine, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
		}
	}

	@SuppressWarnings("unlikely-arg-type")
	@DeleteMapping("/user/delete/{userId}/{medicineId}")
	public ResponseEntity<UserEntity> deleteMedicine(@PathVariable Long userId, @PathVariable Long medicineId) {
		if (userId != null || !"".equals(userId) && medicineId != null || !"".equals(medicineId)) {
			userService.deleteMedicine(userId, medicineId);

		} else {

		}

		return null;

	}

	@GetMapping("/user/medicines/{userId}")
	public ResponseEntity<UserResponse> viewMedicines(@PathVariable Long userId) {
		if (userId != null) {
			UserResponse viewMedicines = userService.viewMedicines(userId);
			return new ResponseEntity<>(viewMedicines, HttpStatus.OK);

		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/user/medicines")
	public ResponseEntity<List<MedicineModuleEntity>> viewAllMedicines() {
		List<MedicineModuleEntity> allMedicines = userService.viewAllMedicines();
		if (allMedicines.isEmpty()) {
			return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(allMedicines, HttpStatus.OK);
		}
	}

	public ResponseEntity<UserEntity> canelOrder(Long userId) {
		return null;

	}

	@PostMapping("/user/sellmedicine/{userId}")
	public ResponseEntity<OrderDetails> sellMedicine(@PathVariable Long userId,
			@RequestBody Map<String, Object> requestBody) {

		@SuppressWarnings("unchecked")
		List<Map<String, Object>> medicinesData = (List<Map<String, Object>>) requestBody.get("medicines");
		List<MedicineModuleEntity> medicines = new ArrayList<>();

		for (Map<String, Object> medicineData : medicinesData) {
			MedicineModuleEntity medicine = objectMapper.convertValue(medicineData, MedicineModuleEntity.class);
			medicines.add(medicine);
		}

		CounterSaleOrderedCustomerDetailsEnity customer = objectMapper.convertValue(requestBody.get("customer"),
				CounterSaleOrderedCustomerDetailsEnity.class);

		OrderDetails orderMedicine = userService.orderMedicine(userId, medicines, customer);
		return new ResponseEntity<>(orderMedicine, HttpStatus.OK);

	}

	@PostMapping("/user/notifyDelete/{userId}/{medicineId}")
	public ResponseEntity<String> notifyDelete(@PathVariable Long userId, @PathVariable Long medicineId,
			@RequestBody String message) {
		String notifyDelete = userService.notifyDelete(userId, medicineId, message);
		return new ResponseEntity<>(notifyDelete, HttpStatus.OK);
	}

	@GetMapping("/user/forgotPazwd/{userEmail}")
	public ResponseEntity<Boolean> forgotPazwd(@PathVariable String userEmail) {
		boolean forgotPazwd = userService.forgotPazwd(userEmail);
		if (forgotPazwd) {
			return new ResponseEntity<>(forgotPazwd, HttpStatus.OK);
		}
		return new ResponseEntity<>(forgotPazwd, HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	@GetMapping("/user/{userId}/orderedCount")
	public Long getOrderedCountForUser(@PathVariable Long userId) {
		return userService.getOrderedCountForUser(userId);
	}
	
	@GetMapping("/user/viewAllOrder/{userId}")
	public ResponseEntity<UserEntity>  viewAllOrder(@PathVariable Long userId) {
		UserEntity viewAllOrder = userService.viewAllOrder(userId);
		return new ResponseEntity<>(viewAllOrder,HttpStatus.OK);
	}

}
