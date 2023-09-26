package com.example.demo.dao;

import java.util.List;

import com.example.demo.pojo.Doctor;

public interface DoctorDAO {
	int addDoctor(Doctor doctor );
	boolean updateDoctor(Doctor doctor);
	boolean updateSpecialization(int doctorId,String specialization);
	int deleteDoctorById(int doctorId);
	Doctor findDoctorById(int doctorId);
	List<Doctor>findAllDoctors();
	List<Doctor>findAllDoctorsBySpecialization(String specialization);
}
