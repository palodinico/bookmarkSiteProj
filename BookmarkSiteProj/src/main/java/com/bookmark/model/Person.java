package com.bookmark.model;

public class Person {
	
	private final String name;
	private final long id;

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

}
