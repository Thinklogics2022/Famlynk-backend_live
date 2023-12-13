package com.famlynk.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.famlynk.dto.MutualConnectionResponse;

@Document(collection = "Familymembers")
public class FamilyMembers {

	@Id
	@Indexed
	private String famid;
	@Indexed
	private String userId;
	private String name;
	private String dob;
	private String image;
	@Indexed
	private String relation;
	@Indexed
	private String firstLevelRelation;
	@Indexed
	private String secondLevelRelation;
	@Indexed
	private String thirdLevelRelation;
	private String gender;
	private Date createdOn;
	private Date modifiedOn;
	private String mobileNo;
	private String email;
	@Indexed
	private String uniqueUserID;
	private Notify notification;
	private String maritalStatus;
	private String hometown;
	private String address;
	private List<MutualConnectionResponse> mutualConnection = new ArrayList<>();
	private String side;
	private boolean isRegisterUser;

	public String getFamid() {
		return famid;
	}

	public void setFamid(String famid) {
		this.famid = famid;
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

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	public String getFirstLevelRelation() {
		return firstLevelRelation;
	}

	public void setFirstLevelRelation(String firstLevelRelation) {
		this.firstLevelRelation = firstLevelRelation;
	}

	public String getSecondLevelRelation() {
		return secondLevelRelation;
	}

	public void setSecondLevelRelation(String secondLevelRelation) {
		this.secondLevelRelation = secondLevelRelation;
	}

	public String getThirdLevelRelation() {
		return thirdLevelRelation;
	}

	public void setThirdLevelRelation(String thirdLevelRelation) {
		this.thirdLevelRelation = thirdLevelRelation;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
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

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUniqueUserID() {
		return uniqueUserID;
	}

	public void setUniqueUserID(String uniqueUserID) {
		this.uniqueUserID = uniqueUserID;
	}

	public Notify getNotification() {
		return notification;
	}

	public void setNotification(Notify notification) {
		this.notification = notification;
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

	public List<MutualConnectionResponse> getMutualConnection() {
		return mutualConnection;
	}

	public void setMutualConnection(List<MutualConnectionResponse> mutualConnection) {
		this.mutualConnection = mutualConnection;
	}

	public String getSide() {
		return side;
	}

	public void setSide(String side) {
		this.side = side;
	}

	public boolean isRegisterUser() {
		return isRegisterUser;
	}

	public void setRegisterUser(boolean isRegisterUser) {
		this.isRegisterUser = isRegisterUser;
	}

	@Override
	public String toString() {
		return "FamilyMembers [famid=" + famid + ", userId=" + userId + ", name=" + name + ", dob=" + dob + ", image="
				+ image + ", relation=" + relation + ", firstLevelRelation=" + firstLevelRelation
				+ ", secondLevelRelation=" + secondLevelRelation + ", thirdLevelRelation=" + thirdLevelRelation
				+ ", gender=" + gender + ", createdOn=" + createdOn + ", modifiedOn=" + modifiedOn + ", mobileNo="
				+ mobileNo + ", email=" + email + ", uniqueUserID=" + uniqueUserID + ", notification=" + notification
				+ ", maritalStatus=" + maritalStatus + ", hometown=" + hometown + ", address=" + address
				+ ", mutualConnection=" + mutualConnection + ", side=" + side + ", isRegisterUser=" + isRegisterUser
				+ "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(uniqueUserID);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FamilyMembers other = (FamilyMembers) obj;
		return Objects.equals(uniqueUserID, other.uniqueUserID);
	}
}
