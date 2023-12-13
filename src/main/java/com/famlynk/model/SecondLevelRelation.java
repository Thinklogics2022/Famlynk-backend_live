package com.famlynk.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "SecondLevelRelation")
public class SecondLevelRelation {

	@Id
	private String id;
	private String firstLevelRelation;
	private String secondLevelRelation;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	@Override
	public String toString() {
		return "SecondLevelRelation [id=" + id + ", firstLevelRelation=" + firstLevelRelation + ", secondLevelRelation="
				+ secondLevelRelation + "]";
	}

}
