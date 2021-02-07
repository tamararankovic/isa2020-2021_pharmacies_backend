package isa.tim28.pharmacies.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isa.tim28.pharmacies.dtos.StatisticalDataDTO;
import isa.tim28.pharmacies.model.DermatologistAppointment;
import isa.tim28.pharmacies.model.MedicineConsumption;
import isa.tim28.pharmacies.model.PharmacistAppointment;
import isa.tim28.pharmacies.model.Pharmacy;
import isa.tim28.pharmacies.repository.DermatologistAppointmentRepository;
import isa.tim28.pharmacies.repository.MedicineConsumptionRepository;
import isa.tim28.pharmacies.repository.PharmacistAppointmentRepository;
import isa.tim28.pharmacies.service.interfaces.IStatisticsService;

@Service
public class StatisticsService implements IStatisticsService {

	private PharmacistAppointmentRepository pharmAppRepository;
	private DermatologistAppointmentRepository dermAppRepository;
	private MedicineConsumptionRepository medConsRepository;
	
	@Autowired
	public StatisticsService(PharmacistAppointmentRepository pharmAppRepository,
			DermatologistAppointmentRepository dermAppRepository, MedicineConsumptionRepository medConsRepository) {
		super();
		this.pharmAppRepository = pharmAppRepository;
		this.dermAppRepository = dermAppRepository;
		this.medConsRepository = medConsRepository;
	}

	@Override
	public List<StatisticalDataDTO> getPharmacistAppointmentCountByMonth(Pharmacy pharmacy) {
		List<StatisticalDataDTO> appCount = new ArrayList<StatisticalDataDTO>();
		List<PharmacistAppointment> allPassedAppointments = pharmAppRepository.findAll().stream()
															.filter(a -> a.getPharmacist().getEngegementInPharmacy().getPharmacy().getId() == pharmacy.getId() 
															&& a.getStartDateTime().isBefore(LocalDateTime.now())).collect(Collectors.toList());
		allPassedAppointments.sort((a, b) -> a.getStartDateTime().compareTo(b.getStartDateTime()));
		if(allPassedAppointments.size() > 0) {
			LocalDate minDate = allPassedAppointments.get(0).getStartDateTime().toLocalDate();
			while(minDate.getMonthValue() + minDate.getYear()*12 <= LocalDate.now().getMonthValue() + LocalDate.now().getYear()*12) {
				appCount.add(new StatisticalDataDTO(minDate.getMonthValue() + ". " + minDate.getYear() + ".", (double) allPassedAppointments.stream().filter(a -> a.getStartDateTime().getMonth().equals(minDate.getMonth()) && a.getStartDateTime().getYear() == minDate.getYear()).count()));
				minDate.plusMonths(1);
			}
		}
		return appCount;
	}

	@Override
	public List<StatisticalDataDTO> getPharmacistAppointmentCountByQuarter(Pharmacy pharmacy) {
		List<StatisticalDataDTO> appsByQuarter = new ArrayList<StatisticalDataDTO>();
		int monthOfCurrentQuarter = LocalDate.now().getMonthValue() % 3;
		List<StatisticalDataDTO> appsByMonth = getPharmacistAppointmentCountByMonth(pharmacy);
		int remainingMonths = (appsByMonth.size() - monthOfCurrentQuarter) % 3;
		int appCountInCurrentQuarter = 0;
		int year = LocalDate.now().getYear();
		int currentQuarter = LocalDate.now().getMonthValue() % 4 + 1;
		for(int i = appsByMonth.size() - 1; i > appsByMonth.size() - monthOfCurrentQuarter - 1; i--) {
			appCountInCurrentQuarter += appsByMonth.get(i).getValue();
		}
		appsByQuarter.add(new StatisticalDataDTO(currentQuarter + "-" + year, (double) appCountInCurrentQuarter));
		for(int i = appsByMonth.size() - monthOfCurrentQuarter - 1; i >= 2; i -= 3) {
			if (currentQuarter <= 1) {
				currentQuarter = 4;
				year--;
			} else {
				currentQuarter--;
			}
			appsByQuarter.add(new StatisticalDataDTO(currentQuarter + "-" + year, appsByMonth.get(i).getValue() + appsByMonth.get(i-1).getValue() + appsByMonth.get(i-2).getValue()));
		}
		if (currentQuarter <= 1) {
			currentQuarter = 4;
			year--;
		} else {
			currentQuarter--;
		}
		int appsInRemainingQuarter = 0;
		for(int i = 0; i < remainingMonths; i++) {
			appsInRemainingQuarter += appsByMonth.get(i).getValue();
		}
		appsByQuarter.add(new StatisticalDataDTO(currentQuarter + "-" + year, (double) appsInRemainingQuarter));
		Collections.reverse(appsByQuarter);
		return appsByQuarter;
	}

	@Override
	public List<StatisticalDataDTO> getPharmacistAppointmentCountByYear(Pharmacy pharmacy) {
		List<StatisticalDataDTO> appCount = new ArrayList<StatisticalDataDTO>();
		List<PharmacistAppointment> allPassedAppointments = pharmAppRepository.findAll().stream()
															.filter(a -> a.getPharmacist().getEngegementInPharmacy().getPharmacy().getId() == pharmacy.getId() 
															&& a.getStartDateTime().isBefore(LocalDateTime.now())).collect(Collectors.toList());
		allPassedAppointments.sort((a, b) -> a.getStartDateTime().compareTo(b.getStartDateTime()));
		if(allPassedAppointments.size() > 0) {
			LocalDate minDate = allPassedAppointments.get(0).getStartDateTime().toLocalDate();
			while(minDate.getYear() <= LocalDate.now().getYear()) {
				appCount.add(new StatisticalDataDTO(minDate.getYear() + ".", (double) allPassedAppointments.stream().filter(a -> a.getStartDateTime().getYear() == minDate.getYear()).count()));
				minDate.plusYears(1);
			}
		}
		return appCount;
	}

	@Override
	public List<StatisticalDataDTO> getDermatologistAppointmentCountByMonth(Pharmacy pharmacy) {
		List<StatisticalDataDTO> appCount = new ArrayList<StatisticalDataDTO>();
		List<DermatologistAppointment> allPassedAppointments = dermAppRepository.findAll().stream()
															.filter(a -> a.getPharmacy().getId() == pharmacy.getId()
															&& a.getStartDateTime().isBefore(LocalDateTime.now())
															&& a.isScheduled()).collect(Collectors.toList());
		allPassedAppointments.sort((a, b) -> a.getStartDateTime().compareTo(b.getStartDateTime()));
		if(allPassedAppointments.size() > 0) {
			LocalDate minDate = allPassedAppointments.get(0).getStartDateTime().toLocalDate();
			while(minDate.getMonthValue() + minDate.getYear()*12 <= LocalDate.now().getMonthValue() + LocalDate.now().getYear()*12) {
				appCount.add(new StatisticalDataDTO(minDate.getMonthValue() + ". " + minDate.getYear() + ".", (double) allPassedAppointments.stream().filter(a -> a.getStartDateTime().getMonth().equals(minDate.getMonth()) && a.getStartDateTime().getYear() == minDate.getYear()).count()));
				minDate.plusMonths(1);
			}
		}
		return appCount;
	}

	@Override
	public List<StatisticalDataDTO> getDermatologistAppointmentCountByQuarter(Pharmacy pharmacy) {
		List<StatisticalDataDTO> appsByQuarter = new ArrayList<StatisticalDataDTO>();
		int monthOfCurrentQuarter = LocalDate.now().getMonthValue() % 3;
		List<StatisticalDataDTO> appsByMonth = getDermatologistAppointmentCountByMonth(pharmacy);
		int remainingMonths = (appsByMonth.size() - monthOfCurrentQuarter) % 3;
		int appCountInCurrentQuarter = 0;
		int year = LocalDate.now().getYear();
		int currentQuarter = LocalDate.now().getMonthValue() % 4 + 1;
		for(int i = appsByMonth.size() - 1; i > appsByMonth.size() - monthOfCurrentQuarter - 1; i--) {
			appCountInCurrentQuarter += appsByMonth.get(i).getValue();
		}
		appsByQuarter.add(new StatisticalDataDTO(currentQuarter + "-" + year, (double) appCountInCurrentQuarter));
		for(int i = appsByMonth.size() - monthOfCurrentQuarter - 1; i >= 2; i -= 3) {
			if (currentQuarter <= 1) {
				currentQuarter = 4;
				year--;
			} else {
				currentQuarter--;
			}
			appsByQuarter.add(new StatisticalDataDTO(currentQuarter + "-" + year, appsByMonth.get(i).getValue() + appsByMonth.get(i-1).getValue() + appsByMonth.get(i-2).getValue()));
		}
		if (currentQuarter <= 1) {
			currentQuarter = 4;
			year--;
		} else {
			currentQuarter--;
		}
		int appsInRemainingQuarter = 0;
		for(int i = 0; i < remainingMonths; i++) {
			appsInRemainingQuarter += appsByMonth.get(i).getValue();
		}
		appsByQuarter.add(new StatisticalDataDTO(currentQuarter + "-" + year, (double) appsInRemainingQuarter));
		Collections.reverse(appsByQuarter);
		return appsByQuarter;
	}

	@Override
	public List<StatisticalDataDTO> getDermatologistAppointmentCountByYear(Pharmacy pharmacy) {
		List<StatisticalDataDTO> appCount = new ArrayList<StatisticalDataDTO>();
		List<DermatologistAppointment> allPassedAppointments = dermAppRepository.findAll().stream()
				.filter(a -> a.getPharmacy().getId() == pharmacy.getId()
				&& a.getStartDateTime().isBefore(LocalDateTime.now())
				&& a.isScheduled()).collect(Collectors.toList());
		allPassedAppointments.sort((a, b) -> a.getStartDateTime().compareTo(b.getStartDateTime()));
		if(allPassedAppointments.size() > 0) {
			LocalDate minDate = allPassedAppointments.get(0).getStartDateTime().toLocalDate();
			while(minDate.getYear() <= LocalDate.now().getYear()) {
				appCount.add(new StatisticalDataDTO(minDate.getYear() + ".", (double) allPassedAppointments.stream().filter(a -> a.getStartDateTime().getYear() == minDate.getYear()).count()));
				minDate.plusYears(1);
			}
		}
		return appCount;
	}

	@Override
	public List<StatisticalDataDTO> getMedicineConsumptionByMonth(Pharmacy pharmacy) {
		List<StatisticalDataDTO> medCount = new ArrayList<StatisticalDataDTO>();
		List<MedicineConsumption> medConsumption = medConsRepository.findAll().stream()
															.filter(mc -> mc.getPharmacy().getId() == pharmacy.getId()).collect(Collectors.toList());
		medConsumption.sort((a, b) -> a.getDateCreated().compareTo(b.getDateCreated()));
		if(medConsumption.size() > 0) {
			LocalDate minDate = medConsumption.get(0).getDateCreated();
			while(minDate.getMonthValue() + minDate.getYear()*12 <= LocalDate.now().getMonthValue() + LocalDate.now().getYear()*12) {
				medCount.add(new StatisticalDataDTO(minDate.getMonthValue() + ". " + minDate.getYear() + ".", (double) medConsumption.stream().filter(a -> a.getDateCreated().getMonth().equals(minDate.getMonth()) && a.getDateCreated().getYear() == minDate.getYear()).count()));
				minDate.plusMonths(1);
			}
		}
		return medCount;
	}

	@Override
	public List<StatisticalDataDTO> getMedicineConsumptionByQuarter(Pharmacy pharmacy) {
		List<StatisticalDataDTO> medsByQuarter = new ArrayList<StatisticalDataDTO>();
		int monthOfCurrentQuarter = LocalDate.now().getMonthValue() % 3;
		List<StatisticalDataDTO> medsByMonth = getMedicineConsumptionByMonth(pharmacy);
		int remainingMonths = (medsByMonth.size() - monthOfCurrentQuarter) % 3;
		int medsCountInCurrentQuarter = 0;
		int year = LocalDate.now().getYear();
		int currentQuarter = LocalDate.now().getMonthValue() % 4 + 1;
		for(int i = medsByMonth.size() - 1; i > medsByMonth.size() - monthOfCurrentQuarter - 1; i--) {
			medsCountInCurrentQuarter += medsByMonth.get(i).getValue();
		}
		medsByQuarter.add(new StatisticalDataDTO(currentQuarter + "-" + year, (double) medsCountInCurrentQuarter));
		for(int i = medsByMonth.size() - monthOfCurrentQuarter - 1; i >= 2; i -= 3) {
			if (currentQuarter <= 1) {
				currentQuarter = 4;
				year--;
			} else {
				currentQuarter--;
			}
			medsByQuarter.add(new StatisticalDataDTO(currentQuarter + "-" + year, medsByMonth.get(i).getValue() + medsByMonth.get(i-1).getValue() + medsByMonth.get(i-2).getValue()));
		}
		if (currentQuarter <= 1) {
			currentQuarter = 4;
			year--;
		} else {
			currentQuarter--;
		}
		int medsInRemainingQuarter = 0;
		for(int i = 0; i < remainingMonths; i++) {
			medsInRemainingQuarter += medsByMonth.get(i).getValue();
		}
		medsByQuarter.add(new StatisticalDataDTO(currentQuarter + "-" + year, (double) medsInRemainingQuarter));
		Collections.reverse(medsByQuarter);
		return medsByQuarter;
	}

	@Override
	public List<StatisticalDataDTO> getMedicineConsumptionByYear(Pharmacy pharmacy) {
		List<StatisticalDataDTO> medCount = new ArrayList<StatisticalDataDTO>();
		List<MedicineConsumption> medConsumption = medConsRepository.findAll().stream()
															.filter(mc -> mc.getPharmacy().getId() == pharmacy.getId()).collect(Collectors.toList());
		medConsumption.sort((a, b) -> a.getDateCreated().compareTo(b.getDateCreated()));
		if(medConsumption.size() > 0) {
			LocalDate minDate = medConsumption.get(0).getDateCreated();
			while(minDate.getYear() <= LocalDate.now().getYear()) {
				medCount.add(new StatisticalDataDTO(minDate.getYear() + ".", (double) medConsumption.stream().filter(a -> a.getDateCreated().getYear() == minDate.getYear()).count()));
				minDate.plusMonths(1);
			}
		}
		return medCount;
	}

	@Override
	public List<StatisticalDataDTO> getDailyIncomeFromDermatologistAppointments(Pharmacy pharmacy) {
		List<StatisticalDataDTO> income = new ArrayList<StatisticalDataDTO>();
		List<DermatologistAppointment> allPassedAppointments = dermAppRepository.findAll().stream()
															.filter(a -> a.getPharmacy().getId() == pharmacy.getId()
															&& a.getStartDateTime().isBefore(LocalDateTime.now())
															&& a.isScheduled()).collect(Collectors.toList());
		if(allPassedAppointments.size() > 0) {
			LocalDate minDate = allPassedAppointments.get(0).getStartDateTime().toLocalDate();
			while(minDate.isBefore(minDate)) {
				List<DermatologistAppointment> appsDay = allPassedAppointments.stream().filter(a -> a.getStartDateTime().toLocalDate().equals(minDate)).collect(Collectors.toList());
				double dailyIncome = 0;
				for(DermatologistAppointment app : appsDay)
					dailyIncome += app.getPrice();
				income.add(new StatisticalDataDTO(minDate.getDayOfMonth() + ". " + minDate.getMonthValue() + ". " + minDate.getYear() + ".", dailyIncome));
				minDate.plusDays(1);
			}
		}
		return income;
	}

	@Override
	public List<StatisticalDataDTO> getDailyIncomeFromPharmacistAppointments(Pharmacy pharmacy) {
		List<StatisticalDataDTO> income = new ArrayList<StatisticalDataDTO>();
		List<PharmacistAppointment> allPassedAppointments = pharmAppRepository.findAll().stream()
															.filter(a -> a.getPharmacist().getEngegementInPharmacy().getPharmacy().getId() == pharmacy.getId()
															&& a.getStartDateTime().isBefore(LocalDateTime.now())).collect(Collectors.toList());
		if(allPassedAppointments.size() > 0) {
			LocalDate minDate = allPassedAppointments.get(0).getStartDateTime().toLocalDate();
			while(minDate.isBefore(minDate)) {
				income.add(new StatisticalDataDTO(minDate.getDayOfMonth() + ". " + minDate.getMonthValue() + ". " + minDate.getYear() + ".", allPassedAppointments.stream().filter(a -> a.getStartDateTime().toLocalDate().equals(minDate)).count()*pharmacy.getPharmacistAppointmentPriceOnDate(minDate)));
				minDate.plusDays(1);
			}
		}
		return income;
	}

	@Override
	public List<StatisticalDataDTO> getDailyIncomeFromSoldMedicines(Pharmacy pharmacy) {
		List<StatisticalDataDTO> income = new ArrayList<StatisticalDataDTO>();
		List<MedicineConsumption> medConsumption = medConsRepository.findAll().stream().filter(mc -> mc.getPharmacy().getId() == pharmacy.getId()).collect(Collectors.toList());
		if(medConsumption.size() > 0) {
			LocalDate minDate = medConsumption.get(0).getDateCreated();
			while(minDate.isBefore(LocalDate.now())) {
				double dailyIncome = 0;
				for(MedicineConsumption mc : medConsumption)
					if(mc.getDateCreated().equals(minDate))
						dailyIncome += pharmacy.getMedicinePriceOnDate(minDate, mc.getMedicine())*mc.getQuantity();
				income.add(new StatisticalDataDTO(minDate.getDayOfMonth() + ". " + minDate.getMonthValue() + ". " + minDate.getYear() + ".", dailyIncome));
				minDate.plusDays(1);
			}
		}
		return income;
	}
}
