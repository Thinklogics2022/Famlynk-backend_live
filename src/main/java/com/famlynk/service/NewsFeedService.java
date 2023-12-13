package com.famlynk.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.famlynk.dto.LikeResoponse;
import com.famlynk.dto.SharedToFamily;
import com.famlynk.dto.UserLikes;
import com.famlynk.model.Comment;
import com.famlynk.model.FamilyMembers;
import com.famlynk.model.NewsFeed;
import com.famlynk.model.Register;
import com.famlynk.repository.ICommentRepository;
import com.famlynk.repository.IFamilyMembersRepository;
import com.famlynk.repository.INewsFeedRepository;
import com.famlynk.repository.IRegisterRepository;

@Service
public class NewsFeedService {

	@Autowired
	public INewsFeedRepository newsFeedRepository;
	@Autowired
	public CommentService commentService;
	@Autowired
	public IRegisterRepository registerRepository;
	@Autowired
	public ICommentRepository commentRepository;
	@Autowired
	public IFamilyMembersRepository familyMembersRepository;

	public NewsFeed addNewsFeed(NewsFeed newsFeed) {
		Date date = (Date) Calendar.getInstance().getTime();
		newsFeed.setCreatedOn(date);
		return newsFeedRepository.save(newsFeed);
	}

	/**
	 * Retrieves a list of uniqueUserID for family members by using userId
	 * 
	 * @param userId
	 * @return
	 */
	private List<String> getFamilyMembersUniqueUserIDByUserId(String userId) {
		List<FamilyMembers> familyMembers = familyMembersRepository.findByUserId(userId);
		List<String> uniqueIDs = familyMembers.stream().map(FamilyMembers::getUniqueUserID).distinct()
				.collect(Collectors.toList());
		return uniqueIDs;
	}

	public long getFamilyMembersNewsFeedCount(String userId) {
		List<NewsFeed> newsFeedList = newsFeedRepository.findAll();
		List<String> uniqueIDs = getFamilyMembersUniqueUserIDByUserId(userId);
		List<NewsFeed> newsFeeds = newsFeedList.stream()
				.filter(newsFeed -> uniqueIDs.contains(newsFeed.getUniqueUserID())).collect(Collectors.toList());
		return newsFeeds.size();
	}

	public long countNewsFeedsExcludingFamilyMembersByUserId(String userid) {
		List<NewsFeed> newsFeedList = newsFeedRepository.findAll();
		List<String> uniqueIDs = getFamilyMembersUniqueUserIDByUserId(userid);
		List<NewsFeed> newsFeeds = newsFeedList.stream()
				.filter(newsFeed -> !uniqueIDs.contains(newsFeed.getUniqueUserID())).collect(Collectors.toList());
		return newsFeeds.size();
	}

	public Page<NewsFeed> getNewsFeedByUserId(int pageNumber, int pageSize, String userId) {
		PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "createdOn"));
		Page<NewsFeed> newsFeedPage = newsFeedRepository.findAll(pageRequest);
		List<NewsFeed> newsFeedList = newsFeedPage.getContent();
		List<String> userIds = getFamilyMembersUniqueUserIDByUserId(userId);
		List<NewsFeed> newsFeeds = new ArrayList<>();
		for (NewsFeed newsFeed : newsFeedList) {
			if (!userIds.contains(newsFeed.getUniqueUserID())) {
				newsFeeds.add(newsFeed);
			}
		}
		return new PageImpl<>(newsFeeds, pageRequest, newsFeeds.size());
	}

	public List<NewsFeed> getFamilyMembersNewsFeedByUserId(String userId) {
		List<NewsFeed> newsFeedList = newsFeedRepository.findAll();
		List<String> userIds = getFamilyMembersUniqueUserIDByUserId(userId);
		List<NewsFeed> newsFeeds = newsFeedList.stream()
				.filter(newsFeed -> userIds.contains(newsFeed.getUniqueUserID())).collect(Collectors.toList());
		Collections.reverse(newsFeeds);
		return newsFeeds;
	}

	public long getNewsFeedCount() {
		List<NewsFeed> newsFeedList = newsFeedRepository.findAll();
		return newsFeedList.size();
	}

	public NewsFeed updateNewsFeedByNewsFeedId(NewsFeed newsFeed, String newsFeedId) {
		NewsFeed newsFeedObj = newsFeedRepository.findById(newsFeedId).get();
		newsFeedObj.setLike(newsFeed.getLike());
		newsFeedObj.setDescription(newsFeed.getDescription());
		newsFeedObj.setPhoto(newsFeed.getPhoto());
		newsFeedObj.setVedio(newsFeed.getVedio());
		newsFeedObj.setUserLikes(newsFeed.getUserLikes());
		return newsFeedRepository.save(newsFeedObj);
	}

	public void deleteNewsFeedByUserIdAndId(String userId, String id) {
		commentService.deleteByNewsFeedComments(userId, id);
		newsFeedRepository.deleteById(id);
		newsFeedRepository.findAll();
	}

	public LikeResoponse updateLikeByUserIdAndId(String userId, String id) {
		LikeResoponse likeResoponse = new LikeResoponse();
		NewsFeed newsFeed = newsFeedRepository.findById(id).get();
		Register register = registerRepository.findByUserId(userId);
		if (newsFeed == null) {
			return likeResoponse;
		}
		if (newsFeed.getUserLikes().contains(userId) && newsFeed.getUserLikeNames().contains(register.getName())) {
			newsFeed.getUserLikes().remove(userId);
			newsFeed.getUserLikeNames().remove(register.getName());
			newsFeed.setLike(newsFeed.getLike() - 1);
		} else {
			newsFeed.getUserLikes().add(userId);
			newsFeed.getUserLikeNames().add(register.getName());
			newsFeed.setLike(newsFeed.getLike() + 1);
			likeResoponse.setUserId(newsFeed.getUserId());
		}
		newsFeedRepository.save(newsFeed);
		likeResoponse.setCount(newsFeed.getLike());
		likeResoponse.setNewsFeedId(id);
		List<String> users = new ArrayList<String>();
		for (String userIdObj : newsFeed.getUserLikes()) {
			users.add(registerRepository.findByUserId(userIdObj).getName());
		}
		likeResoponse.setUsers(users);
		return likeResoponse;
	}

	public List<NewsFeed> updateWholeNewsFeedByUserId(NewsFeed newsFeed, String userId) {
		List<NewsFeed> newsFeedList = newsFeedRepository.findByUserId(userId);
		for (NewsFeed newsFeedObj : newsFeedList) {
			newsFeedObj.setName(newsFeed.getName());
			newsFeedObj.setProfilePicture(newsFeed.getProfilePicture());
			newsFeedRepository.save(newsFeedObj);
		}
		List<Comment> commentList = commentRepository.findByUserId(userId);
		for (Comment comment : commentList) {
			comment.setName(newsFeed.getName());
			comment.setProfilePicture(newsFeed.getProfilePicture());
			commentRepository.save(comment);
		}
		return newsFeedList;
	}

	public List<NewsFeed> getNewsfeedByUserIdAndUniqueUserID(String userId, String uniqueUserID) {
		return newsFeedRepository.findByUserIdAndUniqueUserID(userId, uniqueUserID);
	}

	public void deleteUserPost(String userId, List<String> newsFeedId) {
		newsFeedRepository.deleteByUserIdAndNewsFeedId(userId, newsFeedId);
	}

	public List<UserLikes> userLikes(String userId) {
		List<UserLikes> likedPosts = new ArrayList<>();
		List<NewsFeed> allNewsFeeds = newsFeedRepository.findAll();
		Register register = registerRepository.findByUserId(userId);
		for (NewsFeed newsFeed : allNewsFeeds) {
			if (newsFeed.getUserLikes().contains(userId)) {
				UserLikes userLikes = new UserLikes();
				userLikes.setNewsFeedId(newsFeed.getNewsFeedId());
				userLikes.setUserId(userId);
				userLikes.setCreatedOn(newsFeed.getCreatedOn());
				userLikes.setProfilePicture(newsFeed.getProfilePicture());
				userLikes.setPhoto(newsFeed.getPhoto());
				userLikes.setToName(newsFeed.getName());
				userLikes.setFromName(register.getName());
				likedPosts.add(userLikes);
			}
		}
		Collections.reverse(likedPosts);
		return likedPosts;
	}

	public List<SharedToFamily> getRegisteredFamilyMember(String userId) {
		List<SharedToFamily> sharedToFamilyList = new ArrayList<SharedToFamily>();
		List<FamilyMembers> familyMemberList = familyMembersRepository.findByUserIdAndIsRegisterUser(userId, true);
		for (FamilyMembers familyMembers : familyMemberList) {
			SharedToFamily sharedToFamily = new SharedToFamily();
			sharedToFamily.setName(familyMembers.getName());
			sharedToFamily.setImage(familyMembers.getImage());
			sharedToFamily.setUniqueUserID(familyMembers.getUniqueUserID());
			sharedToFamilyList.add(sharedToFamily);
		}
		return sharedToFamilyList;
	}

	public Page<NewsFeed> getNewsFeed(String value, int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("createdOn")));
		Page<NewsFeed> newsFeed = newsFeedRepository.findBySharedContaining(value, pageable);
		return newsFeed;
	}

	public List<NewsFeed> getFamilyNewsFeed(String userId, String value) {
		List<FamilyMembers> familyMembers = familyMembersRepository.findByUserIdAndIsRegisterUser(userId, true);
		List<NewsFeed> newsFeedObj = new ArrayList<NewsFeed>();
		for (FamilyMembers familyMembersObj : familyMembers) {
			List<NewsFeed> newsFeed = newsFeedRepository
					.findByUniqueUserIDAndSharedContaining(familyMembersObj.getUniqueUserID(), value);
			newsFeedObj.addAll(newsFeed);
		}
		Collections.reverse(newsFeedObj);
		return newsFeedObj;
	}

	public List<NewsFeed> getsendToPersonsNewsFeed(String uniqueUSerID) {
		List<NewsFeed> newsFeed = newsFeedRepository.findBySharedPersonContainingExcludingField(uniqueUSerID);
		Collections.reverse(newsFeed);
		return newsFeed;
	}

	public NewsFeed userNewsFeed(String newsFeedId) {
		return newsFeedRepository.findByNewsFeedId(newsFeedId);
	}
}
