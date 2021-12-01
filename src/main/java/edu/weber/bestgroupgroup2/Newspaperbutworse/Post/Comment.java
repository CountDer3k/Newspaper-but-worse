package edu.weber.bestgroupgroup2.Newspaperbutworse.Post;

import edu.weber.bestgroupgroup2.Newspaperbutworse.User.User;

public class Comment {

	private int postId;
	private int parentId;
	private String content;
	private User user;
	
	public Comment() {}
	
	public Comment(int parentId) {
		this.parentId = parentId;
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
