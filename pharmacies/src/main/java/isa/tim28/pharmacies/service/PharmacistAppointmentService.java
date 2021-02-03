package isa.tim28.pharmacies.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isa.tim28.pharmacies.model.Pharmacist;
import isa.tim28.pharmacies.repository.PharmacistAppointmentRepository;
import isa.tim28.pharmacies.service.interfaces.IPharmacistAppointmentService;

@Service
public class PharmacistAppointmentService implements IPharmacistAppointmentService {

	private PharmacistAppointmentRepository appointmentRepository;

	@Autowired
	public PharmacistAppointmentService(PharmacistAppointmentRepository appointmentRepository) {
		super();
		this.appointmentRepository = appointmentRepository;
	}

	@Override
	public boolean pharmacistHasIncomingAppointments(Pharmacist pharmacist) {
		return appointmentRepository.findAll().stream()
				.filter(a -> a.getPharmacist().getId() == pharmacist.getId() 
				&& a.getStartDateTime().isAfter(LocalDateTime.now())).count() > 0;
	}
}
