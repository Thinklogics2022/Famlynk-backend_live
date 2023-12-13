package com.famlynk.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ThirdLevelRelation")
public class ThirdLevelRelation {

	@Id
	private String id;
	private String secondLevelRelation;
	private String thirdLevelRelation;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	@Override
	public String toString() {
		return "ThirdLevelRelation [id=" + id + ", secondLevelRelation=" + secondLevelRelation + ", thirdLevelRelation="
				+ thirdLevelRelation + "]";
	}

}
