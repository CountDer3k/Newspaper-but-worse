package edu.weber.bestgroupgroup2.Newspaperbutworse.Post;


import java.io.Serializable;
import java.sql.Date;

public class PostModel implements Serializable{

	@Override
	public String toString() {
		return "PostModel [Id=" + Id + ", userId=" + userId + ", createDate=" + createDate + ", modifiedDate="
				+ modifiedDate + ", article=" + article + ", url=" + url + "]";
	}

	private int Id;
	private int userId;
	private Date createDate;
	private Date modifiedDate;
	private ArticleModel article;
	private String url;
	
	public PostModel() {}
	
	public PostModel(int Id){
		url = "articles/"+Id;
	}
	
	public PostModel(int Id, int userId, ArticleModel article) 
	{
		this.Id = Id;
		this.userId = userId;
		this.article = article;
		url = "articles/"+Id;
	}
	
	public PostModel(int Id, int userId, Date createDate, Date modifiedDate, ArticleModel article) 
	{
		this.Id = Id;
		this.userId = userId;
		this.article = article;
		this.createDate = createDate;
		this.modifiedDate = modifiedDate;
		url = "articles/"+Id;
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public ArticleModel getArticle() {
		return article;
	}

	public void setArticle(ArticleModel article) {
		this.article = article;
	}
	
	
	
}
