package edu.weber.bestgroupgroup2.Newspaperbutworse.Post;


import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;



@RunWith(MockitoJUnitRunner.class)
public class PostControllerTest {

	PostController postController;
	private MockMvc mockMvc;
	@Mock
	PostService postService;
	
	
	@Before
	public void setup() {
		postController = new PostController(postService);
		mockMvc = MockMvcBuilders.standaloneSetup(this.postController).build();
	}
	
	@Test
	public void testShowArticleView() {

	}
}
