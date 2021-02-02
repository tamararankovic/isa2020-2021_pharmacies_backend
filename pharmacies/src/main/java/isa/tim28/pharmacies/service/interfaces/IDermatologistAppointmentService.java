package isa.tim28.pharmacies.service.interfaces;

import java.util.Set;

import isa.tim28.pharmacies.model.Dermatologist;
import isa.tim28.pharmacies.model.DermatologistAppointment;
import isa.tim28.pharmacies.model.Pharmacy;

public interface IDermatologistAppointmentService {

	Set<DermatologistAppointment> findAllAvailableByPharmacyId(long pharmacyId);
	
	boolean dermatologistHasIncomingAppointmentsInPharmacy(Dermatologist dermatologist, Pharmacy pharmacy);
	
	void deleteUnscheduledAppointments(Dermatologist dermatologist);
}
