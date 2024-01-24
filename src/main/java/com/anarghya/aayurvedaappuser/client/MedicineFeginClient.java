package com.anarghya.aayurvedaappuser.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.anarghya.aayurvedaappuser.entity.MedicineModuleEntity;

@FeignClient(value = "medicineFegin", url="http://localhost:2024/api")
public interface MedicineFeginClient {
	
////	@PostMapping("/medicines/create/{userId}")
//	@PutMapping("/medicines/update/{userId}")
//	public ResponseEntity<MedicineModuleEntity> upsertMedicine(@PathVariable Long userId,@RequestBody MedicineModuleEntity medicine);
//	

	@PostMapping("/medicines/create/{userId}")
	public ResponseEntity<MedicineModuleEntity> createMedicine(@PathVariable Long userId,@RequestBody MedicineModuleEntity medicine);
	
	@PutMapping("/medicines/update/{userId}")
	public ResponseEntity<MedicineModuleEntity> updateMedicineByUser(@PathVariable Long userId,@RequestBody MedicineModuleEntity medicine);
	
	
	@GetMapping("/medicines/user/{userId}")
	public ResponseEntity<List<MedicineModuleEntity>>  viewMedicineByUser(@PathVariable Long userId);

	@DeleteMapping("/medicines/delete/{userId}/{id}")
	public ResponseEntity<String>  deleteMedicineByUser(@PathVariable Long userId,@PathVariable Long id);
	
	@GetMapping("/medicines")
	public List<MedicineModuleEntity> getAllMedicines();
	
	@GetMapping("/medicines/medicine/{userId}/{id}")
	public ResponseEntity<MedicineModuleEntity> getUserByIdAndMedicineId(@PathVariable Long userId,@PathVariable Long id);
	
	@PostMapping("/medicines/quantity/{id}/{quantity}")
	public ResponseEntity<MedicineModuleEntity>  decreaseQuantity(@PathVariable Long id,@PathVariable int quantity);

}
