package isa.tim28.pharmacies.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import isa.tim28.pharmacies.exceptions.PriceInvalidException;

@Entity
public class PriceList  implements Comparable<PriceList> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<MedicinePrice> medicinePrices = new HashSet<MedicinePrice>();
	
	@Column(name = "pharm_app_price", nullable = false)
	private double pharmacistAppointmentPrice;
	
	@Column(name = "derm_app_price", nullable = false)
	private double defaultDermatologistAppointmentPrice;
	
	@Column(name = "pharm_app_price_defined", nullable = false)
	private boolean pharmAppPriceDefined;
	
	@Column(name = "derm_app_price_defined", nullable = false)
	private boolean dermAppPriceDefined;
	
	@Column(name = "start_date", nullable = false)
	private LocalDate startDate;

	public PriceList() {
		super();
	}

	public PriceList(long id, Set<MedicinePrice> medicinePrices, double pharmacistAppointmentPrice,
			double defaultDermatologistAppointmentPrice, boolean pharmAppPriceDefined, boolean dermAppPriceDefined,
			LocalDate startDate) throws PriceInvalidException {
		if (!isPriceValid(defaultDermatologistAppointmentPrice) && dermAppPriceDefined) 
			throw new PriceInvalidException("Price must be a number between 1 and 100000");
		if (!isPriceValid(pharmacistAppointmentPrice) && pharmAppPriceDefined) 
			throw new PriceInvalidException("Price must be a number between 1 and 100000");
		this.id = id;
		this.medicinePrices = medicinePrices;
		this.pharmacistAppointmentPrice = pharmacistAppointmentPrice;
		this.defaultDermatologistAppointmentPrice = defaultDermatologistAppointmentPrice;
		this.pharmAppPriceDefined = pharmAppPriceDefined;
		this.dermAppPriceDefined = dermAppPriceDefined;
		this.startDate = startDate;
	}

	public PriceList(Set<MedicinePrice> medicinePrices, double pharmacistAppointmentPrice,
			double defaultDermatologistAppointmentPrice, boolean pharmAppPriceDefined, boolean dermAppPriceDefined,
			LocalDate startDate) throws PriceInvalidException {
		if (!isPriceValid(defaultDermatologistAppointmentPrice) && dermAppPriceDefined) 
			throw new PriceInvalidException("Price must be a number between 1 and 100000");
		if (!isPriceValid(pharmacistAppointmentPrice) && pharmAppPriceDefined) 
			throw new PriceInvalidException("Price must be a number between 1 and 100000");
		this.medicinePrices = medicinePrices;
		this.pharmacistAppointmentPrice = pharmacistAppointmentPrice;
		this.defaultDermatologistAppointmentPrice = defaultDermatologistAppointmentPrice;
		this.pharmAppPriceDefined = pharmAppPriceDefined;
		this.dermAppPriceDefined = dermAppPriceDefined;
		this.startDate = startDate;
	}
	
	private boolean isPriceValid (double price) {
		return price > 0 && price <= 100000;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Set<MedicinePrice> getMedicinePrices() {
		return medicinePrices;
	}

	public void setMedicinePrices(Set<MedicinePrice> medicinePrices) {
		this.medicinePrices = medicinePrices;
	}

	public double getPharmacistAppointmentPrice() {
		return pharmacistAppointmentPrice;
	}

	public void setPharmacistAppointmentPrice(double pharmacistAppointmentPrice) {
		this.pharmacistAppointmentPrice = pharmacistAppointmentPrice;
	}

	public double getDefaultDermatologistAppointmentPrice() {
		return defaultDermatologistAppointmentPrice;
	}

	public void setDefaultDermatologistAppointmentPrice(double defaultDermatologistAppointmentPrice) {
		this.defaultDermatologistAppointmentPrice = defaultDermatologistAppointmentPrice;
	}

	public boolean isPharmAppPriceDefined() {
		return pharmAppPriceDefined;
	}

	public void setPharmAppPriceDefined(boolean pharmAppPriceDefined) {
		this.pharmAppPriceDefined = pharmAppPriceDefined;
	}

	public boolean isDermAppPriceDefined() {
		return dermAppPriceDefined;
	}

	public void setDermAppPriceDefined(boolean dermAppPriceDefined) {
		this.dermAppPriceDefined = dermAppPriceDefined;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	@Override
	public int compareTo(PriceList o) {
		if (startDate.isBefore(o.getStartDate()))
			return 1;
		if (startDate.isAfter(o.getStartDate()))
			return -1;
		return 0;
	}
	
	public boolean isInPriceList(Medicine medicine) {
		for(MedicinePrice mp : medicinePrices) {
			if(mp.getMedicine().getId() == medicine.getId())
				return true;
		}
		return false;
	}
	
	public double getPrice(Medicine medicine) {
		for(MedicinePrice mp : medicinePrices) {
			if(mp.getMedicine().getId() == medicine.getId())
				return mp.getPrice();
		}
		return 0;
	}
	
	public void setPrice(Medicine medicine, double price) throws PriceInvalidException {
		if (isInPriceList(medicine)) {
			medicinePrices.stream().filter(mp -> mp.getMedicine().getId() == medicine.getId()).findFirst().get().setPrice(price);
		} else {
			medicinePrices.add(new MedicinePrice(medicine, price));
		}
	}
}
