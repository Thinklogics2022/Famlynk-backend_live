package com.famlynk.dto;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

public class RegisterMemberDetail {

	@Id
	@Indexed
	private String id;
	@Indexed
	private String userId;
	private String name;
	private String gender;
	private String dateOfBirth;
	private String password;
	private String email;
	private Date createdOn;
	private Date modifiedOn;
	private Boolean status;
	private String role;
	private Boolean enabled;
	private String verificationToken;
	private String phoneNumber;
	private String otp;
	private String profileImage;
	private String uniqueUserID;
	private String coverImage;
	private String maritalStatus;
	private String hometown;
	private String address;
	private Boolean isRegisterUser;

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getVerificationToken() {
		return verificationToken;
	}

	public void setVerificationToken(String verificationToken) {
		this.verificationToken = verificationToken;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
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

	public String getCoverImage() {
		return coverImage;
	}

	public void setCoverImage(String coverImage) {
		this.coverImage = coverImage;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Boolean getIsRegisterUser() {
		return isRegisterUser;
	}

	public void setIsRegisterUser(Boolean isRegisterUser) {
		this.isRegisterUser = isRegisterUser;
	}

	@Override
	public String toString() {
		return "RegisterMemberDetail [id=" + id + ", userId=" + userId + ", name=" + name + ", gender=" + gender
				+ ", dateOfBirth=" + dateOfBirth + ", password=" + password + ", email=" + email + ", createdOn="
				+ createdOn + ", modifiedOn=" + modifiedOn + ", status=" + status + ", role=" + role + ", enabled="
				+ enabled + ", verificationToken=" + verificationToken + ", phoneNumber=" + phoneNumber + ", otp=" + otp
				+ ", profileImage=" + profileImage + ", uniqueUserID=" + uniqueUserID + ", coverImage=" + coverImage
				+ ", maritalStatus=" + maritalStatus + ", hometown=" + hometown + ", address=" + address + "]";
	}
}
