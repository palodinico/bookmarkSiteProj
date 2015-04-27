package com.bookmark.model;

import java.net.MalformedURLException;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.Test;

import static org.junit.Assert.*;

public class BookmarkTest {
	
	@Test
	public void canConstructABookmarkWithUrlAndId() throws MalformedURLException {
		AtomicLong counter = new AtomicLong();
		
		Bookmark bookmark= new Bookmark("http://w3.ibm.com/jp/", counter.getAndIncrement());
		assertEquals("http://w3.ibm.com/jp/", bookmark.getUrl());
		assertEquals(0, bookmark.getId());
		
		bookmark= new Bookmark("http://www.google.co.jp/", counter.getAndIncrement());
		assertEquals("http://www.google.co.jp/", bookmark.getUrl());
		assertEquals(1, bookmark.getId());
		assertEquals("www.google.co.jp", bookmark.getUrlObj().getHost());
	}
	
	@Test(expected=MalformedURLException.class)
	public void throwExceptionWithInvalidUrl() throws MalformedURLException {
		Bookmark bookmrk= new Bookmark("httpsssss://w3.ibm.com/jp/", 1);		
	}
}
