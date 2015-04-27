package com.bookmark;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class Application {

	private static ApplicationContext context;

	public static void main(String[] args) {
		context = SpringApplication.run(Application.class, args);
		initDatabase();
	}
		
	public static void initDatabase() {
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		jdbcTemplate.execute("drop table bookmarks if exists");
		jdbcTemplate.execute("create table bookmarks ("
				+ "id serial, url varchar(255))");
		String[] urls = new String[] { 
				"http://www.w3.org",
				"http://www.abc.org", 
				"http://www.bbb.org",
				"http://www.www.org",
				"http://www.ok.org"};
		int no = 0;
		for (String url : urls) {
			jdbcTemplate.update(
					"INSERT INTO bookmarks(id, url) values (?,?)",
					no, url);
			no++;
		}
	}
	
	public static JdbcTemplate getJdbcTemplate() {
        return context.getBean(JdbcTemplate.class);
    }
	
}
