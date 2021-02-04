package isa.tim28.pharmacies.service.interfaces;

import java.util.List;
import java.util.Set;

import isa.tim28.pharmacies.dtos.DermatologistAppointmentDTO;
import isa.tim28.pharmacies.dtos.DermatologistReportDTO;
import isa.tim28.pharmacies.dtos.MedicineDTOM;
import isa.tim28.pharmacies.dtos.MedicineDetailsDTO;
import isa.tim28.pharmacies.dtos.MedicineQuantityCheckDTO;
import isa.tim28.pharmacies.exceptions.UserDoesNotExistException;
import isa.tim28.pharmacies.model.Dermatologist;
import isa.tim28.pharmacies.model.DermatologistAppointment;
import isa.tim28.pharmacies.model.Pharmacy;

public interface IDermatologistAppointmentService {

	Set<DermatologistAppointment> findAllAvailableByPharmacyId(long pharmacyId);
	
	DermatologistAppointmentDTO getAppointmentDTOById(long appointmentId);
	
	List<MedicineDTOM> getMedicineList();
	
	String fillReport(DermatologistReportDTO dto);
	
	boolean checkAllergies(long patientId, long medicineId) throws UserDoesNotExistException;
	
	DermatologistAppointment getAppointmentById(long appointmentId);
	
	String getMedicineCodeById(long medicineId);
	
	MedicineQuantityCheckDTO checkIfMedicineIsAvailable(long medicineId, long appointmentId);
	
	List<MedicineDTOM> compatibleMedicine(long medicineId);
	
	MedicineDetailsDTO medicineDetails(long medicineId);
	
	boolean dermatologistHasIncomingAppointmentsInPharmacy(Dermatologist dermatologist, Pharmacy pharmacy);
	
	void deleteUnscheduledAppointments(Dermatologist dermatologist);
	
}
