package com.famlynk.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.famlynk.model.FamilyMembers;

@Repository
public interface IFamilyMembersRepository extends MongoRepository<FamilyMembers, String> {

	public List<FamilyMembers> findByUserId(String userId);

	public List<FamilyMembers> findByUserIdAndRelationNotIn(String userId, String relation);

	public List<FamilyMembers> findByUniqueUserIDAndRelation(String userId, String relation);

	public List<FamilyMembers> findByUniqueUserID(String uniqueUserID);

	public void deleteByUniqueUserIDAndUserId(String uniqueUserID, String userId);

	public FamilyMembers findByUserIdAndUniqueUserID(String userId, String uniqueUserID);

	public boolean existsByEmail(String email);

	public boolean existsByMobileNo(String mobileNo);
	
	public List<FamilyMembers> findByUserIdAndIsRegisterUser(String userId, Boolean value);

}
