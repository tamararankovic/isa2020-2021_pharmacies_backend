package isa.tim28.pharmacies.service.interfaces;

import java.util.List;
import java.util.Set;

import isa.tim28.pharmacies.dtos.DermatologistAppointmentDTO;
import isa.tim28.pharmacies.dtos.DermatologistReportDTO;
import isa.tim28.pharmacies.dtos.MedicineDTO;
import isa.tim28.pharmacies.dtos.MedicineDetailsDTO;
import isa.tim28.pharmacies.dtos.MedicineQuantityCheckDTO;
import isa.tim28.pharmacies.exceptions.UserDoesNotExistException;
import isa.tim28.pharmacies.model.DermatologistAppointment;

public interface IDermatologistAppointmentService {

	Set<DermatologistAppointment> findAllAvailableByPharmacyId(long pharmacyId);
	
	DermatologistAppointmentDTO getAppointmentDTOById(long appointmentId);
	
	List<MedicineDTO> getMedicineList();
	
	String fillReport(DermatologistReportDTO dto);
	
	boolean checkAllergies(long patientId, long medicineId) throws UserDoesNotExistException;
	
	DermatologistAppointment getAppointmentById(long appointmentId);
	
	String getMedicineCodeById(long medicineId);
	
	MedicineQuantityCheckDTO checkIfMedicineIsAvailable(long medicineId, long appointmentId);
	
	List<MedicineDTO> compatibleMedicine(long medicineId);
	
	MedicineDetailsDTO medicineDetails(long medicineId);
}
