package com.bookmark.model;

import java.net.MalformedURLException;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.Test;

import static org.junit.Assert.*;

public class BookmarkTest {
	
	@Test
	public void canConstructABookmarkWithUrlAndId() throws MalformedURLException {
		AtomicLong counter = new AtomicLong();
		
		Bookmark bookmrk= new Bookmark("http://w3.ibm.com/jp/", counter.getAndIncrement());
		assertEquals("http://w3.ibm.com/jp/", bookmrk.getUrl());
		assertEquals(0, bookmrk.getId());
		
		bookmrk= new Bookmark("http://www.google.co.jp/", counter.getAndIncrement());
		assertEquals("http://www.google.co.jp/", bookmrk.getUrl());
		assertEquals(1, bookmrk.getId());
		assertEquals("www.google.co.jp", bookmrk.getUrlObj().getHost());
	}
	
	@Test(expected=MalformedURLException.class)
	public void throwExceptionWithInvalidUrl() throws MalformedURLException {
		Bookmark bookmrk= new Bookmark("httpsssss://w3.ibm.com/jp/", 1);		
	}
}
