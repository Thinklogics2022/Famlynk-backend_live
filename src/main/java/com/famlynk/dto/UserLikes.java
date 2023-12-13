package com.famlynk.dto;

import java.util.Date;

public class UserLikes {
	
	private String newsFeedId;
	private String userId;
	private String fromName;
    private Date createdOn;
    private String profilePicture;
    private String toName;
    private String photo;
    
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
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public String getProfilePicture() {
		return profilePicture;
	}
	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getFromName() {
		return fromName;
	}
	public void setFromName(String fromName) {
		this.fromName = fromName;
	}
	public String getToName() {
		return toName;
	}
	public void setToName(String toName) {
		this.toName = toName;
	}
	@Override
	public String toString() {
		return "UserLikes [newsFeedId=" + newsFeedId + ", userId=" + userId + ", fromName=" + fromName + ", createdOn="
				+ createdOn + ", profilePicture=" + profilePicture + ", toName=" + toName + ", photo=" + photo + "]";
	}
	
}