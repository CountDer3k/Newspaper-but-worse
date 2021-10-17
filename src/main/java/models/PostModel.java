package models;

import java.sql.Date;

public class PostModel {

	private int Id;
	private int userId;
	private Date createDate;
	private Date modifiedDate;
	private ArticleModel article;
	
	public PostModel(){
		
	}
	
	public PostModel(int Id, int userId, ArticleModel article) 
	{
		this.Id = Id;
		this.userId = userId;
		this.article = article;
	}
	
	public PostModel(int Id, int userId, Date createDate, Date modifiedDate, ArticleModel article) 
	{
		this.Id = Id;
		this.userId = userId;
		this.article = article;
		this.createDate = createDate;
		this.modifiedDate = modifiedDate;
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
