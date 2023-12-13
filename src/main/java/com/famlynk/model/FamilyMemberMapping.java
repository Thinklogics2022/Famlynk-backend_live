package com.famlynk.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "FamilyMemberMapping")
public class FamilyMemberMapping {

	@Id
	private String id;
	@Indexed
	private String primaryMemberId;
	private String secondaryMemberId;
	private String relationship;
	@Indexed
	private String userId;
	private String name;
	private String image;
	private String gender;
	private String familyLevel;
	private String side;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPrimaryMemberId() {
		return primaryMemberId;
	}

	public void setPrimaryMemberId(String primaryMemberId) {
		this.primaryMemberId = primaryMemberId;
	}

	public String getSecondaryMemberId() {
		return secondaryMemberId;
	}

	public void setSecondaryMemberId(String secondaryMemberId) {
		this.secondaryMemberId = secondaryMemberId;
	}

	public String getRelationship() {
		return relationship;
	}

	public void setRelationship(String relationship) {
		this.relationship = relationship;
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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getFamilyLevel() {
		return familyLevel;
	}

	public void setFamilyLevel(String familyLevel) {
		this.familyLevel = familyLevel;
	}

	public String getSide() {
		return side;
	}

	public void setSide(String side) {
		this.side = side;
	}

	@Override
	public String toString() {
		return "FamilyMemberMapping [id=" + id + ", primaryMemberId=" + primaryMemberId + ", secondaryMemberId="
				+ secondaryMemberId + ", relationship=" + relationship + ", userId=" + userId + ", name=" + name
				+ ", image=" + image + ", gender=" + gender + ", familyLevel=" + familyLevel + ", side=" + side + "]";
	}

}
