package com.example.demo.controllers;

import java.util.ArrayList;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.example.demo.feign.Hospital_Doctor_Feign;
import com.example.demo.pojos.Doctor;
import com.example.demo.pojos.Hospital;
import com.example.demo.pojos.MyToken;
import com.example.demo.repo.HospitalRepository;

@RestController
public class Find_Doctors_in_Hospital_Controller {

	@Autowired
	RestTemplate restTemplate1;

	@Autowired
	HospitalRepository repo;

	@GetMapping("/hospitals-balanced-gateway/{hospitalId}")
	ResponseEntity<Hospital> findAllDoctorsInHospitals_loadBalanced_gateway(@PathVariable int hospitalId,
			@RequestHeader(name = "sort", defaultValue = "all") String sort) {
		List<Doctor> doctors = new ArrayList<>();
		Hospital hospital = repo.findHospitalById(hospitalId);
		if (hospital != null) {

			MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
			map.add("username", "client1");
			map.add("password", "client1");
			map.add("grant_type", "client_credentials"); //
			map.add("client_secret", "e3YNqtUjjG87K5DklLE1n6AdS6BsxYKJ");
			map.add("client_id", "microservice_client");
			map.add("scope", "openid");
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			HttpEntity<MultiValueMap<String, String>> requestBodyFormUrlEncoded = new HttpEntity<>(map, headers);
			String access_token_url = "http://localhost:8080/realms/security_demo/protocol/openid-connect/token";

			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<MyToken> response_token = restTemplate.exchange(access_token_url, HttpMethod.POST,
					requestBodyFormUrlEncoded, MyToken.class);

			System.out.println(response_token.getBody().getAccess_token());

			HttpHeaders headers_token = new HttpHeaders();
			headers_token.setBearerAuth(response_token.getBody().getAccess_token());
			HttpEntity<MultiValueMap<String, String>> reqest_doctor_header_entity = new HttpEntity<>(headers_token);

			List<Integer> doctor_ids = repo.findDoctorIds(hospitalId);
			System.out.println(doctor_ids.size());
			for (int i = 0; i < doctor_ids.size(); i++) {
				ResponseEntity<Doctor> entity = restTemplate1.exchange("http://api-gateway-service/doctors/{doctorId}",
						HttpMethod.GET, reqest_doctor_header_entity, Doctor.class, doctor_ids.get(i));
				if (entity.getStatusCode().equals(HttpStatus.OK)) {
					doctors.add(entity.getBody());
				}
			}
		}
		hospital.setDoctors(doctors);
		return new ResponseEntity<Hospital>(hospital, HttpStatus.OK);

	}

	@Autowired
	Hospital_Doctor_Feign feign_client;

	@GetMapping("/hospitals-feign/{hospitalId}")
	ResponseEntity<Hospital> findAllDoctorsInHospitals_feign(@PathVariable int hospitalId) {

		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("username", "client1");
		map.add("password", "client1");
		map.add("grant_type", "client_credentials"); //
		map.add("client_secret", "e3YNqtUjjG87K5DklLE1n6AdS6BsxYKJ");
		map.add("client_id", "microservice_client");
		map.add("scope", "openid");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		HttpEntity<MultiValueMap<String, String>> requestBodyFormUrlEncoded = new HttpEntity<>(map, headers);
		String access_token_url = "http://localhost:8080/realms/security_demo/protocol/openid-connect/token";

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<MyToken> response_token = restTemplate.exchange(access_token_url, HttpMethod.POST,
				requestBodyFormUrlEncoded, MyToken.class);

		List<Doctor> doctors = new ArrayList<>();
		Hospital hospital = repo.findHospitalById(hospitalId);
		if (hospital != null) {
			List<Integer> doctor_ids = repo.findDoctorIds(hospitalId);

			for (int i = 0; i < doctor_ids.size(); i++) {
				ResponseEntity<Doctor> entity = feign_client.searchDoctorById(
						String.format("%s %s", "Bearer", response_token.getBody().getAccess_token()),
						doctor_ids.get(i));
				if (entity.getStatusCode().equals(HttpStatus.OK)) {
					doctors.add(entity.getBody());
				}
			}

			hospital.setDoctors(doctors);

			return new ResponseEntity<Hospital>(hospital, HttpStatus.OK);

		}
		return new ResponseEntity<Hospital>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/hospitals-feign-interceptor/{hospitalId}")
	ResponseEntity<Hospital> findAllDoctorsInHospitals_feign_interceptor(@PathVariable int hospitalId) {
		List<Doctor> doctors = new ArrayList<>();
		Hospital hospital = repo.findHospitalById(hospitalId);
		if (hospital != null) {
			List<Integer> doctor_ids = repo.findDoctorIds(hospitalId);

			for (int i = 0; i < doctor_ids.size(); i++) {
				ResponseEntity<Doctor> entity = feign_client.searchDoctorById(doctor_ids.get(i));
				if (entity.getStatusCode().equals(HttpStatus.OK)) {
					doctors.add(entity.getBody());
				}
			}

			hospital.setDoctors(doctors);

			return new ResponseEntity<Hospital>(hospital, HttpStatus.OK);

		}
		return new ResponseEntity<Hospital>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/hospitals-feign-relay/{hospitalId}")
	ResponseEntity<Hospital> findAllDoctorsInHospitals_feign_relay(@RequestHeader(value = "authorization", required = true) String authorizationHeader,@PathVariable int hospitalId) {

		System.out.println("authorization header:-"+authorizationHeader);
		List<Doctor> doctors = new ArrayList<>();
		Hospital hospital = repo.findHospitalById(hospitalId);
		if (hospital != null) {
			List<Integer> doctor_ids = repo.findDoctorIds(hospitalId);

			for (int i = 0; i < doctor_ids.size(); i++) {
				ResponseEntity<Doctor> entity = feign_client.searchDoctorById(authorizationHeader,doctor_ids.get(i));
				if (entity.getStatusCode().equals(HttpStatus.OK)) {
					doctors.add(entity.getBody());
				}
			}

			hospital.setDoctors(doctors);

			return new ResponseEntity<Hospital>(hospital, HttpStatus.OK);

		}
		return new ResponseEntity<Hospital>(HttpStatus.NO_CONTENT);
	}

}
