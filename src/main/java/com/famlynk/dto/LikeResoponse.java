package com.famlynk.dto;

import java.util.List;

/**
 * 
 * @author Josephine
 * @date 29-may-2023
 *
 */
public class LikeResoponse {
	private String newsFeedId;
	private long count;
	private List<String> users;
	private String userId;

	public String getNewsFeedId() {
		return newsFeedId;
	}

	public void setNewsFeedId(String newsFeedId) {
		this.newsFeedId = newsFeedId;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public List<String> getUsers() {
		return users;
	}

	public void setUsers(List<String> users) {
		this.users = users;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "LikeResoponse [newsFeedId=" + newsFeedId + ", count=" + count + ", users=" + users + ", userId="
				+ userId + "]";
	}

}
