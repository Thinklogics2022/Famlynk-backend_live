package com.famlynk.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.famlynk.model.SecondLevelRelation;

public interface ISecondLevelRelationRepository extends MongoRepository<SecondLevelRelation, String>{

	public List<SecondLevelRelation> findByFirstLevelRelation(String firstLevelRelation);
}
