package edu.weber.bestgroupgroup2.Newspaperbutworse.Article;

import org.springframework.beans.factory.annotation.Autowired;

import edu.weber.bestgroupgroup2.Newspaperbutworse.Models.ArticleModel;

public class ArticleService {

	private ArticleRepository articleRepository;
	
	@Autowired
	public ArticleService(ArticleRepository articleRepo) {
		this.articleRepository = articleRepo;
	}
	
	
}
