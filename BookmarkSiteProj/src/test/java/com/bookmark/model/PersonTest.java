package com.bookmark.model;

import java.net.MalformedURLException;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.Test;

import static org.junit.Assert.*;

public class PersonTest {
    @Test
    public void canConstructAPersonWithANameAndId() throws MalformedURLException {
    	AtomicLong counter = new AtomicLong();
    	
        Person person = new Person("Larry", counter.getAndIncrement());
        assertEquals("Larry", person.getName());
        assertEquals(0, person.getId());
        
        person = new Person("Bobbie", counter.getAndIncrement());
        assertEquals("Bobbie", person.getName());
        assertEquals(1, person.getId());
        
        person.addBookmark(new Bookmark("http://www.yahoo.co.jp", 1));
        person.addBookmark(new Bookmark("http://www.sega.co.jp", 2));
        person.addBookmark(new Bookmark("http://www.yahoo.com", 3));
        assertEquals(3, person.getBookmarks().size());
        assertEquals(2, person.getBookmark(1).getId());
        assertEquals("http://www.yahoo.com", person.getBookmark(2).getUrl());
    }
}
