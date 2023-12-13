package com.famlynk.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.famlynk.model.NewsFeed;

@Repository
public interface INewsFeedRepository extends MongoRepository<NewsFeed, String> {

	public NewsFeed findByNewsFeedId(String newsFeedId);

	public List<NewsFeed> findByUserId(String userId);

	public List<NewsFeed> findByUserIdAndUniqueUserID(String userId, String uniqueUserId);

	public void deleteByUserIdAndNewsFeedId(String userId, List<String> newsFeedId);

	Page<NewsFeed> findBySharedContaining(String value, Pageable pageable);
	
	public List<NewsFeed> findByUniqueUserIDAndSharedContaining(String uniqueUserID,String value);

	@Query(value = "{'sharedPerson': { $regex: ?0 }}", fields = "{ 'sharedPerson' : 0 }")
	List<NewsFeed> findBySharedPersonContainingExcludingField(String uniqueUserID);


}
