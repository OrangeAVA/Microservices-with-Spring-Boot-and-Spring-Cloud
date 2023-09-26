package com.example.demo.controllers;

import java.util.ArrayList;


import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.demo.feign.Hospital_Doctor_Feign;
import com.example.demo.pojos.Doctor;
import com.example.demo.pojos.Hospital;
import com.example.demo.repo.HospitalRepository;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;


@RestController
public class Find_Doctors_in_Hospital_Controller {

	@Autowired
	HospitalRepository repo;

//  Without iterservice communcation
//	@GetMapping("/hospitals/{hospitalId}")
//	ResponseEntity<Hospital> findAllDoctorsInHospitals(@PathVariable int hospitalId) {
//		List<Doctor> doctors = new ArrayList<>();
//	    Hospital hospital = repo.findHospitalById(hospitalId);
//		if (hospital!=null) {
//			return new ResponseEntity<Hospital>(hospital, HttpStatus.OK);
//		}
//		return new ResponseEntity<Hospital>(HttpStatus.NO_CONTENT);
//	}

	@GetMapping("/hospitals/{hospitalId}")
	ResponseEntity<Hospital> findAllDoctorsInHospitals(@PathVariable int hospitalId) {
		List<Doctor> doctors = new ArrayList<>();
		Hospital hospital = repo.findHospitalById(hospitalId);
		if (hospital != null) {
			List<Integer> doctor_ids = repo.findDoctorIds(hospitalId);
			RestTemplate restTemplate = new RestTemplate();
			for (int i = 0; i < doctor_ids.size(); i++) {

				ResponseEntity<Doctor> entity = restTemplate.getForEntity("http://localhost:8085/doctors/{doctorId}",
						Doctor.class, doctor_ids.get(i));
				if (entity.getStatusCode().equals(HttpStatus.OK)) {
					doctors.add(entity.getBody());
				}
			}

			hospital.setDoctors(doctors);

			return new ResponseEntity<Hospital>(hospital, HttpStatus.OK);

		}
		return new ResponseEntity<Hospital>(HttpStatus.NO_CONTENT);
	}

	@Autowired
	RestTemplate restTemplate;

	@GetMapping("/hospitals-balanced/{hospitalId}")
	ResponseEntity<Hospital> findAllDoctorsInHospitals_loadBalanced(@PathVariable int hospitalId) {
		List<Doctor> doctors = new ArrayList<>();
		Hospital hospital = repo.findHospitalById(hospitalId);
		if (hospital != null) {
			List<Integer> doctor_ids = repo.findDoctorIds(hospitalId);
			for (int i = 0; i < doctor_ids.size(); i++) {

				ResponseEntity<Doctor> entity = restTemplate.getForEntity(
						"http://doctor-find-by-id-service/doctors/{doctorId}", Doctor.class, doctor_ids.get(i));
				if (entity.getStatusCode().equals(HttpStatus.OK)) {
					doctors.add(entity.getBody());

				}
			}
			hospital.setDoctors(doctors);
			return new ResponseEntity<Hospital>(hospital, HttpStatus.OK);
		}
		return new ResponseEntity<Hospital>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/hospitals-balanced-gateway/{hospitalId}")
	ResponseEntity<Hospital> findAllDoctorsInHospitals_loadBalanced_gateway(@PathVariable int hospitalId,
			@RequestHeader(name = "sort", defaultValue = "all") String sort) {
		List<Doctor> doctors = new ArrayList<>();
		Hospital hospital = repo.findHospitalById(hospitalId);
		if (hospital != null) {
			List<Integer> doctor_ids = repo.findDoctorIds(hospitalId);
			for (int i = 0; i < doctor_ids.size(); i++) {
//			   ResponseEntity<Doctor> entity =	restTemplate.getForEntity("http://doctor-find-by-id-service/doctors/{doctorId}",
				System.out.println("called");
				ResponseEntity<Doctor> entity = restTemplate
						.getForEntity("http://api-gateway-service/doctors/{doctorId}", Doctor.class, doctor_ids.get(i));
				if (entity.getStatusCode().equals(HttpStatus.OK)) {
					doctors.add(entity.getBody());

				}
			}
			hospital.setDoctors(doctors);
			return new ResponseEntity<Hospital>(hospital, HttpStatus.OK);
		}
		return new ResponseEntity<Hospital>(HttpStatus.NO_CONTENT);
	}

	@Autowired
	Hospital_Doctor_Feign feign_client;

	@Autowired
	Environment env;

	@GetMapping("/hospitals-feign/{hospitalId}")
	ResponseEntity<Hospital> findAllDoctorsInHospitals_feign(@PathVariable int hospitalId) {
		List<Doctor> doctors = new ArrayList<>();
		Hospital hospital = repo.findHospitalById(hospitalId);
		if (hospital != null) {
			List<Integer> doctor_ids = repo.findDoctorIds(hospitalId);

			for (int i = 0; i < doctor_ids.size(); i++) {

				ResponseEntity<Doctor> entity = feign_client.searchDoctorById(doctor_ids.get(i));
				if (entity.getStatusCode().equals(HttpStatus.OK)) {
					doctors.add(entity.getBody());
					System.out.println(env.getProperty("loadbalancer.client.name"));
				}
			}

			hospital.setDoctors(doctors);

			return new ResponseEntity<Hospital>(hospital, HttpStatus.OK);

		}
		return new ResponseEntity<Hospital>(HttpStatus.NO_CONTENT);
	}

	@CircuitBreaker(name = "circuit-breaker-for-doctor", fallbackMethod = "getDoctorFallback")
	@GetMapping("/hospitals-circuit-breaker/{hospitalId}")
	ResponseEntity<Hospital> findAllDoctorsInHospitals_cricuitBreaker(@PathVariable int hospitalId) {
		List<Doctor> doctors = new ArrayList<>();
		Hospital hospital = repo.findHospitalById(hospitalId);
		if (hospital != null) {
			List<Integer> doctor_ids = repo.findDoctorIds(hospitalId);
			for (int i = 0; i < doctor_ids.size(); i++) {
				ResponseEntity<Doctor> entity = restTemplate.getForEntity(
						"http://doctor-find-by-id-service/doctors/{doctorId}", Doctor.class, doctor_ids.get(i));
				if (entity.getStatusCode().equals(HttpStatus.OK)) {
					doctors.add(entity.getBody());

				}
			}
			hospital.setDoctors(doctors);
			return new ResponseEntity<Hospital>(hospital, HttpStatus.OK);
		}
		return new ResponseEntity<Hospital>(HttpStatus.NO_CONTENT);
	}

	public ResponseEntity<Hospital> getDoctorFallback(int hospitalId, Exception e) {

		Hospital hospital = repo.findHospitalById(hospitalId);
		if (hospital != null) {
			return new ResponseEntity<Hospital>(hospital, HttpStatus.OK);
		}
		return new ResponseEntity<Hospital>(HttpStatus.NO_CONTENT);
	}

	@Retry(name = "retry-for-doctor", fallbackMethod = "getDoctorFallback")
	@GetMapping("/hospitals-retry/{hospitalId}")
	ResponseEntity<Hospital> findAllDoctorsInHospitals_retry(@PathVariable int hospitalId) {
		System.out.println("Method called");
		List<Doctor> doctors = new ArrayList<>();
		Hospital hospital = repo.findHospitalById(hospitalId);
		if (hospital != null) {
			List<Integer> doctor_ids = repo.findDoctorIds(hospitalId);
			for (int i = 0; i < doctor_ids.size(); i++) {
				ResponseEntity<Doctor> entity = restTemplate.getForEntity(
						"http://doctor-find-by-id-service/doctors/{doctorId}", Doctor.class, doctor_ids.get(i));
				if (entity.getStatusCode().equals(HttpStatus.OK)) {
					doctors.add(entity.getBody());

				}
			}
			hospital.setDoctors(doctors);
			return new ResponseEntity<Hospital>(hospital, HttpStatus.OK);
		}
		return new ResponseEntity<Hospital>(HttpStatus.NO_CONTENT);
	}

	@RateLimiter(name = "rate-limiter-for-doctor", fallbackMethod = "getDoctorFallback_rateLimiter")
	@GetMapping("/hospitals-rate-limiter/{hospitalId}")
	ResponseEntity<Hospital> findAllDoctorsInHospitals_rate_limiter(@PathVariable int hospitalId) {
		System.out.println("Method called");
		List<Doctor> doctors = new ArrayList<>();
		Hospital hospital = repo.findHospitalById(hospitalId);
		if (hospital != null) {
			List<Integer> doctor_ids = repo.findDoctorIds(hospitalId);
			for (int i = 0; i < doctor_ids.size(); i++) {
				ResponseEntity<Doctor> entity = restTemplate.getForEntity(
						"http://doctor-find-by-id-service/doctors/{doctorId}", Doctor.class, doctor_ids.get(i));
				if (entity.getStatusCode().equals(HttpStatus.OK)) {
					doctors.add(entity.getBody());

				}
			}
			hospital.setDoctors(doctors);
			return new ResponseEntity<Hospital>(hospital, HttpStatus.OK);
		}
		return new ResponseEntity<Hospital>(HttpStatus.NO_CONTENT);
	}

	public ResponseEntity<Hospital> getDoctorFallback_rateLimiter(int hospitalId, Exception e) {

		System.out.println(e.getMessage());
		return new ResponseEntity<Hospital>(HttpStatus.TOO_MANY_REQUESTS);
	}

	@Bulkhead(name = "bulkhead-for-doctor", fallbackMethod = "getDoctorFallback_rateLimiter")
	@GetMapping("/hospitals-bulkhead/{hospitalId}")
	ResponseEntity<Hospital> findAllDoctorsInHospitals_bulkhead(@PathVariable int hospitalId) {
		System.out.println("Method called ");
		List<Doctor> doctors = new ArrayList<>();
		Hospital hospital = repo.findHospitalById(hospitalId);
		if (hospital != null) {
			List<Integer> doctor_ids = repo.findDoctorIds(hospitalId);
			for (int i = 0; i < doctor_ids.size(); i++) {
				ResponseEntity<Doctor> entity = restTemplate.getForEntity(
						"http://doctor-find-by-id-service/doctors/{doctorId}", Doctor.class, doctor_ids.get(i));
				if (entity.getStatusCode().equals(HttpStatus.OK)) {
					doctors.add(entity.getBody());

				}
			}
			hospital.setDoctors(doctors);
			return new ResponseEntity<Hospital>(hospital, HttpStatus.OK);
		}
		return new ResponseEntity<Hospital>(HttpStatus.NO_CONTENT);
	}

	@TimeLimiter(name = "timelimiter-for-doctor", fallbackMethod = "getDoctorFallback_timeLimiter")
	@GetMapping("/hospitals-timelimiter/{hospitalId}")
	CompletableFuture<ResponseEntity<Hospital>> findAllDoctorsInHospitals_timelimiter(@PathVariable int hospitalId) {

		return CompletableFuture.supplyAsync(() -> {
			return this.getDetails(hospitalId);
		});

	}

	public ResponseEntity<Hospital> getDetails(int hospitalId) {
		System.out.println("Method called ");
		List<Doctor> doctors = new ArrayList<>();
		Hospital hospital = repo.findHospitalById(hospitalId);
		if (hospital != null) {
			List<Integer> doctor_ids = repo.findDoctorIds(hospitalId);
			for (int i = 0; i < doctor_ids.size(); i++) {
				ResponseEntity<Doctor> entity = restTemplate.getForEntity(
						"http://doctor-find-by-id-service/doctors/{doctorId}", Doctor.class, doctor_ids.get(i));
				if (entity.getStatusCode().equals(HttpStatus.OK)) {
					doctors.add(entity.getBody());

				}
			}
			hospital.setDoctors(doctors);
			new ResponseEntity<Hospital>(hospital, HttpStatus.OK);
		}
		return new ResponseEntity<Hospital>(HttpStatus.NO_CONTENT);
	}

	public CompletableFuture<ResponseEntity<Hospital>> getDoctorFallback_timeLimiter(int hospitalId, Exception e) {

		System.out.println(e.getMessage());
		return CompletableFuture.supplyAsync(() -> new ResponseEntity<Hospital>(HttpStatus.REQUEST_TIMEOUT));
	}

}
