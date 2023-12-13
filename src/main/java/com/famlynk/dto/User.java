package com.famlynk.dto;

public class User {
	private String name;
	private String dateOfBirth;
	private String profileImage;
	private String uniqueUserID;
	private String userId;
	private String gender;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getProfileImage() {
		return profileImage;
	}
	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}
	public String getUniqueUserID() {
		return uniqueUserID;
	}
	public void setUniqueUserID(String uniqueUserID) {
		this.uniqueUserID = uniqueUserID;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	@Override
	public String toString() {
		return "AdminUserPreview [name=" + name + ", dateOfBirth=" + dateOfBirth + ", profileImage=" + profileImage
				+ ", uniqueUserID=" + uniqueUserID + ", userId=" + userId + ", gender=" + gender + "]";
	}
}