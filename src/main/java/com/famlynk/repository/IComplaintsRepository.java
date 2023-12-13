package com.famlynk.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.famlynk.model.Complaint;

public interface IComplaintsRepository extends MongoRepository<Complaint,String> {

	List<Complaint> findByType(String string);

	long countByType(String type);

}
