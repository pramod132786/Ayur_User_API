package com.anarghya.aayurvedaappuser.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.anarghya.aayurvedaappuser.entity.UserEntity;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>{

	public Optional<UserEntity> findByUserEmailAndPazzwd(String userEmail, String pazzwd);
	
//	public Optional<UserEntity>  findByPazzwd(String pazzwd);
	
	public Optional<UserEntity> findByUserEmail(String userEmail);
	
	 @Query("SELECT u.orderedCount FROM UserEntity u WHERE u.userId = :userId")
	    Long getOrderedCountByUserId(@Param("userId") Long userId);
	
}
