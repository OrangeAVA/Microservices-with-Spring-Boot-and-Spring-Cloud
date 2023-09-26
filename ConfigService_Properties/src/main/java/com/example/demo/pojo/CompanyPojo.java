package com.example.demo.pojo;

public class CompanyPojo {
	
	private String name;
	private int locationCode;
	
	public CompanyPojo() {
		// TODO Auto-generated constructor stub
	}

	public CompanyPojo(String name, int locationCode) {
		super();
		this.name = name;
		this.locationCode = locationCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLocationCode() {
		return locationCode;
	}

	public void setLocationCode(int locationCode) {
		this.locationCode = locationCode;
	}

	@Override
	public String toString() {
		return "MyPojo [name=" + name + ", locationCode=" + locationCode + "]";
	}
	
	

}
