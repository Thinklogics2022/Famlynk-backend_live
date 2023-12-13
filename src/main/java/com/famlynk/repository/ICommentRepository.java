package com.famlynk.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.famlynk.model.Comment;

@Repository
public interface ICommentRepository extends MongoRepository<Comment, String> {

	void deleteByUserIdAndNewsFeedId(String userId, String newsFeedId);

	public List<Comment> findByNewsFeedId(String newsFeedId);

	public List<Comment> findByUserId(String userId);
	
	void deleteByUserIdAndIdIn(String userId, List<String> commentIds);
}
