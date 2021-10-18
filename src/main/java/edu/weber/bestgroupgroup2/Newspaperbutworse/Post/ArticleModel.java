package edu.weber.bestgroupgroup2.Newspaperbutworse.Post;

import java.sql.Date;

public class ArticleModel {

	private int postId;
	private int sectionId;
	private String access;
	private String title;
	private String content;
	
	
	public ArticleModel() {
		
	}
	
	public ArticleModel(String title, String content) {
		this.title = title;
		this.content = content;
	}
	
	public ArticleModel(int postId, int sectionId, String title, String content, String access) 
	{
		this.postId = postId;
		this.sectionId = sectionId;
		this.title = title;
		this.content = content;
		this.access = access;
	}
	
	public int getPostId() {
		return postId;
	}
	public void setPostId(int postId) {
		this.postId = postId;
	}
	public int getSectionId() {
		return sectionId;
	}
	public void setSectionId(int sectionId) {
		this.sectionId = sectionId;
	}
	public String getAccess() {
		return access;
	}
	public void setAccess(String access) {
		this.access = access;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
	
	
}

