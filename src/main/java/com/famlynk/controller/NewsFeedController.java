package com.famlynk.controller;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.famlynk.dto.LikeResoponse;
import com.famlynk.dto.SharedToFamily;
import com.famlynk.dto.UserLikes;
import com.famlynk.model.NewsFeed;
import com.famlynk.service.NewsFeedService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/newsfeed")
public class NewsFeedController {

	@Autowired
	public NewsFeedService newsFeedService;

	/**
	 * post {@link NewsFeed}
	 * 
	 * @param newsfeed
	 * @return
	 */
	@PostMapping("/createnewsfeed")
	public NewsFeed addNewsFeed(@RequestBody NewsFeed newsfeed) {
		return newsFeedService.addNewsFeed(newsfeed);
	}

	/**
	 * Retrieving members news feed without family
	 * 
	 * @param offset
	 * @param pageSize
	 * @param userId
	 * @return
	 */
	@GetMapping("/retrievenewsfeed/pagination/{offset}/{pageSize}/{userId}")
	public Page<NewsFeed> getNewsFeedUsingPagination(@PathVariable int offset, @PathVariable int pageSize,
			@PathVariable String userId) {
		return newsFeedService.getNewsFeedByUserId(offset, pageSize, userId);
	}

	/**
	 * Retrieving news feed from family without pagination
	 * 
	 * @param offset
	 * @param pageSize
	 * @param userId
	 * @return
	 */
	@GetMapping("/retrievefamilynewsfeed/{userId}")
	public List<NewsFeed> getFamilyMembersNewsFeed(@PathVariable String userId) {
		return newsFeedService.getFamilyMembersNewsFeedByUserId(userId);
	}

	/**
	 * get the size of the total {@link NewsFeed}
	 * 
	 * @return
	 */
	@GetMapping("/newsfeedTotal")
	public long getnewsfeedCount() {
		return newsFeedService.getNewsFeedCount();
	}

	/**
	 * Retrieving news feed total from family
	 * 
	 * @param offset
	 * @param pageSize
	 * @param userId
	 * @return
	 */
	@GetMapping("/newsfeedtotalwithfamily/{userId}")
	public long getFamilyMemberNewsFeedCount(@PathVariable String userId) {
		return newsFeedService.getFamilyMembersNewsFeedCount(userId);
	}

	/**
	 * Retrieving news feed total without from family
	 * 
	 * @param offset
	 * @param pageSize
	 * @param userId
	 * @return
	 */
	@GetMapping("/newsfeedtotalwithoutfamily/{userId}")
	public long getNewsfeedsWithoutFamilyMembers(@PathVariable String userId) {
		return newsFeedService.countNewsFeedsExcludingFamilyMembersByUserId(userId);
	}

	/**
	 * update {@link NewsFeed}
	 * 
	 * @param newsfeed
	 * @param newsFeedId
	 * @return
	 */
	@PutMapping("/updatenewsfeed/{newsFeedId}")
	public ResponseEntity<NewsFeed> updateNewsFeedByNewsFeedId(@RequestBody NewsFeed newsfeed,
			@PathVariable String newsFeedId) {
		NewsFeed put = newsFeedService.updateNewsFeedByNewsFeedId(newsfeed, newsFeedId);
		return new ResponseEntity<NewsFeed>(put, HttpStatus.OK);
	}

	/**
	 * delete {@link NewsFeed} by using userId and id, also delete the comments for
	 * that particular {@link NewsFeed}
	 * 
	 * @param userId
	 * @param id
	 * @return
	 */
	@DeleteMapping("/deletenewsfeed/{userId}/{id}")
	private ResponseEntity<NewsFeed> deleteNewsFeedById(@PathVariable("userId") String userId,
			@PathVariable("id") String id) {
		newsFeedService.deleteNewsFeedByUserIdAndId(userId, id);
		return new ResponseEntity("NewsFeed with its Comments Deleted", HttpStatus.OK);
	}

	/**
	 * update like by using userId and id
	 * 
	 * @param userId
	 * @param id
	 * @return
	 */
	@GetMapping("/likes/{userId}/{id}")
	public LikeResoponse getLikesByUserIdAndId(@PathVariable("userId") String userId, @PathVariable("id") String id) {
		return newsFeedService.updateLikeByUserIdAndId(userId, id);
	}

	/**
	 * update the profile image and name for whole {@link NewsFeed} by using userId
	 * 
	 * @param newsfeed
	 * @param userId
	 * @return
	 */
	@PutMapping("/profileandnameupdate/{userId}")
	public List<NewsFeed> updateProfileAndNameForNewsFeeds(@RequestBody NewsFeed newsfeed,
			@PathVariable("userId") String userId) {
		List<NewsFeed> getObj = newsFeedService.updateWholeNewsFeedByUserId(newsfeed, userId);
		return getObj;
	}

	/**
	 * List the particular {@link NewsFeed} by using userId and uniqueUserID
	 * 
	 * @param userId
	 * @param uniqueUserID
	 * @return
	 */
	@GetMapping("/retrieveusernewsfeed/{userId}/{uniqueUserID}")
	public List<NewsFeed> getNewsFeedsByUserIdAndUniqueUserID(@PathVariable String userId,
			@PathVariable String uniqueUserID) {
		 List<NewsFeed> newsFeeds = newsFeedService.getNewsfeedByUserIdAndUniqueUserID(userId, uniqueUserID);
		Collections.reverse(newsFeeds);
		return newsFeeds;
	}
	
	/**
	 * Selected post delete api in array
	 * @param userId
	 * @param newsFeedId
	 * @return
	 */
	@DeleteMapping("/deletePost/{userId}")
	public ResponseEntity<String> deletePost(@PathVariable String userId, @RequestBody List<String> newsFeedId) {
	    newsFeedService.deleteUserPost(userId, newsFeedId);
		return ResponseEntity.ok("Selected post deleted successfully.");
	}
	
	/**
	 * user likes post 
	 * @param userId
	 * @return
	 */
	@GetMapping("/userLikes/{userId}")
	public List<UserLikes> userLikes(@PathVariable String userId){
		return newsFeedService.userLikes(userId);
	}

	/**
	 * retrieve registered user in user's familymembers
	 * 
	 * @param userId
	 * @return
	 */
	@GetMapping("/retrieveregistereduser/{userId}")
	public List<SharedToFamily> getregisteruser(@PathVariable String userId) {
		return newsFeedService.getRegisteredFamilyMember(userId);
	}

	/**
	 * get {@link NewsFeed} based on shared value(public or family)
	 * 
	 * @param value
	 * @param page
	 * @param size
	 * @return
	 */
	@GetMapping("/retrievenewsfeed/{shared}")
	public Page<NewsFeed> getPaginatedNewsFeed(@PathVariable String shared, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "20") int size) {
		return newsFeedService.getNewsFeed(shared, page, size);
	}
	/**
	 * get family member {@link NewsFeed} 
	 * @param userId
	 * @param shared
	 * @return
	 */
	@GetMapping("/retrievefamilynewsfeed/{userId}/{shared}")
	public List<NewsFeed> getFamilyNewsFeed(@PathVariable String userId, @PathVariable String shared){
		return newsFeedService.getFamilyNewsFeed(userId, shared);
	}

	/**
	 * get a particular {@link NewsFeed} shared by other user
	 * 
	 * @param uniqueUserID
	 * @return
	 */
	@GetMapping("/sharedperson/{uniqueUserID}")
	public List<NewsFeed> getPersonNewsFeed(@PathVariable String uniqueUserID) {
		return newsFeedService.getsendToPersonsNewsFeed(uniqueUserID);
	}
	
	/**
	 * get specific newsFeed
	 * @param newsFeedId
	 * @return
	 */
	@GetMapping("/userNewsFeed/{newsFeedId}")
	public NewsFeed userPost(@PathVariable String newsFeedId) {
		return newsFeedService.userNewsFeed(newsFeedId);
	}
}
