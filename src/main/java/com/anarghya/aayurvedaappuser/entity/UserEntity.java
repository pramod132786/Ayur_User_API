package com.anarghya.aayurvedaappuser.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "User_Module")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "userId")
public class UserEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long userId;

	@NotBlank(message = "Name must not be Blank")
	@NotEmpty(message = "Name must not be Empty")
	private String userName;
	@Column(unique = true)
	@NotBlank(message = "Email must not be Blank")
	@NotEmpty(message = "Email must not be Empty")
	private String userEmail;

	@Column(unique = true)
	@NotNull(message = "PhoneNumber must not be Null")
	private Long mobileNo;
	@Column(updatable = false)
	@CreationTimestamp
	private LocalDate userCreatedDate;
	@Column(insertable = false)
	@UpdateTimestamp
	private LocalDate userUpdatedDate;
	private int failedLoginAttempts;
	private String registeredNumber;
	private String address;
	
	
	private Long orderedCount;

	@OneToMany(mappedBy = "users", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JsonManagedReference
	private List<OrderDetails> orders;

	@OneToMany(mappedBy = "createdBy", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JsonIgnore
	private List<CounterSaleOrderedCustomerDetailsEnity> customerDetails;

	@Transient
	private OrderDetails order;

	public OrderDetails getOrder() {
		return order;
	}

	public void setOrder(OrderDetails order) {
		this.order = order;
	}

	public List<CounterSaleOrderedCustomerDetailsEnity> getOrderedCustomerDetails() {
		return customerDetails;
	}

	public void setOrderedCustomerDetails(List<CounterSaleOrderedCustomerDetailsEnity> customerDetails) {
		this.customerDetails = customerDetails;
	}

	public List<OrderDetails> getOrders() {
		return orders;
	}

	public void setOrders(List<OrderDetails> orders) {
		this.orders = orders;
	}

	public int getFailedLoginAttempts() {
		return failedLoginAttempts;
	}

	public void setFailedLoginAttempts(int failedLoginAttempts) {
		this.failedLoginAttempts = failedLoginAttempts;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public Long getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(Long mobileNo) {
		this.mobileNo = mobileNo;
	}

	public LocalDate getUserCreatedDate() {
		return userCreatedDate;
	}

	public void setUserCreatedDate(LocalDate userCreatedDate) {
		this.userCreatedDate = userCreatedDate;
	}

	public LocalDate getUserUpdatedDate() {
		return userUpdatedDate;
	}

	public void setUserUpdatedDate(LocalDate userUpdatedDate) {
		this.userUpdatedDate = userUpdatedDate;
	}

	public String getPazzwd() {
		return pazzwd;
	}

	public void setPazzwd(String pazzwd) {
		this.pazzwd = pazzwd;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}

	public String getRegisteredNumber() {
		return registeredNumber;
	}

	public void setRegisteredNumber(String registeredNumber) {
		this.registeredNumber = registeredNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<CounterSaleOrderedCustomerDetailsEnity> getCustomerDetails() {
		return customerDetails;
	}

	public void setCustomerDetails(List<CounterSaleOrderedCustomerDetailsEnity> customerDetails) {
		this.customerDetails = customerDetails;
	}
	

	public Long getOrderedCount() {
		return orderedCount;
	}

	public void setOrderedCount(Long orderedCount) {
		this.orderedCount = orderedCount;
	}


	@NotBlank(message = "Password must not be Blank")
	@NotEmpty(message = "Pasasword must not be Empty")
	private String pazzwd;
	@NotBlank(message = "Role must not be Blank")
	@NotEmpty(message = "Role must not be Empty")
	private String role;
	private String accountStatus;

}
