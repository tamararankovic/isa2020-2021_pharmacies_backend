package isa.tim28.pharmacies.service.interfaces;

import java.util.Set;

import isa.tim28.pharmacies.model.Dermatologist;

public interface IDermatologistService {

	Set<Dermatologist> findAllByPharmacyId(long pharmacyId);
}
