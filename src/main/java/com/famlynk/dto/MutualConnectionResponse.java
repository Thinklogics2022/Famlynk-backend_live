package com.famlynk.dto;

public class MutualConnectionResponse {

	private String uniqueUserID;
	private String name;

	public String getUniqueUserID() {
		return uniqueUserID;
	}

	public void setUniqueUserID(String uniqueUserID) {
		this.uniqueUserID = uniqueUserID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "MutualConnectionResponse [uniqueUserID=" + uniqueUserID + ", name=" + name + "]";
	}

}
