package com.bookmark.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.bookmark.Application;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class BookmarkControllerTest {
	
	@Autowired
    JdbcTemplate jdbcTemplate;

	@Autowired
	BookmarkController bookmarkControler;
	
	private MockMvc mvc;
	
	@Autowired
	WebApplicationContext wac;
	
	@Before
	public void setUp() throws Exception {
		jdbcTemplate.execute("drop table bookmarks if exists");
		jdbcTemplate.execute("create table bookmarks ("
				+ "id serial, url varchar(255))");
		int noMax = 1000;
		for (int no = 0; no < noMax; no++) {
			jdbcTemplate.update(
					"INSERT INTO bookmarks(id, url) values (?,?)",
					no, "http://www.w" + no + ".com");
		}
		
		MockitoAnnotations.initMocks(this);
		this.mvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}
	
	@Test
	public void canGET() throws Exception {
		mvc.perform(
				get("/bookmarks")
				//.header("host", "localhost:8080")
				.param("pageNumber", "1")
				.param("itemsPerPage", "10"))
		   .andExpect(status().isOk());
		mvc.perform(get("/selectedBookmarks")).andExpect(status().isOk());
		mvc.perform(get("/addSampleData")).andExpect(status().isOk());
	}
}
