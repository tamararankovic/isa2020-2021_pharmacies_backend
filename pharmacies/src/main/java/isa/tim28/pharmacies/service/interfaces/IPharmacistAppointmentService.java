package isa.tim28.pharmacies.service.interfaces;

import java.util.List;

import isa.tim28.pharmacies.dtos.DermatologistAppointmentDTO;
import isa.tim28.pharmacies.dtos.DermatologistReportDTO;
import isa.tim28.pharmacies.dtos.MedicineDTO;
import isa.tim28.pharmacies.dtos.MedicineQuantityCheckDTO;
import isa.tim28.pharmacies.dtos.ReservationValidDTO;
import isa.tim28.pharmacies.exceptions.UserDoesNotExistException;
import isa.tim28.pharmacies.model.Pharmacist;
import isa.tim28.pharmacies.model.PharmacistAppointment;
import isa.tim28.pharmacies.model.Reservation;

public interface IPharmacistAppointmentService {
	
	DermatologistAppointmentDTO getAppointmentDTOById(long appointmentId);
	
	List<MedicineDTO> getMedicineList();
	
	String fillReport(DermatologistReportDTO dto);
	
	boolean checkAllergies(long patientId, long medicineId) throws UserDoesNotExistException;
	
	PharmacistAppointment getAppointmentById(long appointmentId);
	
	String getMedicineCodeById(long medicineId);
	
	MedicineQuantityCheckDTO checkIfMedicineIsAvailable(long medicineId, long appointmentId);
	
	List<MedicineDTO> compatibleMedicine(long medicineId);
	
	ReservationValidDTO isReservationValid(long reservationId, Pharmacist pharmacist);

	Reservation reservationTaken(long reservationId);

}
