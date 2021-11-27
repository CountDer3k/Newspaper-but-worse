package edu.weber.bestgroupgroup2.Newspaperbutworse.Post;

import org.springframework.lang.NonNull;

public class CommentDto {
	
	@NonNull
	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
