package isa.tim28.pharmacies.service.interfaces;

import isa.tim28.pharmacies.model.Pharmacist;

public interface IPharmacistAppointmentService {

	boolean pharmacistHasIncomingAppointments(Pharmacist pharmacist);
}
