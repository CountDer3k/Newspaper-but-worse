package edu.weber.bestgroupgroup2.Newspaperbutworse.Post;

import org.junit.Test;

import java.sql.Date;


import org.junit.Assert;

class PostModelTest {

	
	@Test
	public void testPostModel() {
		PostModel expected = new PostModel();
		PostModel actual = new PostModel();
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testSetId() {
		ArticleModel a = new ArticleModel();
		PostModel pm = new PostModel(1, 1, a);
		int expected = 1;
		
		int actual = pm.getId();
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testPostModelID(){
		ArticleModel a = new ArticleModel();
		PostModel pm = new PostModel(1, 1, new Date(0), new Date(0), a);
		
		Assert.assertEquals(1, pm.getId());
	}
}
