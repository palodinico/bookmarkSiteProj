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
		int noMax = 1000;
		for (int no = 0; no < noMax; no++) {
			jdbcTemplate.update(
					"INSERT INTO bookmarks(id, url) values (?,?)",
					no, "http://www.w" + no + ".com");
		}
	}
	
	public static JdbcTemplate getJdbcTemplate() {
        return context.getBean(JdbcTemplate.class);
    }
	
}
