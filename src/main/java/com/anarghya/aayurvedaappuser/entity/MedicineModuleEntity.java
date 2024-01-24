package com.anarghya.aayurvedaappuser.entity;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "counter_sale_medicines_details")
public class MedicineModuleEntity {

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getMedicineOrderedDate() {
		return medicineOrderedDate;
	}

	public void setMedicineOrderedDate(LocalDate medicineOrderedDate) {
		this.medicineOrderedDate = medicineOrderedDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMfdDate() {
		return mfdDate;
	}

	public void setMfdDate(String mfdDate) {
		this.mfdDate = mfdDate;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	

	public int getOrderdQuantity() {
		return orderdQuantity;
	}

	public void setOrderdQuantity(int orderdQuantity) {
		this.orderdQuantity = orderdQuantity;
	}

	public float getCost() {
		return cost;
	}

	public void setCost(float cost) {
		this.cost = cost;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}

	public String getBatchCode() {
		return batchCode;
	}

	public void setBatchCode(String batchCode) {
		this.batchCode = batchCode;
	}

	public Long getMedicineId() {
		return medicineId;
	}

	public void setMedicineId(Long medicineId) {
		this.medicineId = medicineId;
	}

	public OrderDetails getOrders() {
		return orders;
	}

	public void setOrders(OrderDetails orders) {
		this.orders = orders;
	}

	@Override
	public int hashCode() {
		return Objects.hash(batchCode, category, company, cost, description, expiryDate, formula, id, medicineId,
				medicineOrderedDate, mfdDate, name, orders, orderdQuantity);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MedicineModuleEntity other = (MedicineModuleEntity) obj;
		return Objects.equals(batchCode, other.batchCode) && Objects.equals(category, other.category)
				&& Objects.equals(company, other.company)
				&& Float.floatToIntBits(cost) == Float.floatToIntBits(other.cost)
				&& Objects.equals(description, other.description) && Objects.equals(expiryDate, other.expiryDate)
				&& Objects.equals(formula, other.formula) && Objects.equals(id, other.id)
				&& Objects.equals(medicineId, other.medicineId)
				&& Objects.equals(medicineOrderedDate, other.medicineOrderedDate)
				&& Objects.equals(mfdDate, other.mfdDate) && Objects.equals(name, other.name)
				&& Objects.equals(orders, other.orders) && orderdQuantity == other.orderdQuantity;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long medicineId;
	private String name;
	private String mfdDate;
	private String expiryDate;
	private int orderdQuantity;
	private float cost;
	private String company;

	private String category;
	private String description; 
	private String formula;
	private String batchCode;
	@CreationTimestamp
	@Column(updatable = false)
	private LocalDate medicineOrderedDate;

	@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JsonBackReference
	private OrderDetails orders;


}
