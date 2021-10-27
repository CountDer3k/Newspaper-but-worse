package edu.weber.bestgroupgroup2.Newspaperbutworse.Post;

import java.sql.Date;

public class PostArticleModel {

	private int Id;
	private int userId;
	private Date createDate;
	private Date modifiedDate;
	private int postId;
	private int sectionId;
	private String title;
	private String content;
	private String access;
	private PostModel post;
	
	public PostArticleModel() {
		
	}
	
	public PostModel getPost() {
		return post;
	}
	
	public void setPost(PostModel post) {
		this.post = post;
	}
}
