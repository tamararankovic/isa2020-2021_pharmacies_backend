package isa.tim28.pharmacies.service.interfaces;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import isa.tim28.pharmacies.dtos.CurrentlyHasAppointmentDTO;
import isa.tim28.pharmacies.dtos.DermatologistAppointmentDTO;
import isa.tim28.pharmacies.dtos.DermatologistExaminationForPatientDTO;
import isa.tim28.pharmacies.dtos.DermatologistReportDTO;
import isa.tim28.pharmacies.dtos.DoctorRatingDTO;
import isa.tim28.pharmacies.dtos.ExistingDermatologistAppointmentDTO;
import isa.tim28.pharmacies.dtos.LeaveDTO;
import isa.tim28.pharmacies.dtos.LeaveViewDTO;
import isa.tim28.pharmacies.dtos.MedicineDTOM;
import isa.tim28.pharmacies.dtos.MedicineDetailsDTO;
import isa.tim28.pharmacies.dtos.MedicineQuantityCheckDTO;
import isa.tim28.pharmacies.dtos.MyPatientDTO;
import isa.tim28.pharmacies.dtos.PharmAppByMonthDTO;
import isa.tim28.pharmacies.dtos.PharmAppByWeekDTO;
import isa.tim28.pharmacies.dtos.PharmAppByYearDTO;
import isa.tim28.pharmacies.dtos.PharmAppDTO;
import isa.tim28.pharmacies.dtos.ShowCounselingDTO;
import isa.tim28.pharmacies.exceptions.ForbiddenOperationException;
import isa.tim28.pharmacies.exceptions.UserDoesNotExistException;
import isa.tim28.pharmacies.model.Dermatologist;
import isa.tim28.pharmacies.model.DermatologistAppointment;
import isa.tim28.pharmacies.model.Pharmacy;
import isa.tim28.pharmacies.model.User;

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
	
	DermatologistAppointment saveDermatologistAppointment(long lastAppointmentId, long price, LocalDateTime startDateTime);
	
	boolean checkIfFreeAppointmentExists(long lastAppointmentId, LocalDateTime startDateTime);
	
	Set<ExistingDermatologistAppointmentDTO> getExistingDermatologistAppointments(long lastAppointmentId);
	
	DermatologistAppointment saveExistingDermatologistAppointment(long lastAppointmentId, long newAppointmentId);
	
	boolean dermatologistHasAppointmentsInTimInterval(Dermatologist dermatologist, LocalDate startDate, LocalDate endDate);
	
	List<PharmAppDTO> getAppointmentsByWeek(PharmAppByWeekDTO dto, long pharmacyId, long userId);
	
	List<PharmAppDTO> getAppointmentsByMonth(PharmAppByMonthDTO dto, long pharmacyId, long userId);
	
	List<PharmAppDTO> getAppointmentsByYear(PharmAppByYearDTO dto, long pharmacyId, long userId);
	
	void patientWasNotPresent(long appointmentId);
	
	void saveLeaveRequest(LeaveDTO dto, long userId);
	
	List<LeaveViewDTO> allLeaveRequests(long userId);
	
	List<MyPatientDTO> myPatients(long userId);
	
	PharmAppDTO hasAppointmentWithPatient(long userId, long patientId);
	
	void createPredefinedAppointment(Dermatologist dermatologist, LocalDateTime startDateTime, int durationInMinutes, long price, Pharmacy pharmacy) throws ForbiddenOperationException;

	List<ShowCounselingDTO> getAllIncomingAppointments(long id, boolean past);
	
	CurrentlyHasAppointmentDTO isDermatologistInAppointment(long userId);
	
	void endCurrentAppointment(long userId);

	void updateMedicineQuantity(long medicineId, long appointmentId);

	DermatologistExaminationForPatientDTO scheduleApp(long appId, long appVersion, User loggedInUser);
}
