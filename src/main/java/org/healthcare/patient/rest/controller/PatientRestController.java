package org.healthcare.patient.rest.controller;

import java.util.List;

import org.healthcare.appointment.dto.Appointment;
import org.healthcare.patient.entity.Patient;
import org.healthcare.patient.exception.PatientNotFoundException;
import org.healthcare.patient.openfeign.OpenfeignClient;
import org.healthcare.patient.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@RestController
@RequestMapping("/api/patient")
public class PatientRestController {
	
	@Autowired
	private PatientService service;
	@Value("${app.appointment.url}")
	private String appointmentUrl;
	@Autowired
	private OpenfeignClient client;
	
	@ResponseStatus(code=HttpStatus.OK)
	@GetMapping
	public List<Patient> findAll(){
		return service.findAll();
	}
	
	@ResponseStatus(code=HttpStatus.OK)
	@GetMapping("/{id}")
	public Patient findById(@PathVariable int id) throws PatientNotFoundException {
		return service.findById(id);
	}
	
	@ResponseStatus(code=HttpStatus.CREATED)
	@PostMapping
	public Patient registerNewPatient(@RequestBody Patient patient) {
		return service.registerNewPatient(patient);
	}
	
	@ResponseStatus(code=HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void deletePatient(@PathVariable int id) {
		service.deletePatient(id);
	}
	
//	1. Fetch list of all appointments for one Patient
	@GetMapping(value="/patientAppointments" , params="patientId")
	public List<Appointment> getAppointmentsForOnePatient(@RequestParam int patientId) {	
		
//		---Using RestClient----
//		List<Appointment> a = RestClient.builder()
//				.baseUrl(appointmentUrl)
//				.build()
//				.get()
//				.uri("?patientId="+patientId)
//				.retrieve()
//				.body(new ParameterizedTypeReference<List<Appointment>>() {});
		
//		---Using Spring Cloud---
		List<Appointment> a = client.getByPatientId(patientId);
		
		return a;
	}
	
	
	@ExceptionHandler
	public ProblemDetail handlePatientNotFoundException(PatientNotFoundException e) {
		System.out.println(e.getMessage());
		ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
		detail.setTitle("Patient Not Found");
		return detail;
	}

}
