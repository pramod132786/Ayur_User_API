package com.anarghya.aayurvedaappuser.entity;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "counter_ordered_details")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "orderedId")
public class OrderDetails {

	@Id
	@GeneratedValue(generator = "ordered-id-generator")
	@GenericGenerator(name = "ordered-id-generator", strategy = "com.anarghya.aayurvedaappuser.generator.OrderedIdGenerator")
	private String orderedId;

	private LocalDate orderedDate;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JsonBackReference
	private UserEntity users;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private CounterSaleOrderedCustomerDetailsEnity customer;

	@OneToMany(mappedBy = "orders", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<MedicineModuleEntity> medicines;


	@PrePersist
	public void prePersist() {
		if (orderedId == null) {
			orderedId = generateOrderedId();
		}
	}

	public LocalDate getOrderedDate() {
		return orderedDate;
	}

	public void setOrderedDate(LocalDate orderedDate) {
		this.orderedDate = orderedDate;
	}

	private String generateOrderedId() {
		return null; 
	}

	public String getOrderedId() {
		return orderedId;
	}

	public void setOrderedId(String orderedId) {
		this.orderedId = orderedId;
	}

	public UserEntity getUsers() {
		return users;
	}

	public void setUsers(UserEntity users) {
		this.users = users;
	}

	public CounterSaleOrderedCustomerDetailsEnity getCustomer() {
		return customer;
	}

	public void setCustomer(CounterSaleOrderedCustomerDetailsEnity customer) {
		this.customer = customer;
	}

	public List<MedicineModuleEntity> getMedicines() {
		return medicines;
	}

	public void setMedicines(List<MedicineModuleEntity> medicines) {
		this.medicines = medicines;
	}

	@Override
	public int hashCode() {
		return Objects.hash(customer, medicines, orderedId, users);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderDetails other = (OrderDetails) obj;
		return Objects.equals(customer, other.customer) && Objects.equals(medicines, other.medicines)
				&& Objects.equals(orderedId, other.orderedId) && Objects.equals(users, other.users);
	}

}
