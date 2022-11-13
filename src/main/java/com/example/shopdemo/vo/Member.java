package com.example.shopdemo.vo;

import java.util.Objects;

public class Member {

	private int id;
	private String firstName;
	private String lastName;
	private String birthday;
	private String phoneNumber;
	
	public Member() {
		super();
	}

	public Member(int id, String firstName, String lastName, String birthday) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthday = birthday;
		
	}
	

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public int hashCode() {
		return Objects.hash(birthday, firstName, id, lastName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Member other = (Member) obj;
		return Objects.equals(birthday, other.birthday) && Objects.equals(firstName, other.firstName) && id == other.id
				&& Objects.equals(lastName, other.lastName);
	}
	
	
	
	
}
