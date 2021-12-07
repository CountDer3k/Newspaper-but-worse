package edu.weber.bestgroupgroup2.Newspaperbutworse.Post;

import org.springframework.lang.NonNull;

public class PostDto {

	@NonNull
	private String title;
	
	@NonNull
	private String content;
	
	@NonNull
	private String access;

	
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

	public String getAccess() {
		return access;
	}

	public void setAccess(String access) {
		this.access = access;
	}
	
	
	
}
