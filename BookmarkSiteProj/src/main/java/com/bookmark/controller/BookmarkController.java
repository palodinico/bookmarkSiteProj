package com.bookmark.controller;

import java.net.MalformedURLException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bookmark.model.Bookmark;
import com.bookmark.paginate.Page;
import com.bookmark.paginate.PaginationHelper;

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
	public String addSampleData(
			@RequestParam(value="pageNumber", required=false, defaultValue="1") int pageNumber,
			@RequestParam(value="itemsPerPage", required=false, defaultValue="10") int itemsPerPage,
			Model model) {
		
		Page<Bookmark> bookmarks = getBookmarks(pageNumber, itemsPerPage);
		model.addAttribute("bookmarks", bookmarks.getPageItems());
		model.addAttribute("pageNumber", bookmarks.getPageNumber());
		model.addAttribute("pageAvailable", bookmarks.getPageAvailable());
		if (bookmarks.getPageNumber() == 1) {
			model.addAttribute("isFistPage", true);
		}
		if ( bookmarks.getPageAvailable() <= bookmarks.getPageNumber()) {
			model.addAttribute("isLastPage", true);
		}
		
		return "addSampleData";
	}
	
	public Page<Bookmark> getBookmarks(final int pageNo, final int pageSize) {
		PaginationHelper<Bookmark> ph = new PaginationHelper<Bookmark>();
		return ph.fetchPage(
				jdbcTemplate, 
				"SELECT count(*) FROM bookmarks", 
				"SELECT id, url FROM bookmarks", 
				new Object[]{}, 
				pageNo, 
				pageSize, 
				new ParameterizedRowMapper<Bookmark>(){
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
	}
}
