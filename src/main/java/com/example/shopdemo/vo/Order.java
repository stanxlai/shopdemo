package com.example.shopdemo.vo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Order {
    private String date = "";
    private List<OrderItem> orderItems = new ArrayList<>();
    private int id;
    private int memberId;
    

    public Order(String date, int id, int memberId) {
    	super();
    	this.id = id;
    	this.date = date;
    	this.memberId = memberId;
    }
    
    public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		return id == other.id;
	}

	
	
}