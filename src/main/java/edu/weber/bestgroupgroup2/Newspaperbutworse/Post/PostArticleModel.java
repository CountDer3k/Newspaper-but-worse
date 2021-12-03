package edu.weber.bestgroupgroup2.Newspaperbutworse.Post;

import java.io.Serializable;

/*
 This class holds a post (PostModel)
 and has a name of the author from the post 
 */

public class PostArticleModel implements Serializable{

	private PostModel post;
	// author name
	private String name;
	
	
	public PostArticleModel() {
		
	}
	
	public PostModel getPost() {
		return post;
	}
	
	public void setPost(PostModel post) {
		this.post = post;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "PostArticleModel [post=" + post + ", name=" + name + "]";
	}
	
	
}
