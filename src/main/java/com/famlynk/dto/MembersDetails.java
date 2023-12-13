package com.famlynk.dto;

/**
 * 
 * @author Josephine
 * @date 23-May-2023
 *
 */
public class MembersDetails {

	private String name;
	private String gender;
	private String dateOfBirth;
	private String email;
	private String maritalStatus;
	private String hometown;
	private String profileImage;
	private String address;
	private String mobileNo;
	private String uniqueUserID;
	private String userId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getHometown() {
		return hometown;
	}

	public void setHometown(String hometown) {
		this.hometown = hometown;
	}

	public String getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
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

	@Override
	public String toString() {
		return "MembersDetails [name=" + name + ", gender=" + gender + ", dateOfBirth=" + dateOfBirth + ", email="
				+ email + ", maritalStatus=" + maritalStatus + ", hometown=" + hometown + ", profileImage="
				+ profileImage + ", address=" + address + ", mobileNo=" + mobileNo + ", uniqueUserID=" + uniqueUserID
				+ ", userId=" + userId + "]";
	}

}
