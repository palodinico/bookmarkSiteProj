package com.bookmark.model;

import java.net.MalformedURLException;
import java.net.URL;

public final class Bookmark {
	private final String url;
	private final long id;
	private final URL urlObj;

	public Bookmark(String url, long id) throws MalformedURLException {
		urlObj = new URL(url);
		this.url = url;
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public long getId() {
		return id;
	}
	
	public URL getUrlObj() {
		return urlObj;
	}
}
