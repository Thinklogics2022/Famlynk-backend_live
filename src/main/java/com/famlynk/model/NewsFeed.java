package com.famlynk.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.famlynk.dto.MutualConnectionResponse;
import com.fasterxml.jackson.annotation.JsonInclude;

@Document(collection = "Newsfeed")
public class NewsFeed {
	@Id
	private String newsFeedId;
	private String userId;
	private String profilePicture;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String vedio;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String photo;
	private Long like;
	private String description;
	private String name;
	private Date createdOn;
	private List<String> userLikes;
	private List<String> userLikeNames;
	@Indexed
	private String uniqueUserID;
	private List<MutualConnectionResponse> mutualConnection = new ArrayList<>();
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private List<String> shared;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private List<String> sharedPerson;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getNewsFeedId() {
		return newsFeedId;
	}

	public void setNewsFeedId(String newsFeedId) {
		this.newsFeedId = newsFeedId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getVedio() {
		return vedio;
	}

	public void setVedio(String vedio) {
		this.vedio = vedio;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public Long getLike() {
		if (like == null) {
			like = 0L;
		}
		return like;
	}

	public void setLike(Long like) {
		this.like = like;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public List<String> getUserLikes() {
		if (userLikes == null) {
			userLikes = new ArrayList<>();
		}
		return userLikes;
	}

	public void setUserLikes(List<String> userLikes) {
		this.userLikes = userLikes;
	}

	public List<String> getUserLikeNames() {
		if (userLikeNames == null) {
			userLikeNames = new ArrayList<>();
		}
		return userLikeNames;
	}

	public void setUserLikeNames(List<String> userLikeNames) {
		this.userLikeNames = userLikeNames;
	}

	public String getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}

	public String getUniqueUserID() {
		return uniqueUserID;
	}

	public void setUniqueUserID(String uniqueUserID) {
		this.uniqueUserID = uniqueUserID;
	}

	public List<MutualConnectionResponse> getMutualConnection() {
		return mutualConnection;
	}

	public void setMutualConnection(List<MutualConnectionResponse> mutualConnection) {
		this.mutualConnection = mutualConnection;
	}

	public List<String> getSharedPerson() {
		return sharedPerson;
	}

	public void setSharedPerson(List<String> sharedPerson) {
		this.sharedPerson = sharedPerson;
	}

	public List<String> getShared() {
		return shared;
	}

	public void setShared(List<String> shared) {
		this.shared = shared;
	}

	@Override
	public String toString() {
		return "NewsFeed [newsFeedId=" + newsFeedId + ", userId=" + userId + ", profilePicture=" + profilePicture
				+ ", vedio=" + vedio + ", photo=" + photo + ", like=" + like + ", description=" + description
				+ ", name=" + name + ", createdOn=" + createdOn + ", userLikes=" + userLikes + ", userLikeNames="
				+ userLikeNames + ", uniqueUserID=" + uniqueUserID + ", mutualConnection=" + mutualConnection
				+ ", shared=" + shared + ", sharedPerson=" + sharedPerson + "]";
	}

}
