package com.famlynk.model;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection= "Complaints")
public class Complaint {
	@Id
	private String id;
	private String newsFeedId;
	private String userId;
	private String uniqueUserID;
	private String category;
	private String description;
	private Date createdOn;
	private String action;
	private String commentId;
	private String type;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNewsFeedId() {
		return newsFeedId;
	}
	public void setNewsFeedId(String newsFeedId) {
		this.newsFeedId = newsFeedId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUniqueUserID() {
		return uniqueUserID;
	}
	public void setUniqueUserID(String uniqueUserID) {
		this.uniqueUserID = uniqueUserID;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	
	public String getCommentId() {
		return commentId;
	}
	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "Complaint [id=" + id + ", newsFeedId=" + newsFeedId + ", userId=" + userId + ", uniqueUserID="
				+ uniqueUserID + ", category=" + category + ", description=" + description + ", createdOn=" + createdOn
				+ ", action=" + action + ", commentId=" + commentId + ", type=" + type + "]";
	}
	
}