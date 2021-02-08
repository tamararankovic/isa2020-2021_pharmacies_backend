package isa.tim28.pharmacies.service.interfaces;

import java.time.LocalDateTime;
import java.util.List;

import isa.tim28.pharmacies.dtos.DermatologistAppointmentDTO;
import isa.tim28.pharmacies.dtos.DermatologistReportDTO;
import isa.tim28.pharmacies.dtos.LeaveDTO;
import isa.tim28.pharmacies.dtos.LeaveViewDTO;
import isa.tim28.pharmacies.dtos.MedicineDTOM;
import isa.tim28.pharmacies.dtos.MedicineQuantityCheckDTO;
import isa.tim28.pharmacies.dtos.MyPatientDTO;
import isa.tim28.pharmacies.dtos.PharmAppByMonthDTO;
import isa.tim28.pharmacies.dtos.PharmAppByWeekDTO;
import isa.tim28.pharmacies.dtos.PharmAppByYearDTO;
import isa.tim28.pharmacies.dtos.PharmAppDTO;
import isa.tim28.pharmacies.dtos.ReservationValidDTO;
import isa.tim28.pharmacies.exceptions.UserDoesNotExistException;
import isa.tim28.pharmacies.model.Pharmacist;
import isa.tim28.pharmacies.model.PharmacistAppointment;
import isa.tim28.pharmacies.model.Reservation;

public interface IPharmacistAppointmentService {
	
	DermatologistAppointmentDTO getAppointmentDTOById(long appointmentId);
	
	List<MedicineDTOM> getMedicineList();
	
	String fillReport(DermatologistReportDTO dto);
	
	boolean checkAllergies(long patientId, long medicineId) throws UserDoesNotExistException;
	
	PharmacistAppointment getAppointmentById(long appointmentId);
	
	String getMedicineCodeById(long medicineId);
	
	MedicineQuantityCheckDTO checkIfMedicineIsAvailable(long medicineId, long appointmentId);
	
	List<MedicineDTOM> compatibleMedicine(long medicineId);
	
	ReservationValidDTO isReservationValid(long reservationId, Pharmacist pharmacist);

	Reservation reservationTaken(long reservationId);
	
	boolean pharmacistHasIncomingAppointments(Pharmacist pharmacist);
	
	PharmacistAppointment savePharmacistAppointment(long lastAppointmentId, LocalDateTime startDateTime);
	
	boolean checkIfFreeAppointmentExists(long lastAppointmentId, LocalDateTime startDateTime);
	
	List<PharmAppDTO> getAppointmentsByWeek(PharmAppByWeekDTO dto, long userId);
	
	List<PharmAppDTO> getAppointmentsByMonth(PharmAppByMonthDTO dto, long userId);
	
	List<PharmAppDTO> getAppointmentsByYear(PharmAppByYearDTO dto, long userId);
	
	void patientWasNotPresent(long appointmentId);

	void saveLeaveRequest(LeaveDTO dto, long userId);
	
	List<LeaveViewDTO> allLeaveRequests(long userId);
	
	List<MyPatientDTO> myPatients(long userId);
	
	PharmAppDTO hasAppointmentWithPatient(long userId, long patientId);
}
