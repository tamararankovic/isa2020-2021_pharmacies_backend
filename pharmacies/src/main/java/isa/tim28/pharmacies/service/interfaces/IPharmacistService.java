package isa.tim28.pharmacies.service.interfaces;

import java.util.Set;

import isa.tim28.pharmacies.model.Pharmacist;

public interface IPharmacistService {

	Set<Pharmacist> findAllByPharmacyId(long pharmacyId);
}
