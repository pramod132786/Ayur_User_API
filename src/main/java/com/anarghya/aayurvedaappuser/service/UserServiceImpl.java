package com.anarghya.aayurvedaappuser.service;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.anarghya.aayurvedaappuser.client.MedicineFeginClient;
import com.anarghya.aayurvedaappuser.entity.CounterSaleOrderedCustomerDetailsEnity;
import com.anarghya.aayurvedaappuser.entity.LoginForm;
import com.anarghya.aayurvedaappuser.entity.MedicineModuleEntity;
import com.anarghya.aayurvedaappuser.entity.OrderDetails;
import com.anarghya.aayurvedaappuser.entity.UserEntity;
import com.anarghya.aayurvedaappuser.entity.UserResponse;
import com.anarghya.aayurvedaappuser.exception.AccountLockedException;
import com.anarghya.aayurvedaappuser.exception.UserNotFoundException;
import com.anarghya.aayurvedaappuser.repo.CounterSaleCustomerDetails;
import com.anarghya.aayurvedaappuser.repo.MedicineOrderDetails;
import com.anarghya.aayurvedaappuser.repo.OrderDetailsRepo;
import com.anarghya.aayurvedaappuser.repo.UserRepository;
import com.anarghya.aayurvedaappuser.utils.EmailUtils;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private MedicineFeginClient medicineFeginClient;

	@Autowired
	private EmailUtils emailUtils;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private CounterSaleCustomerDetails customerRepo;

	@Autowired
	private MedicineOrderDetails medicineRepo;

	@Autowired
	private OrderDetailsRepo orderRepo;

	@Override
	public UserEntity login(LoginForm loginForm) throws UserNotFoundException {
		Optional<UserEntity> byUserEmail = userRepo.findByUserEmail(loginForm.getUserEmail());
		if (byUserEmail.isPresent()) {
			UserEntity userEntity = byUserEmail.get();
			String pazwd = loginForm.getPazzwd();

			Encoder encoder = Base64.getEncoder();
			String getpassword = encoder.encodeToString(pazwd.getBytes());

			String encodedPassword = byUserEmail.get().getPazzwd();

			if (userEntity.getAccountStatus().contains("Deactive")) {
				throw new AccountLockedException("Your account is deactivated . Please contact support Team.");

			}

			if (userEntity.getFailedLoginAttempts() >= 4) {
				userEntity.setAccountStatus("Locked");
				userRepo.save(userEntity);
				throw new AccountLockedException("Account is locked. Please contact support Team.");
			}

			if (getpassword.equals(encodedPassword)) {

				userEntity.setFailedLoginAttempts(0);
				userRepo.save(userEntity);
				return userEntity;

			} else {

				userEntity.setFailedLoginAttempts(userEntity.getFailedLoginAttempts() + 1);
				userRepo.save(userEntity);
				throw new UserNotFoundException("You have entered an incorrect Credentails.");
			}
		} else {
			throw new UserNotFoundException("Invalid Credentails");
		}

	}

	@Override
	public UserResponse upsertMedicine(Long userId, MedicineModuleEntity medicine) throws UserNotFoundException {
		Optional<UserEntity> user = userRepo.findById(userId);
		UserResponse userResponse = new UserResponse();
		ArrayList<MedicineModuleEntity> medicines = new ArrayList<>();

		if (medicine.getId() != null) {
			if (user.isPresent()) {
				ResponseEntity<MedicineModuleEntity> medicineModuleEntity = medicineFeginClient
						.updateMedicineByUser(userId, medicine);
				if (medicineModuleEntity.getBody() != null) {
					userResponse.setUser(user.get());
					medicines.add(medicineModuleEntity.getBody());
					userResponse.setMedicine(medicines);
					return userResponse;

				} else {
					throw new UserNotFoundException("Medicine Not Updated");
				}
			} else {
				throw new UserNotFoundException("User Not Found");
			}
		} else {
			if (user.isPresent()) {
				ResponseEntity<MedicineModuleEntity> medicineModuleEntity = medicineFeginClient.createMedicine(userId,
						medicine);
				if (medicineModuleEntity.getBody() != null) {
					userResponse.setUser(user.get());
					medicines.add(medicineModuleEntity.getBody());
					userResponse.setMedicine(medicines);
					return userResponse;
				} else {
					throw new UserNotFoundException("Medicine Not Added.");
				}

			} else {
				throw new UserNotFoundException("User Not Found");
			}

		}

	}

	@Override
	public UserEntity deleteMedicine(Long userId, Long medicineId) {
		Optional<UserEntity> user = userRepo.findById(userId);
		if (user.isPresent()) {
			return null;
		}

		return null;
	}

	@Override
	public UserResponse viewMedicines(Long userId) {
		UserResponse userResponse = new UserResponse();
		ArrayList<MedicineModuleEntity> medicines = new ArrayList<>();
		Optional<UserEntity> user = userRepo.findById(userId);

		if (user.isPresent()) {
			ResponseEntity<List<MedicineModuleEntity>> viewMedicineByUser = medicineFeginClient
					.viewMedicineByUser(userId);
			userResponse.setUser(user.get());
			viewMedicineByUser.getBody().forEach(e -> medicines.add(e));
			userResponse.setMedicine(medicines);
			return userResponse;
		}
		throw new UserNotFoundException("User Not Found");
	}

	@Override
	public UserEntity canelOrder(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unlikely-arg-type")
	@Override
	public UserEntity createUser(UserEntity user) {
		if (user != null && !"".equals(user)) {
			user.setAccountStatus("Active");
			String pazwd = user.getPazzwd();
			Encoder encoder = Base64.getEncoder();
			String encodepassword = encoder.encodeToString(pazwd.getBytes());
			user.setPazzwd(encodepassword);
			UserEntity userEntity = userRepo.save(user);
			String subject = "Your Account Details to Login to Application.";
			StringBuffer body = new StringBuffer();
			body.append("<h4> Hello, " + userEntity.getUserName() + " Your Account Details: </h4><br>");
			body.append("Use below credentails to login into applicaiton.<br>");
			body.append("<h4>Your Email:- " + userEntity.getUserEmail() + ".</h4>");
			body.append("<h4>Your Password:- " + pazwd + ".</h4><br>");
			body.append("<br>");
			body.append("Thank you..!!");

			emailUtils.sendMail(userEntity.getUserEmail(), subject, body.toString());
			return userEntity;
		} else {
			throw new UserNotFoundException("Please provide User Details.. Server Error.");
		}

	}

	@Override
	public List<MedicineModuleEntity> viewAllMedicines() {
		List<MedicineModuleEntity> medicines = medicineFeginClient.getAllMedicines();
		if (medicines.isEmpty()) {
			throw new UserNotFoundException("No Medicines Available");
		} else {
			return medicines;
		}

	}

	@Override
	public UserEntity unlockAccount(Long userId, String status) {
		Optional<UserEntity> findById = userRepo.findById(userId);
		if (findById.isEmpty()) {
			return findById.orElseThrow(() -> new UserNotFoundException("User Not Found"));
		} else {
			UserEntity userEntity = findById.get();
			userEntity.setAccountStatus(status);
			userEntity.setFailedLoginAttempts(0);
			userRepo.save(userEntity);
			return userEntity;
		}
	}

	@Override
	public List<UserEntity> viewAllUsers() {
		List<UserEntity> users = userRepo.findAll();
		if (users.isEmpty()) {
			return null;
		}

		return users;
	}

	@Override
	public String notifyDelete(Long userId, Long medicineId, String message) {
		Optional<UserEntity> findById = userRepo.findById(userId);
		if (findById.isPresent()) {
			ResponseEntity<MedicineModuleEntity> userByIdAndMedicineId = medicineFeginClient
					.getUserByIdAndMedicineId(userId, medicineId);
			System.out.println("Medicine name" + userByIdAndMedicineId.getBody().getName());
			if (userByIdAndMedicineId.getStatusCodeValue() == 404) {
				throw new UserNotFoundException("Medicine was not found");
			}
			MedicineModuleEntity medicineModuleEntity = userByIdAndMedicineId.getBody();
			UserEntity userEntity = findById.get();

			String body = "<h4> I am " + userEntity.getUserName() + ".</h4> <br/>"
					+ " <h6>   I want to Delete Medicine called " + medicineModuleEntity.getName()
					+ " <br/> Approve my Request. <br/> i.e, Issue of Deletion of Medicine : " + message.toString()
					+ "</h6>";
			boolean sendMail = emailUtils.sendMail(userEntity.getUserEmail(), "Regarding Medicine Delete Notification",
					body);
			if (sendMail) {
				return "Your Request of Medicine Deletion is Sent to Admin team.";
			} else {
				return "Your Request of Medicine Deletion is not Sent.";
			}
		} else {
			throw new UserNotFoundException("User not Found");
		}

	}

	@Override
	public boolean forgotPazwd(String userEmail) {
		Optional<UserEntity> byUserEmail = userRepo.findByUserEmail(userEmail);
		if (byUserEmail.isEmpty()) {
			throw new UserNotFoundException("You have entered an incorrect Email.");
		}
		UserEntity userEntity = byUserEmail.get();
		String pazwd = userEntity.getPazzwd();
		Decoder decoder = Base64.getDecoder();
		byte[] decode = decoder.decode(pazwd.getBytes());
		String decodedPassword = new String(decode);
		String subject = "Your Account Password :";
		StringBuffer body = new StringBuffer();
		body.append("<h4>Hi, " + userEntity.getUserName() + " Your Account Password:</h4> ");
		body.append("Your Password:- " + decodedPassword + "<br>");
		body.append("Your Acoount Status:- " + userEntity.getAccountStatus() + "<br>");
		body.append("Thank you");
		boolean sendMail = emailUtils.sendMail(userEntity.getUserEmail(), subject, body.toString());
		return sendMail;
	}

	@Transactional
	public OrderDetails orderMedicine(Long userId, List<MedicineModuleEntity> medicines,
			CounterSaleOrderedCustomerDetailsEnity customer) {

		Optional<UserEntity> userOptional = userRepo.findById(userId);
		if (userOptional.isEmpty()) {
			throw new UserNotFoundException("User not found with ID: " + userId);
		}

		UserEntity userEntity = userOptional.get();

		Optional<CounterSaleOrderedCustomerDetailsEnity> existingCustomerOptional = customerRepo
				.findByEmailAndMobileNo(customer.getEmail(), customer.getMobileNo());

		CounterSaleOrderedCustomerDetailsEnity customerDetails;

		if (existingCustomerOptional.isPresent()) {
			customerDetails = existingCustomerOptional.get();
		} else {
			customerDetails = new CounterSaleOrderedCustomerDetailsEnity();
			customerDetails.setName(customer.getName());
			customerDetails.setEmail(customer.getEmail());
			customerDetails.setMobileNo(customer.getMobileNo());
			customerDetails.setCreatedBy(userEntity);
		}

		customerDetails = customerRepo.save(customerDetails);

		OrderDetails orderDetails = new OrderDetails();
		List<MedicineModuleEntity> newMedicines = new ArrayList<>();

		for (MedicineModuleEntity medicine : medicines) {
			MedicineModuleEntity newMedicine = new MedicineModuleEntity();
			newMedicine.setName(medicine.getName());
			newMedicine.setMedicineId(medicine.getId());
			newMedicine.setCompany(medicine.getCompany());
			newMedicine.setOrderdQuantity(medicine.getOrderdQuantity());
			newMedicine.setCategory(medicine.getCategory());
			newMedicine.setBatchCode(medicine.getBatchCode());
			newMedicine.setCost(medicine.getCost());
			newMedicine.setDescription(medicine.getDescription());
			newMedicine.setExpiryDate(medicine.getExpiryDate());
			newMedicine.setMfdDate(medicine.getMfdDate());
			newMedicine.setFormula(medicine.getFormula());
			newMedicine.setOrders(orderDetails);

			medicineRepo.save(newMedicine);
			newMedicines.add(newMedicine);
		}

		orderDetails.setUsers(userEntity);
		orderDetails.setCustomer(customerDetails);
		orderDetails.setOrderedDate(LocalDate.now());
		orderDetails.setMedicines(newMedicines);

		orderDetails = orderRepo.save(orderDetails);
		orderDetails.getCustomer();
		orderDetails.getMedicines();
		orderDetails.getUsers();
		userEntity.getOrders().add(orderDetails);

		userEntity.setOrderedCount(userEntity.getOrderedCount() + 1);
		userRepo.save(userEntity);

		return orderDetails;
	}
	
	@Override
	 public Long getOrderedCountForUser(Long userId) {
		Optional<UserEntity> userOptional = userRepo.findById(userId);
		if (userOptional.isEmpty()) {
			throw new UserNotFoundException("User not found with ID: " + userId);
		}
	        return userRepo.getOrderedCountByUserId(userId);
	    }

	@Override
	public UserEntity viewAllOrder(Long userId) {

		Optional<UserEntity> userOptional = userRepo.findById(userId);
		if (userOptional.isEmpty()) {
			throw new UserNotFoundException("User not found with ID: " + userId);
		}

		UserEntity userEntity = userOptional.get();
//	    userEntity.getOrders();
//	    userEntity.getCustomerDetails();

		return userEntity;
	}

}
