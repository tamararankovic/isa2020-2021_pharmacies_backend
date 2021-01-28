package isa.tim28.pharmacies.model;

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
import javax.persistence.OneToOne;

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
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private PriceList priceList;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Rating> ratings = new HashSet<Rating>();

	public Pharmacy() {
		super();
	}
	
	public Pharmacy(long id, String name, String address, String description, Set<MedicineQuantity> medicines,
			PriceList priceList, Set<Rating> ratings) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.description = description;
		this.medicines = medicines;
		this.priceList = priceList;
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

	public void setName(String name) {
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

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<MedicineQuantity> getMedicines() {
		return medicines;
	}

	public void setMedicines(Set<MedicineQuantity> medicines) {
		this.medicines = medicines;
	}

	public PriceList getPriceList() {
		return priceList;
	}

	public void setPriceList(PriceList priceList) {
		this.priceList = priceList;
	}

	public Set<Rating> getRatings() {
		return ratings;
	}

	public void setRatings(Set<Rating> ratings) {
		this.ratings = ratings;
	}
}
