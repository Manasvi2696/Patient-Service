package org.healthcare.patient.openfeign;

import java.util.List;

import org.healthcare.appointment.dto.Appointment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(url="${app.appointment.url}", name="ps")
public interface OpenfeignClient {
	
	@GetMapping("/{id}")
	public Appointment getById(@PathVariable int id);

	@GetMapping(params = "doctorId")
	public List<Appointment> getByDoctorId(@RequestParam int doctorId);
	
	@GetMapping(params = "patientId")
	public List<Appointment> getByPatientId(@RequestParam int patientId);

}
