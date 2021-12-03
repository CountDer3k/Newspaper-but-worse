package edu.weber.bestgroupgroup2.Newspaperbutworse.Post;

import org.springframework.lang.NonNull;

public class CommentDto {
	
	@NonNull
	private String content;
	
	@NonNull
	private int parentId;

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
}
