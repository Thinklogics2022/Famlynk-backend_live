package com.famlynk.dto;

/**
 * 
 * @author josephine
 * @date 8-8-2023
 */
public class SharedToFamily {

	private String name;
	private String image;
	private String uniqueUserID;

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

	public String getUniqueUserID() {
		return uniqueUserID;
	}

	public void setUniqueUserID(String uniqueUserID) {
		this.uniqueUserID = uniqueUserID;
	}

	@Override
	public String toString() {
		return "SharedToFamily [name=" + name + ", image=" + image + ", uniqueUserID=" + uniqueUserID + "]";
	}

}
