package com.famlynk.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.famlynk.model.ThirdLevelRelation;

public interface IThirdLevelRelationRepository extends MongoRepository<ThirdLevelRelation, String> {
	public List<ThirdLevelRelation> findBySecondLevelRelation(String secondLevelRelation);
}
