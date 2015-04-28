package com.bookmark.paginate;

import java.util.ArrayList;
import java.util.List;

public class Page<E> {
	
	private int pageNumber;
	private int pageAvailable;
	private List<E> pageItems = new ArrayList<E>();
	private int pageSize;
	
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	
	public void setPageAvaliable(int pageAvailable) {
		this.pageAvailable = pageAvailable;
	}
	
	public void setPageItems(List<E> pageItems) {
		this.pageItems = pageItems;
	}
	
	public int getPageNumber() {
		return pageNumber;
	}
	
	public int getPageAvailable() {
		return pageAvailable;
	}
	
	public List<E> getPageItems() {
		return pageItems;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public int getPageSize() {
		return pageSize;
	}
}
