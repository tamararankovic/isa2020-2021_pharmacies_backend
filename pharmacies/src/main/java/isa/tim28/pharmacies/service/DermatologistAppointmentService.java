package isa.tim28.pharmacies.service;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isa.tim28.pharmacies.model.Dermatologist;
import isa.tim28.pharmacies.model.DermatologistAppointment;
import isa.tim28.pharmacies.model.Pharmacy;
import isa.tim28.pharmacies.repository.DermatologistAppointmentRepository;
import isa.tim28.pharmacies.service.interfaces.IDermatologistAppointmentService;

@Service
public class DermatologistAppointmentService implements IDermatologistAppointmentService {

	private DermatologistAppointmentRepository appointmentRepository;
	
	@Autowired
	public DermatologistAppointmentService(DermatologistAppointmentRepository appointmentRepository) {
		super();
		this.appointmentRepository = appointmentRepository;
	}

	@Override
	public Set<DermatologistAppointment> findAllAvailableByPharmacyId(long pharmacyId) {
		return appointmentRepository.findAll().stream()
				.filter(a -> a.getPharmacy().getId() == pharmacyId)
				.filter(a -> a.getStartDateTime().isAfter(LocalDateTime.now()))
				.filter(a -> !a.isScheduled()).collect(Collectors.toSet());
	}

	@Override
	public boolean dermatologistHasIncomingAppointmentsInPharmacy(Dermatologist dermatologist, Pharmacy pharmacy) {
		return appointmentRepository.findAll().stream().filter(a -> a.getDermatologist().getId() == dermatologist.getId()
				&& a.getPharmacy().getId() == pharmacy.getId()
				&& a.getStartDateTime().isAfter(LocalDateTime.now())
				&& a.isScheduled()).count() > 0;
	}
}
