package edu.weber.bestgroupgroup2.Newspaperbutworse.Post;

public class Comment {

	private int postId;
	private int parentId;
	private String content;
	private String username;
	
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
}
