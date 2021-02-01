package isa.tim28.pharmacies.service.interfaces;

import java.util.Set;

import isa.tim28.pharmacies.model.DermatologistAppointment;

public interface IDermatologistAppointmentService {

	Set<DermatologistAppointment> findAllAvailableByPharmacyId(long pharmacyId);
}
