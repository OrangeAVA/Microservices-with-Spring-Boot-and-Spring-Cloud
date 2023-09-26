package com.example.demo.controllers;

import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.DoctorDAO;
import com.example.demo.exceptions.DoctorAlreadyExistsException;
import com.example.demo.pojo.Doctor;

@RestController
public class DoctorController {
	
//	@ExceptionHandler(value = DoctorAlreadyExistsException.class)
//    public String handleDoctorAlreadyExistsException(DoctorAlreadyExistsException blogAlreadyExistsException) {
//        return "Doctor already exists";
//    }
//	
	
	@Autowired
	DoctorDAO doctorDAO;

//	@PostMapping(path = "/doctors", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
//	public Doctor createNewDoctorRecord(@RequestBody Doctor doctor)  throws DoctorAlreadyExistsException{
//		// logic to add record in table will go here
//		Doctor d = null;
//		int added = 0;
//			added = doctorDAO.addDoctor(doctor);
//		if (added == 1)
//			return doctor;
//		return d;
//	}

	@PostMapping(path = "/doctors", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Doctor> createNewDoctorRecord(@RequestBody Doctor doctor)  throws DoctorAlreadyExistsException{
		// logic to add record in table will go here
		int added = 0;
			added = doctorDAO.addDoctor(doctor);
		if (added == 1)
			return new ResponseEntity<Doctor>(doctor,HttpStatusCode.valueOf(201));
	   
		return new ResponseEntity<Doctor>(HttpStatusCode.valueOf(204));
	}

	
	
	@PostMapping(path = "/doctors/form", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public Doctor createNewDoctorRecord_Form(Doctor doctor) {
		// logic to add record in table will go here
		Doctor d = null;
		int added = doctorDAO.addDoctor(doctor);
		if (added == 1)
			return doctor;

		return d;
	}

	@GetMapping("/doctors/{id}")
	public Doctor findDoctorById(@PathVariable int id) {
		// logic to find doctor by id will go here
		Doctor doctor = doctorDAO.findDoctorById(id);
		return doctor;
	}

//	@GetMapping(value="/doctors/specialization/{specialization}")
//	public List<Doctor> findDoctorBySpecialization(@PathVariable(name="specialization") String doctor_specialization) {
//		// logic to find all doctors by specialization will go here
//		List<Doctor> doctors= doctorDAO.findAllDoctorsBySpecialization(doctor_specialization);
//		return doctors;
//	}

//	@GetMapping(value={"/doctors/specialization","/doctors/specialization/{specialization}"})
//	public List<Doctor> findDoctorBySpecialization(@PathVariable(name="specialization",required = false) String doctor_specialization) {
//		// logic to find all doctors by specialization will go here
//		List<Doctor> doctors= doctorDAO.findAllDoctorsBySpecialization(doctor_specialization);
//		return doctors;
//	}

//	@GetMapping(value={"/doctors/specialization","/doctors/specialization/{specialization}"})
//	public List<Doctor> findDoctorBySpecialization(@PathVariable(value="specialization",required = false) String doctor_specialization) {
//		// logic to find all doctors by specialization will go here
//		List<Doctor> doctors= doctorDAO.findAllDoctorsBySpecialization(doctor_specialization);
//		return doctors;
//	}

//	@GetMapping(value={"/doctors/specialization","/doctors/specialization/{specialization}"})
//	public List<Doctor> findDoctorBySpecialization(@PathVariable(name="specialization",required = false,value="specialization") String doctor_specialization) {
//		// logic to find all doctors by specialization will go here
//		List<Doctor> doctors= doctorDAO.findAllDoctorsBySpecialization(doctor_specialization);
//		return doctors;
//	}

	@GetMapping(value = { "/doctors/specialization", "/doctors/specialization/{specialization}" })
	public List<Doctor> findDoctorBySpecialization(
			@PathVariable(name = "specialization") Optional<String> doctor_specialization) {
		List<Doctor> doctors = new ArrayList<>();
		if (doctor_specialization.isPresent()) {
			// logic to find all doctors by specialization will go here
			doctors = doctorDAO.findAllDoctorsBySpecialization(doctor_specialization.get());
			return doctors;
		}
		// logic to find all doctors as specialization is not present will go here
		doctors = doctorDAO.findAllDoctors();
		return doctors;
	}

	@GetMapping(value = "/doctors")
	public List<Doctor> findAllDoctors() {
		// logic to find all doctors will go here
		List<Doctor> doctors = new ArrayList<>();
		doctors = doctorDAO.findAllDoctors();
		return doctors;
	}

	@PutMapping("/doctors")
	public Doctor modifyDoctorInfo(@RequestBody Doctor doctor) {
		// logic to update the doctor's information if the id exists will go here
		// otherwise we will create a new record in DB
		boolean isUpdated = false;
		Doctor doctor_found = doctorDAO.findDoctorById(doctor.getDoctorId());
		if (doctor_found != null) {
			isUpdated = doctorDAO.updateDoctor(doctor);
			if (isUpdated) {
				doctor_found = doctorDAO.findDoctorById(doctor.getDoctorId());
			}
		} else {
			int added = doctorDAO.addDoctor(doctor);
			if (added > 0) {
				return doctor_found = doctor;
			}
		}
		return doctor_found;
	}

//	@PutMapping("/doctors/{id}/{specialization}")
//	public Doctor modifyDoctorInfo(@PathVariable int id, @PathVariable String specialization) {
//		// logic to update the doctor's information will go here
//		return new Doctor(id, "abc", specialization);
//	}

	@PatchMapping("/doctors/{id}/{specialization}")
	public Doctor modifyDoctorInfo(@PathVariable int id, @PathVariable String specialization) {
		// logic to update the doctor's information if the id exists will go here
		boolean isUpdated = doctorDAO.updateSpecialization(id, specialization);
		if (isUpdated) {
			return doctorDAO.findDoctorById(id);
		}
		return null;
	}

	@DeleteMapping("/doctors/{id}")
	public Doctor deleteDoctorById(@PathVariable int id) {
		// logic to delete the doctor's information if the id exists will go here
		Doctor doctor = doctorDAO.findDoctorById(id);
		if (doctor != null) {
			doctorDAO.deleteDoctorById(id);
			return doctor;
		}
		return null;
	}

}
