package com.example.demo.dao;

import java.sql.ResultSet;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.demo.exceptions.DoctorAlreadyExistsException;
import com.example.demo.pojo.Doctor;

@Repository
public class DoctorDAOImpl implements DoctorDAO {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public int addDoctor(Doctor doctor) {
		int added = 0;
		String INSERT_DOCTOR = "insert into doctor_producer values(?,?,?)";
		try {
			added = jdbcTemplate.update(INSERT_DOCTOR, doctor.getDoctorId(), doctor.getDoctorName(),
					doctor.getSpecialization());
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			throw new DoctorAlreadyExistsException("RECORD EXISTS");
		}
		return added;
	}

	@Override
	public boolean updateDoctor(Doctor doctor) {
		boolean isUpdated = false;
		String UPDATE_DOCTOR = "update doctor_producer set doctorName=?, specialization=? where doctorId=?";
		int updated = jdbcTemplate.update(UPDATE_DOCTOR, doctor.getDoctorName(), doctor.getSpecialization(),
				doctor.getDoctorId());
		if (updated > 0) {
			isUpdated = true;
		}
		return isUpdated;
	}

	@Override
	public boolean updateSpecialization(int doctorId, String specialization) {
		boolean isUpdated = false;
		String UPDATE_DOCTOR = "update doctor_producer set specialization=? where doctorId=?";
		int updated = jdbcTemplate.update(UPDATE_DOCTOR, specialization, doctorId);
		if (updated > 0) {
			isUpdated = true;
		}
		return isUpdated;
	}

	@Override
	public int deleteDoctorById(int doctorId) {
		int deleted = 0;
		String DELETE_DOCTOR = "delete from doctor_producer where doctorId=?";
		deleted = jdbcTemplate.update(DELETE_DOCTOR, doctorId);
		return deleted;
	}

	@Override
	public Doctor findDoctorById(int doctorId) {
		Doctor doctor = null;
		String FINDDOCTORBYID = "select * from doctor_producer where doctorId=?";
		doctor = jdbcTemplate.queryForObject(FINDDOCTORBYID, (rs, rowNum) -> {
			Doctor d = new Doctor();
			d.setDoctorId(rs.getInt(1));
			d.setDoctorName(rs.getString(2));
			d.setSpecialization(rs.getString(3));
			return d;
		}, doctorId);
		return doctor;
	}

	@Override
	public List<Doctor> findAllDoctors() {
		List<Doctor> doctors = new ArrayList<>();
		String FINDDOCTORBYID = "select * from doctor_producer";
		doctors = jdbcTemplate.query(FINDDOCTORBYID, (rs, rowNum) -> {
			Doctor d = new Doctor();
			d.setDoctorId(rs.getInt(1));
			d.setDoctorName(rs.getString(2));
			d.setSpecialization(rs.getString(3));
			return d;
		});
		return doctors;
	}

	@Override
	public List<Doctor> findAllDoctorsBySpecialization(String specialization) {
		List<Doctor> doctors = new ArrayList<>();
		if (specialization != null) {
			String FIND_DOCTOR_BY_ID = "select * from doctor_producer where specialization=?";

			doctors = jdbcTemplate.query(FIND_DOCTOR_BY_ID, (rs, rowNum) -> {
				Doctor d = new Doctor();
				d.setDoctorId(rs.getInt(1));
				d.setDoctorName(rs.getString(2));
				d.setSpecialization(rs.getString(3));
				return d;
			}, specialization);
			return doctors;
		}
		doctors = findAllDoctors();
		return doctors;
	}

}
