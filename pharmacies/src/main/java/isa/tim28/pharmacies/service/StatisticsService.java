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
				LocalDate date = minDate;
				appCount.add(new StatisticalDataDTO(minDate.getMonthValue() + ". " + minDate.getYear() + ".", (double) allPassedAppointments.stream().filter(a -> a.getStartDateTime().getMonth().equals(date.getMonth()) && a.getStartDateTime().getYear() == date.getYear()).count()));
				minDate = minDate.plusMonths(1);
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
		int month = LocalDate.now().getMonthValue();
		int currentQuarter = 1;
		if(month >= 1 && month <= 3)
			currentQuarter = 1;
		else if(month >=4 && month <= 6)
			currentQuarter = 2;
		else if(month >= 7 && month <= 9)
			currentQuarter = 3;
		else
			currentQuarter = 4;
		if(appsByMonth.size() > 0) {
			for(int i = appsByMonth.size() - 1; i > appsByMonth.size() - monthOfCurrentQuarter - 1; i--) {
				appCountInCurrentQuarter += appsByMonth.get(i).getValue();
			}
			appsByQuarter.add(new StatisticalDataDTO(currentQuarter + "-" + year, (double) appCountInCurrentQuarter));
			if(appsByMonth.size() - monthOfCurrentQuarter > 0) {
				for(int i = appsByMonth.size() - monthOfCurrentQuarter - 1; i >= 2; i -= 3) {
					if (currentQuarter <= 1) {
						currentQuarter = 4;
						year--;
					} else {
						currentQuarter--;
					}
					appsByQuarter.add(new StatisticalDataDTO(currentQuarter + "-" + year, appsByMonth.get(i).getValue() + appsByMonth.get(i-1).getValue() + appsByMonth.get(i-2).getValue()));
				}
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
			if(remainingMonths > 0)
				appsByQuarter.add(new StatisticalDataDTO(currentQuarter + "-" + year, (double) appsInRemainingQuarter));
			Collections.reverse(appsByQuarter);
		}
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
				LocalDate date = minDate;
				appCount.add(new StatisticalDataDTO(minDate.getYear() + ".", (double) allPassedAppointments.stream().filter(a -> a.getStartDateTime().getYear() == date.getYear()).count()));
				minDate = minDate.plusYears(1);
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
				LocalDate date = minDate;
				appCount.add(new StatisticalDataDTO(minDate.getMonthValue() + ". " + minDate.getYear() + ".", (double) allPassedAppointments.stream().filter(a -> a.getStartDateTime().getMonth().equals(date.getMonth()) && a.getStartDateTime().getYear() == date.getYear()).count()));
				minDate = minDate.plusMonths(1);
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
		int month = LocalDate.now().getMonthValue();
		int currentQuarter = 1;
		if(month >= 1 && month <= 3)
			currentQuarter = 1;
		else if(month >=4 && month <= 6)
			currentQuarter = 2;
		else if(month >= 7 && month <= 9)
			currentQuarter = 3;
		else
			currentQuarter = 4;
		if(appsByMonth.size() > 0) {
			for(int i = appsByMonth.size() - 1; i > appsByMonth.size() - monthOfCurrentQuarter - 1; i--) {
				appCountInCurrentQuarter += appsByMonth.get(i).getValue();
			}
			appsByQuarter.add(new StatisticalDataDTO(currentQuarter + "-" + year, (double) appCountInCurrentQuarter));
			if(appsByMonth.size() - monthOfCurrentQuarter > 0) {
				for(int i = appsByMonth.size() - monthOfCurrentQuarter - 1; i >= 2; i -= 3) {
					if (currentQuarter <= 1) {
						currentQuarter = 4;
						year--;
					} else {
						currentQuarter--;
					}
					appsByQuarter.add(new StatisticalDataDTO(currentQuarter + "-" + year, appsByMonth.get(i).getValue() + appsByMonth.get(i-1).getValue() + appsByMonth.get(i-2).getValue()));
				}
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
			if(remainingMonths > 0)
				appsByQuarter.add(new StatisticalDataDTO(currentQuarter + "-" + year, (double) appsInRemainingQuarter));
			Collections.reverse(appsByQuarter);
		}
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
				LocalDate date = minDate;
				appCount.add(new StatisticalDataDTO(minDate.getYear() + ".", (double) allPassedAppointments.stream().filter(a -> a.getStartDateTime().getYear() == date.getYear()).count()));
				minDate = minDate.plusYears(1);
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
				LocalDate date = minDate;
				medCount.add(new StatisticalDataDTO(minDate.getMonthValue() + ". " + minDate.getYear() + ".", (double) medConsumption.stream().filter(a -> a.getDateCreated().getMonth().equals(date.getMonth()) && a.getDateCreated().getYear() == date.getYear()).count()));
				minDate = minDate.plusMonths(1);
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
		int month = LocalDate.now().getMonthValue();
		int currentQuarter = 1;
		if(month >= 1 && month <= 3)
			currentQuarter = 1;
		else if(month >=4 && month <= 6)
			currentQuarter = 2;
		else if(month >= 7 && month <= 9)
			currentQuarter = 3;
		else
			currentQuarter = 4;
		if(medsByMonth.size() > 0) {
			for(int i = medsByMonth.size() - 1; i > medsByMonth.size() - monthOfCurrentQuarter - 1; i--) {
				medsCountInCurrentQuarter += medsByMonth.get(i).getValue();
			}
			medsByQuarter.add(new StatisticalDataDTO(currentQuarter + "-" + year, (double) medsCountInCurrentQuarter));
			if(medsByMonth.size() - monthOfCurrentQuarter > 0) {
				for(int i = medsByMonth.size() - monthOfCurrentQuarter - 1; i >= 2; i -= 3) {
					if (currentQuarter <= 1) {
						currentQuarter = 4;
						year--;
					} else {
						currentQuarter--;
					}
					medsByQuarter.add(new StatisticalDataDTO(currentQuarter + "-" + year, medsByMonth.get(i).getValue() + medsByMonth.get(i-1).getValue() + medsByMonth.get(i-2).getValue()));
				}
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
			if(remainingMonths > 0)
				medsByQuarter.add(new StatisticalDataDTO(currentQuarter + "-" + year, (double) medsInRemainingQuarter));
			Collections.reverse(medsByQuarter);
		}
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
				LocalDate date = minDate;
				medCount.add(new StatisticalDataDTO(minDate.getYear() + ".", (double) medConsumption.stream().filter(a -> a.getDateCreated().getYear() == date.getYear()).count()));
				minDate = minDate.plusYears(1);
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
		allPassedAppointments.sort((a, b) -> a.getStartDateTime().compareTo(b.getStartDateTime()));
		if(allPassedAppointments.size() > 0) {
			LocalDate minDate = allPassedAppointments.get(0).getStartDateTime().toLocalDate();
			while(minDate.isBefore(LocalDate.now()) || minDate.equals(LocalDate.now())) {
				LocalDate date = minDate;
				List<DermatologistAppointment> appsDay = allPassedAppointments.stream().filter(a -> a.getStartDateTime().toLocalDate().equals(date)).collect(Collectors.toList());
				double dailyIncome = 0;
				for(DermatologistAppointment app : appsDay)
					dailyIncome += app.getPrice();
				income.add(new StatisticalDataDTO(minDate.getDayOfMonth() + ". " + minDate.getMonthValue() + ". " + minDate.getYear() + ".", dailyIncome));
				minDate = minDate.plusDays(1);
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
		allPassedAppointments.sort((a, b) -> a.getStartDateTime().compareTo(b.getStartDateTime()));
		if(allPassedAppointments.size() > 0) {
			LocalDate minDate = allPassedAppointments.get(0).getStartDateTime().toLocalDate();
			while(minDate.isBefore(LocalDate.now()) || minDate.equals(LocalDate.now())) {
				LocalDate date = minDate;
				income.add(new StatisticalDataDTO(minDate.getDayOfMonth() + ". " + minDate.getMonthValue() + ". " + minDate.getYear() + ".", allPassedAppointments.stream().filter(a -> a.getStartDateTime().toLocalDate().equals(date)).count()*pharmacy.getPharmacistAppointmentPriceOnDate(minDate)));
				minDate = minDate.plusDays(1);
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
			while(minDate.isBefore(LocalDate.now()) || minDate.equals(LocalDate.now())) {
				double dailyIncome = 0;
				for(MedicineConsumption mc : medConsumption)
					if(mc.getDateCreated().equals(minDate))
						dailyIncome += pharmacy.getMedicinePriceOnDate(minDate, mc.getMedicine())*mc.getQuantity();
				income.add(new StatisticalDataDTO(minDate.getDayOfMonth() + ". " + minDate.getMonthValue() + ". " + minDate.getYear() + ".", dailyIncome));
				minDate = minDate.plusDays(1);
			}
		}
		return income;
	}

	@Override
	public List<StatisticalDataDTO> getAppointmentCountByMonth(Pharmacy pharmacy) {
		List<StatisticalDataDTO> appCount = new ArrayList<StatisticalDataDTO>();
		List<PharmacistAppointment> allPassedPharmAppointments = pharmAppRepository.findAll().stream()
															.filter(a -> a.getPharmacist().getEngegementInPharmacy().getPharmacy().getId() == pharmacy.getId() 
															&& a.getStartDateTime().isBefore(LocalDateTime.now())).collect(Collectors.toList());
		List<DermatologistAppointment> allPassedDermAppointments = dermAppRepository.findAll().stream()
				.filter(a -> a.getPharmacy().getId() == pharmacy.getId()
				&& a.getStartDateTime().isBefore(LocalDateTime.now())
				&& a.isScheduled()).collect(Collectors.toList());
		allPassedPharmAppointments.sort((a, b) -> a.getStartDateTime().compareTo(b.getStartDateTime()));
		allPassedDermAppointments.sort((a, b) -> a.getStartDateTime().compareTo(b.getStartDateTime()));
		if(allPassedPharmAppointments.size() > 0 || allPassedDermAppointments.size() > 0) {
			LocalDate minDate = allPassedPharmAppointments.get(0).getStartDateTime().toLocalDate();
			LocalDate minDate2 = allPassedDermAppointments.get(0).getStartDateTime().toLocalDate();
			if(minDate2.isBefore(minDate))
				minDate = minDate2;
			while(minDate.getMonthValue() + minDate.getYear()*12 <= LocalDate.now().getMonthValue() + LocalDate.now().getYear()*12) {
				LocalDate date = minDate;
				appCount.add(new StatisticalDataDTO(minDate.getMonthValue() + ". " + minDate.getYear() + ".", (double) allPassedPharmAppointments.stream().filter(a -> a.getStartDateTime().getMonth().equals(date.getMonth()) && a.getStartDateTime().getYear() == date.getYear()).count() + allPassedDermAppointments.stream().filter(a -> a.getStartDateTime().getMonth().equals(date.getMonth()) && a.getStartDateTime().getYear() == date.getYear()).count()));
				minDate = minDate.plusMonths(1);
			}
		}
		return appCount;
	}

	@Override
	public List<StatisticalDataDTO> getAppointmentCountByQuarter(Pharmacy pharmacy) {
		List<StatisticalDataDTO> appsByQuarter = new ArrayList<StatisticalDataDTO>();
		int monthOfCurrentQuarter = LocalDate.now().getMonthValue() % 3;
		List<StatisticalDataDTO> appsByMonth = getAppointmentCountByMonth(pharmacy);
		int remainingMonths = (appsByMonth.size() - monthOfCurrentQuarter) % 3;
		int appCountInCurrentQuarter = 0;
		int year = LocalDate.now().getYear();
		int month = LocalDate.now().getMonthValue();
		int currentQuarter = 1;
		if(month >= 1 && month <= 3)
			currentQuarter = 1;
		else if(month >=4 && month <= 6)
			currentQuarter = 2;
		else if(month >= 7 && month <= 9)
			currentQuarter = 3;
		else
			currentQuarter = 4;
		if(appsByMonth.size() > 0) {
			for(int i = appsByMonth.size() - 1; i > appsByMonth.size() - monthOfCurrentQuarter - 1; i--) {
				appCountInCurrentQuarter += appsByMonth.get(i).getValue();
			}
			appsByQuarter.add(new StatisticalDataDTO(currentQuarter + "-" + year, (double) appCountInCurrentQuarter));
			if(appsByMonth.size() - monthOfCurrentQuarter > 0) {
				for(int i = appsByMonth.size() - monthOfCurrentQuarter - 1; i >= 2; i -= 3) {
					if (currentQuarter <= 1) {
						currentQuarter = 4;
						year--;
					} else {
						currentQuarter--;
					}
					appsByQuarter.add(new StatisticalDataDTO(currentQuarter + "-" + year, appsByMonth.get(i).getValue() + appsByMonth.get(i-1).getValue() + appsByMonth.get(i-2).getValue()));
				}
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
			if(remainingMonths > 0)
				appsByQuarter.add(new StatisticalDataDTO(currentQuarter + "-" + year, (double) appsInRemainingQuarter));
			Collections.reverse(appsByQuarter);
		}
		return appsByQuarter;
	}

	@Override
	public List<StatisticalDataDTO> getAppointmentCountByYear(Pharmacy pharmacy) {
		List<StatisticalDataDTO> appCount = new ArrayList<StatisticalDataDTO>();
		List<PharmacistAppointment> allPassedPharmAppointments = pharmAppRepository.findAll().stream()
															.filter(a -> a.getPharmacist().getEngegementInPharmacy().getPharmacy().getId() == pharmacy.getId() 
															&& a.getStartDateTime().isBefore(LocalDateTime.now())).collect(Collectors.toList());
		List<DermatologistAppointment> allPassedDermAppointments = dermAppRepository.findAll().stream()
				.filter(a -> a.getPharmacy().getId() == pharmacy.getId()
				&& a.getStartDateTime().isBefore(LocalDateTime.now())
				&& a.isScheduled()).collect(Collectors.toList());
		allPassedPharmAppointments.sort((a, b) -> a.getStartDateTime().compareTo(b.getStartDateTime()));
		allPassedDermAppointments.sort((a, b) -> a.getStartDateTime().compareTo(b.getStartDateTime()));
		if(allPassedPharmAppointments.size() > 0 || allPassedDermAppointments.size() > 0) {
			LocalDate minDate = allPassedPharmAppointments.get(0).getStartDateTime().toLocalDate();
			LocalDate minDate2 = allPassedDermAppointments.get(0).getStartDateTime().toLocalDate();
			if(minDate2.isBefore(minDate))
				minDate = minDate2;
			while(minDate.getYear() <= LocalDate.now().getYear()) {
				LocalDate date = minDate;
				appCount.add(new StatisticalDataDTO(minDate.getYear() + ".", (double) allPassedPharmAppointments.stream().filter(a -> a.getStartDateTime().getYear() == date.getYear()).count() + allPassedDermAppointments.stream().filter(a -> a.getStartDateTime().getYear() == date.getYear()).count()));
				minDate = minDate.plusYears(1);
			}
		}
		return appCount;
	}
}
