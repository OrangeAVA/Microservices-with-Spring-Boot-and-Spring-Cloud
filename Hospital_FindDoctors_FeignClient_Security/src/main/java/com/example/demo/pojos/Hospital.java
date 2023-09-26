package com.example.demo.pojos;

import java.util.List;

public class Hospital {

	private int hospitalRegistrationId;
	private String hospitalName;
	private String address;
	
	private List<Doctor>doctors;
	

	public Hospital() {
		// TODO Auto-generated constructor stub
	}

	public Hospital(int hospitalRegistrationId, String hospitalName, String address) {
		super();
		this.hospitalRegistrationId = hospitalRegistrationId;
		this.hospitalName = hospitalName;
		this.address = address;
	}

	public Hospital(int hospitalRegistrationId, String hospitalName, String address, List<Doctor> doctors) {
		super();
		this.hospitalRegistrationId = hospitalRegistrationId;
		this.hospitalName = hospitalName;
		this.address = address;
		this.doctors = doctors;
	}

	public int getHospitalRegistrationId() {
		return hospitalRegistrationId;
	}

	public void setHospitalRegistrationId(int hospitalRegistrationId) {
		this.hospitalRegistrationId = hospitalRegistrationId;
	}

	public String getHospitalName() {
		return hospitalName;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<Doctor> getDoctors() {
		return doctors;
	}

	public void setDoctors(List<Doctor> doctors) {
		this.doctors = doctors;
	}

	@Override
	public String toString() {
		return "Hospital [hospitalRegistrationId=" + hospitalRegistrationId + ", hospitalName=" + hospitalName
				+ ", address=" + address + ", doctors=" + doctors + "]";
	}
	
	
	
	
}
