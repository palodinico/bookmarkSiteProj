package com.bookmark.model;

import java.util.ArrayList;

public class Person {
	
	private final String name;
	private final long id;
	private ArrayList<Bookmark> bookmarks = new ArrayList<Bookmark>();

	public Person(String name, long id) {
		this.name = name;
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public long getId() {
		return id;
	}

	public void addBookmark(Bookmark bookmark) {
		bookmarks.add(bookmark);
	}

	public ArrayList<Bookmark> getBookmarks() {
		return bookmarks;
	}

	public Bookmark getBookmark(int index) {
		return bookmarks.get(index);
	}

}
