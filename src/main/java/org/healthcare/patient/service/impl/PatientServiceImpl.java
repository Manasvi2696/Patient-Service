package org.healthcare.patient.service.impl;

import java.util.List;
import java.util.Optional;

import org.healthcare.patient.dao.PatientDao;
import org.healthcare.patient.entity.Patient;
import org.healthcare.patient.exception.PatientNotFoundException;
import org.healthcare.patient.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientServiceImpl implements PatientService{
	
	@Autowired
	private PatientDao dao;

	@Override
	public List<Patient> findAll() {
		return dao.findAll();
	}

	@Override
	public Patient findById(int id) throws PatientNotFoundException {
		 Optional<Patient> o = dao.findById(id);
		 if(o.isPresent()) {
			 return o.get();
		 }
		 throw new PatientNotFoundException(id);
	}

	@Override
	public Patient registerNewPatient(Patient patient) {
		return dao.save(patient);
	}

	@Override
	public void deletePatient(int id) {
		dao.deleteById(id);
	}
	

}
