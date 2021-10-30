package edu.weber.bestgroupgroup2.Newspaperbutworse.Post;


/*
 This class holds a post (PostModel)
 and has a name of the author from the post 
 */

public class PostArticleModel {

	private PostModel post;
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
	
	
}
