package com.famlynk.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Josephine
 * @date 7-june-2023
 */
@Document(collection = "Notify")
public class Notify {
	@Id
	private String id;
	private String fromUserId;
	private String toUniqueUserID;
	private String fromName;
	private String toName;
	private String relation;
	private String status;
	private String email;
	private String fromUniqueUserID;
    private String profileImage;
    private String isUsed;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFromUserId() {
		return fromUserId;
	}

	public void setFromUserId(String fromUserId) {
		this.fromUserId = fromUserId;
	}

	public String getToUniqueUserID() {
		return toUniqueUserID;
	}

	public void setToUniqueUserID(String toUniqueUserID) {
		this.toUniqueUserID = toUniqueUserID;
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

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}


	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFromUniqueUserID() {
		return fromUniqueUserID;
	}

	public void setFromUniqueUserID(String fromUniqueUserID) {
		this.fromUniqueUserID = fromUniqueUserID;
	}

	public String getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}

	public String getIsUsed() {
		return isUsed;
	}

	public void setIsUsed(String isUsed) {
		this.isUsed = isUsed;
	}

	@Override
	public String toString() {
		return "Notify [id=" + id + ", fromUserId=" + fromUserId + ", toUniqueUserID=" + toUniqueUserID + ", fromName="
				+ fromName + ", toName=" + toName + ", relation=" + relation + ", status=" + status + ", email="
				+ email + ", fromUniqueUserID=" + fromUniqueUserID
				+ ", profileImage=" + profileImage + ",  isUsed=" + isUsed
				+ "]";
	}

	
}