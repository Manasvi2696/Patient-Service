package org.healthcare.patient.exception;

public class PatientNotFoundException extends Exception{
	
	public PatientNotFoundException(int id) {
		super("Patient with id :"+id+" Not found.");
	}

}
