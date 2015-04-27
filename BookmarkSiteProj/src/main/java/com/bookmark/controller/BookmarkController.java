package com.bookmark.controller;

import java.net.MalformedURLException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bookmark.model.Bookmark;

@Controller
public class BookmarkController {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@RequestMapping("/bookmarks")
	public String bookmarks(Model model) {
		model.addAttribute("title", "All bookarks");
		return "bookmarks";
	}

	@RequestMapping("/selectedBookmarks")
	public String selectedBookmarks(Model model) {
		model.addAttribute("title", "Selected Bookmarks");
		return "selectedBookmarks";
	}

	@RequestMapping("/addSampleData")
	public String addSampleData(Model model) {

		jdbcTemplate.execute("drop table bookmarks if exists");
		jdbcTemplate.execute("create table bookmarks ("
				+ "id serial, url varchar(255))");
		String[] urls = new String[] { "http://www.w3.org",
				"http://www.abc.org", "http://www.bbb.org" };
		int no = 0;
		for (String url : urls) {
			jdbcTemplate.update(
					"INSERT INTO bookmarks(id, url) values (?,?)",
					no, url);
			no++;
		}
		
		List<Bookmark> results = jdbcTemplate.query(
				"SELECT id, url from bookmarks ORDER BY url ASC", 
				new RowMapper<Bookmark>() {
					@Override
					public Bookmark mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						Bookmark bookmark = null;
						try {
							bookmark = new Bookmark(rs.getString("url"), rs.getLong("id"));
						} catch (MalformedURLException e) {
							e.printStackTrace();
						}
						return bookmark;
					}
					
				});
		
		model.addAttribute("bookmarks", results);
		
		return "addSampleData";
	}
}
