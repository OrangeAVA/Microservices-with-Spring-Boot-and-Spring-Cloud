package com.example.demo.repo;

import java.sql.ResultSet;

import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.demo.pojos.Hospital;

@Repository
public class HospitalRepository {

	@Autowired
	JdbcTemplate jdbcTemplate;

	public List<Integer> findDoctorIds(int hospitalId) {

		String query = "select doctorId from hospital h inner join "
				+ "doctor_hospital_mapping ON h.hospital_registration_id=doctor_hospital_mapping.hospitalId "
				+ "where h.hospital_registration_id=?";
		return jdbcTemplate.queryForList(query, Integer.class, hospitalId);

	}

	public Hospital findHospitalById(int hospitalId){
		Hospital hospital=null;
	
		String Find_HospitalBy_Id="select * from hospital where hospital_registration_id=?";
		try {
		 hospital = jdbcTemplate.queryForObject(Find_HospitalBy_Id,  new RowMapper<Hospital>() {

			@Override
			public Hospital mapRow(ResultSet rs, int rowNum) throws SQLException {
				// TODO Auto-generated method stub
				Hospital hospital=new Hospital();
				hospital.setHospitalRegistrationId(rs.getInt(1));
				hospital.setAddress(rs.getString(2));
				hospital.setHospitalName(rs.getString(3));
				return hospital;
			}
		},hospitalId);
		}
		catch(Exception e) {
            System.out.println(e.getMessage());
		}
		return hospital;
	}

}
