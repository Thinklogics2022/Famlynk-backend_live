package com.famlynk.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.famlynk.model.FamilyMemberMapping;

@Repository
public interface IFamilyMemberMappingRepository extends MongoRepository<FamilyMemberMapping, String> {

	public List<FamilyMemberMapping> findByPrimaryMemberIdAndUserIdAndFamilyLevel(String primaryMemberId, String userId,
			String familyLevel);

	public void deleteByPrimaryMemberIdOrSecondaryMemberIdAndUserId(String primaryMemberId, String secondaryMemberId,
			String userId);

	public void deleteByPrimaryMemberIdAndUserId(String primaryMemberId, String userId);

	public void deleteBySecondaryMemberIdAndUserId(String secondaryMemberId, String userId);

	public List<FamilyMemberMapping> findBySecondaryMemberId(String secondaryMemberId);
}
