package com.famlynk.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.famlynk.model.Notify;

@Repository
public interface INotifyRepository extends MongoRepository<Notify, String> {

	public List<Notify> findByToUniqueUserID(String toUniqueUserID);

	public String deleteByFromUserIdAndToUniqueUserID(String fromUserId, String toUniqueUserID);

}
