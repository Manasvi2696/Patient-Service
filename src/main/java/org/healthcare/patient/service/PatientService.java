package org.healthcare.patient.service;

import java.util.List;

import org.healthcare.patient.entity.Patient;
import org.healthcare.patient.exception.PatientNotFoundException;

public interface PatientService {

	public List<Patient> findAll();
	
	public Patient findById(int id) throws PatientNotFoundException;
	
	public Patient registerNewPatient(Patient patient);
	
	public void deletePatient(int id);
}
