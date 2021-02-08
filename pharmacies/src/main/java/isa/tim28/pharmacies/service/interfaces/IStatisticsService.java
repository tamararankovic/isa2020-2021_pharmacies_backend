package isa.tim28.pharmacies.service.interfaces;

import java.util.List;

import isa.tim28.pharmacies.dtos.StatisticalDataDTO;
import isa.tim28.pharmacies.model.Pharmacy;

public interface IStatisticsService {

	List<StatisticalDataDTO> getPharmacistAppointmentCountByMonth(Pharmacy pharmacy);
	
	List<StatisticalDataDTO> getPharmacistAppointmentCountByQuarter(Pharmacy pharmacy);
	
	List<StatisticalDataDTO> getPharmacistAppointmentCountByYear(Pharmacy pharmacy);
	
	List<StatisticalDataDTO> getDermatologistAppointmentCountByMonth(Pharmacy pharmacy);
	
	List<StatisticalDataDTO> getDermatologistAppointmentCountByQuarter(Pharmacy pharmacy);
	
	List<StatisticalDataDTO> getDermatologistAppointmentCountByYear(Pharmacy pharmacy);
	
	List<StatisticalDataDTO> getAppointmentCountByMonth(Pharmacy pharmacy);
	
	List<StatisticalDataDTO> getAppointmentCountByQuarter(Pharmacy pharmacy);
	
	List<StatisticalDataDTO> getAppointmentCountByYear(Pharmacy pharmacy);
	
	List<StatisticalDataDTO> getMedicineConsumptionByMonth(Pharmacy pharmacy);
	
	List<StatisticalDataDTO> getMedicineConsumptionByQuarter(Pharmacy pharmacy);
	
	List<StatisticalDataDTO> getMedicineConsumptionByYear(Pharmacy pharmacy);
	
	List<StatisticalDataDTO> getDailyIncomeFromDermatologistAppointments(Pharmacy pharmacy);
	
	List<StatisticalDataDTO> getDailyIncomeFromPharmacistAppointments(Pharmacy pharmacy);
	
	List<StatisticalDataDTO> getDailyIncomeFromSoldMedicines(Pharmacy pharmacy);
}
