package com.famlynk.dto;

public class AdminCount {
	 private long totalCount;
	 private long newsFeedCount;
	 private long commentCount;
	 private long registerCount;
	public long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}
	public long getNewsFeedCount() {
		return newsFeedCount;
	}
	public void setNewsFeedCount(long newsFeedCount) {
		this.newsFeedCount = newsFeedCount;
	}
	public long getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(long commentCount) {
		this.commentCount = commentCount;
	}
	
	public long getRegisterCount() {
		return registerCount;
	}
	public void setRegisterCount(long registerCount) {
		this.registerCount = registerCount;
	}
	@Override
	public String toString() {
		return "AdminCount [totalCount=" + totalCount + ", newsFeedCount=" + newsFeedCount + ", commentCount="
				+ commentCount + ", registerCount=" + registerCount + "]";
	}
	
}