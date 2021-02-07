package isa.tim28.pharmacies.model;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import isa.tim28.pharmacies.exceptions.ForbiddenOperationException;
import isa.tim28.pharmacies.exceptions.PharmacyDataInvalidException;

@Entity
public class Pharmacy {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "address", nullable = false)
	private String address;
	
	@Column(name = "description", nullable = false)
	private String description;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<MedicineQuantity> medicines = new HashSet<MedicineQuantity>();
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<PriceList> priceLists;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Rating> ratings = new HashSet<Rating>();

	public Pharmacy() {
		super();
	}
	
	public Pharmacy(long id, String name, String address, String description, Set<MedicineQuantity> medicines,
			Set<PriceList> priceLists, Set<Rating> ratings) throws PharmacyDataInvalidException {
		if(name.length() < 1 || name.length() > 50)
			throw new PharmacyDataInvalidException("Pharmacy name must have between 1 and 50 characters");
		if(description.length() > 1000)
			throw new PharmacyDataInvalidException("Description mustn't be longer than 1000 characters");
		this.id = id;
		this.name = name;
		this.address = address;
		this.description = description;
		this.medicines = medicines;
		this.priceLists = priceLists;
		this.ratings = ratings;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) throws PharmacyDataInvalidException {
		if(name.length() < 1 || name.length() > 50)
			throw new PharmacyDataInvalidException("Pharmacy name must have between 1 and 50 characters");
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) throws PharmacyDataInvalidException {
		if(description.length() > 1000)
			throw new PharmacyDataInvalidException("Description mustn't be longer than 1000 characters");
		this.description = description;
	}

	public Set<MedicineQuantity> getMedicines() {
		return medicines;
	}

	public void setMedicines(Set<MedicineQuantity> medicines) {
		this.medicines = medicines;
	}

	public Set<PriceList> getPriceLists() {
		return priceLists;
	}

	public void setPriceLists(Set<PriceList> priceLists) {
		this.priceLists = priceLists;
	}

	public Set<Rating> getRatings() {
		return ratings;
	}

	public void setRatings(Set<Rating> ratings) {
		this.ratings = ratings;
	}
	
	public boolean offers(Medicine medicine) {
		return medicines.stream().anyMatch(mq -> mq.getMedicine().getId() == medicine.getId());
	}
	
	public void addMedicine(MedicineQuantity medicine) throws ForbiddenOperationException {
		Optional<MedicineQuantity> mq = medicines.stream().filter(m -> m.getMedicine().getId() == medicine.getMedicine().getId()).findFirst();
		if(mq.isEmpty())
			throw new ForbiddenOperationException("Can't add quantity to medicine that is not offered!");
		mq.get().setQuantity(mq.get().getQuantity() + medicine.getQuantity());
	}

	public boolean isNameValid() {
		if(this.name == "" || this.name.length() < 2 || this.name.length() > 50) return false;
		return true;
	}
	
	public boolean isAddressValid() {
		if(this.address == "" || this.address.length() < 2 || this.address.length() > 50) return false;
		return true;
	}
	
	public boolean isDescriptionValid() {
		if(this.description == "" || this.description.length() < 2 || this.description.length() > 1000) return false;
		return true;
	}
	
	public Set<Medicine> getAllOfferedMedicines() {
		Set<Medicine> ret = new HashSet<Medicine>();
		for(MedicineQuantity mq : medicines) {
			ret.add(mq.getMedicine());
		}
		return ret;
	}
	
	public double getCurrentPrice(Medicine medicine) {
		for(PriceList pl : getPriceListsSortedByStartDateDescending(priceLists)) {
			if(pl.isInPriceList(medicine)) {
				return pl.getPrice(medicine);
			}
		}
		return 0;
	}
	
	public double getPharmacistAppointmentCurrentPrice() {
		for(PriceList pl : getPriceListsSortedByStartDateDescending(priceLists)) {
			if(pl.isPharmAppPriceDefined()) {
				return pl.getPharmacistAppointmentPrice();
			}
		}
		return 0;
	}
	
	public double getDermatologistAppointmentCurrentPrice() {
		for(PriceList pl : getPriceListsSortedByStartDateDescending(priceLists)) {
			if(pl.isDermAppPriceDefined()) {
				return pl.getDefaultDermatologistAppointmentPrice();
			}
		}
		return 0;
	}
	
	public boolean isPriceDefined(Medicine medicine) {
		for(PriceList pl : getPriceListsSortedByStartDateDescending(priceLists)) {
			if(pl.isInPriceList(medicine))
				return true;
		}
		return false;
	}
	
	public boolean isPharmacistAppointmentPriceDefined() {
		for(PriceList pl : getPriceListsSortedByStartDateDescending(priceLists)) {
			if(pl.isPharmAppPriceDefined())
				return true;
		}
		return false;
	}
	
	public boolean isDermatologistAppointmentPriceDefined() {
		for(PriceList pl : getPriceListsSortedByStartDateDescending(priceLists)) {
			if(pl.isDermAppPriceDefined())
				return true;
		}
		return false;
	}
	
	public List<PriceList> getPriceListsSortedByStartDateDescending(Set<PriceList> priceLists) {
		List<PriceList> orderedPriceLists = priceLists.stream().collect(Collectors.toList());
		Collections.sort(orderedPriceLists);
		return orderedPriceLists;
	}
	
	public boolean hasPriceListDefinedOnDate(LocalDate date) {
		return !priceLists.stream().filter(p -> p.getStartDate().equals(date)).findFirst().isEmpty();
	}
	
	public PriceList getPriceListDefinedOnDate(LocalDate date) {
		if (!hasPriceListDefinedOnDate(date))
			return null;
		else
			return priceLists.stream().filter(p -> p.getStartDate().equals(date)).findFirst().get();
	}
	
	public double getAvgRating() {
		double sum = 0;
		for(Rating r : ratings)
			sum += r.getRating();
		return ratings.size() > 0 ? sum / ratings.size() : 0;
	}
	
	public double getPharmacistAppointmentPriceOnDate(LocalDate date) {
		Set<PriceList> validPriceLists = priceLists.stream().filter(pl -> pl.getStartDate().isBefore(date)).collect(Collectors.toSet());
		for(PriceList pl : getPriceListsSortedByStartDateDescending(validPriceLists)) {
			if(pl.isPharmAppPriceDefined()) {
				return pl.getPharmacistAppointmentPrice();
			}
		}
		return 0;
	}
	
	public double getDermatologistAppointmentPriceOnDate(LocalDate date) {
		Set<PriceList> validPriceLists = priceLists.stream().filter(pl -> pl.getStartDate().isBefore(date)).collect(Collectors.toSet());
		for(PriceList pl : getPriceListsSortedByStartDateDescending(validPriceLists)) {
			if(pl.isDermAppPriceDefined()) {
				return pl.getDefaultDermatologistAppointmentPrice();
			}
		}
		return 0;
	}
	
	public double getMedicinePriceOnDate(LocalDate date, Medicine medicine) {
		Set<PriceList> validPriceLists = priceLists.stream().filter(pl -> pl.getStartDate().isBefore(date)).collect(Collectors.toSet());
		for(PriceList pl : getPriceListsSortedByStartDateDescending(validPriceLists)) {
			if(pl.isInPriceList(medicine)) {
				return pl.getPrice(medicine);
			}
		}
		return 0;
	}
}
