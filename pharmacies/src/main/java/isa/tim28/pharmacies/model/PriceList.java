package isa.tim28.pharmacies.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class PriceList {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<MedicinePrice> medicinePrices = new HashSet<MedicinePrice>();

	public PriceList() {
		super();
	}
	
	public PriceList(long id, Set<MedicinePrice> medicinePrices) {
		super();
		this.id = id;
		this.medicinePrices = medicinePrices;
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

	
}
