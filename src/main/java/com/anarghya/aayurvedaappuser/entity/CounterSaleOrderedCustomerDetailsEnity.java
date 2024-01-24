package com.anarghya.aayurvedaappuser.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedBy;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "counter_sale_customer_Details")
public class CounterSaleOrderedCustomerDetailsEnity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long oderedCustomerId;
	@NotBlank(message = "Name must not be Blank")
	@NotEmpty(message = "Name must not be Empty")
	private String name;

	@Email(message = "Enter correct email")
	@NotBlank(message = "Email must not be Blank")
	@NotEmpty(message = "Email must not be Empty")
	private String email;

	@JsonFormat(pattern = "0-9")
	private Long mobileNo;

	@CreationTimestamp
	@Column(updatable = false)
	private LocalDate orderedDate;

	@CreatedBy
	@ManyToOne(fetch = FetchType.EAGER)
	private UserEntity createdBy;

	@OneToMany(mappedBy = "customer")
	private List<OrderDetails> orders;

	public Long getOderedCustomerId() {
		return oderedCustomerId;
	}

	public void setOderedCustomerId(Long oderedCustomerId) {
		this.oderedCustomerId = oderedCustomerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(Long mobileNo) {
		this.mobileNo = mobileNo;
	}

	public LocalDate getOrderedDate() {
		return orderedDate;
	}

	public void setOrderedDate(LocalDate orderedDate) {
		this.orderedDate = orderedDate;
	}

	public UserEntity getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(UserEntity createdBy) {
		this.createdBy = createdBy;
	}

}
