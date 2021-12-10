package edu.weber.bestgroupgroup2.Newspaperbutworse.Post;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;

import org.junit.Assert;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class CommentTests {
	
	PostService postService;
	
	@Mock
	PostRepository postRepository;
	
	@Before
	public void setup() {
		postService = new PostService(postRepository);
	}
	
//	@Test
//	public void testAddNewComment() {
//		
//		CommentDto commDto = new CommentDto();
//		int userID = 1;
//		
//		commDto.setParentId(1);
//		commDto.setContent(null);
//		
//		Comment actual = postService.addNewComment(commDto, userID);
//		List<Comment> expected = postService.getCommentsFromArticle(1);
//		
//		Assert.assertTrue(expected.contains(actual));
//
//	}
}
