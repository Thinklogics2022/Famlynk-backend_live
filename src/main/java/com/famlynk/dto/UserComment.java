package com.famlynk.dto;

import java.util.Date;

public class UserComment {

	private String id;
	private String userId;
	private String fromName;
	private String profilePicture;
	private String newsFeedId;
	private String comment;
	private Date createdOn;
	private String photo;
	private String toName;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getFromName() {
		return fromName;
	}
	public void setFromName(String fromName) {
		this.fromName = fromName;
	}
	public String getProfilePicture() {
		return profilePicture;
	}
	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}
	public String getNewsFeedId() {
		return newsFeedId;
	}
	public void setNewsFeedId(String newsFeedId) {
		this.newsFeedId = newsFeedId;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getToName() {
		return toName;
	}
	public void setToName(String toName) {
		this.toName = toName;
	}
	@Override
	public String toString() {
		return "UserComment [id=" + id + ", userId=" + userId + ", fromName=" + fromName + ", profilePicture="
				+ profilePicture + ", newsFeedId=" + newsFeedId + ", comment=" + comment + ", createdOn=" + createdOn
				+ ", photo=" + photo + ", toName=" + toName + "]";
	}
}