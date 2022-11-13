package com.example.shopdemo.vo;

import java.util.ArrayList;
import java.util.List;

public class Paging<T> {

	private int current = 1;
	private int pageCount = 10;
	private int total = 0;
	private List<T> list = new ArrayList<>();
	
	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getCurrent() {
		return current;
	}

	public void setCurrent(int current) {
		this.current = current;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}
	
	
	
}
