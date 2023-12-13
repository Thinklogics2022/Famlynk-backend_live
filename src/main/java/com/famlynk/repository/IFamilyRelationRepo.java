package com.famlynk.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.famlynk.model.FamilyRelation;

@Repository
public interface IFamilyRelationRepo extends MongoRepository<FamilyRelation, String> {

	public List<FamilyRelation> findByPrimaryRelationAndSecondaryRelationAndGender(String primaryRelation,
			String secondaryRelation, String gender);

}