package com.famlynk.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "FamilyRelation")
public class FamilyRelation {

	@Id
	private String id;
	private String primaryRelation;
	private String secondaryRelation;
	private String gender;
	private String relationship;
	private String level;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPrimaryRelation() {
		return primaryRelation;
	}

	public void setPrimaryRelation(String primaryRelation) {
		this.primaryRelation = primaryRelation;
	}

	public String getSecondaryRelation() {
		return secondaryRelation;
	}

	public void setSecondaryRelation(String secondaryRelation) {
		this.secondaryRelation = secondaryRelation;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getRelationship() {
		return relationship;
	}

	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	@Override
	public String toString() {
		return "FamilyRelation [id=" + id + ", primaryRelation=" + primaryRelation + ", secondaryRelation="
				+ secondaryRelation + ", gender=" + gender + ", relationship=" + relationship + ", level=" + level
				+ "]";
	}

}
