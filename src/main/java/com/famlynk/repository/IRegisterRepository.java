package com.famlynk.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.famlynk.model.Register;

public interface IRegisterRepository extends MongoRepository<Register, String> {
	public Register findByEmail(String email);

	public Register findByUserId(String userId);

	@Query("{'enabled': true}")
	public List<Register> findAll();

	public boolean existsByEmail(String email);

	public Register findByOtp(String otp);

	public Register findByUniqueUserID(String uniqueUserID);

	public List<Register> findByUniqueUserIDNotIn(Set<String> familyMemberIds);

	public List<Register> findByRole(String role);
	

}
