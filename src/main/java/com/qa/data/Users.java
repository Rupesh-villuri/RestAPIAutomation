package com.qa.data;

//POJO - Plain Old Java Object
public class Users {

	String name;
	String job;
	String id;
	String createdAt;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	//Creating the default constructor of this class
	public Users(){
		
	}
	
	//one more constructor i will create so that i can create data for that.
	//so that i can call this constructor and provide data directly in my test
	public Users(String name,String job) {
		this.name=name;
		this.job=job;
	}

	//getters and setters method.
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	} 
}




















