package isa.tim28.pharmacies.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import isa.tim28.pharmacies.exceptions.NewOrderInvalidException;

@Entity
@Table(name = "orders")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<MedicineQuantity> medicineQuantities = new HashSet<MedicineQuantity>();
	
	private LocalDateTime deadline;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Offer> offers = new HashSet<Offer>();
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private PharmacyAdmin adminCreator;
	
	public Order() {
		super();
	}

	public Order(long id, Set<MedicineQuantity> medicineQuantities, LocalDateTime deadline, Set<Offer> offers,
			PharmacyAdmin adminCreator) {
		super();
		this.id = id;
		this.medicineQuantities = medicineQuantities;
		this.deadline = deadline;
		this.offers = offers;
		this.adminCreator = adminCreator;
	}
	
	public Order(Set<MedicineQuantity> medicineQuantities, LocalDateTime deadline,
			PharmacyAdmin adminCreator) throws NewOrderInvalidException {
		if (deadline.isBefore(LocalDateTime.now()))
			throw new NewOrderInvalidException("Deadline set in the past!");
		for (MedicineQuantity mq : medicineQuantities) {
			if(mq.getQuantity() <= 0 || mq.getQuantity() >= 1000)
				throw new NewOrderInvalidException("Quantity must be a number between 1 and 999!");
		}
		this.medicineQuantities = medicineQuantities;
		this.deadline = deadline;
		this.adminCreator = adminCreator;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Set<MedicineQuantity> getMedicineQuantities() {
		return medicineQuantities;
	}

	public void setMedicineQuantities(Set<MedicineQuantity> medicineQuantities) {
		this.medicineQuantities = medicineQuantities;
	}

	public LocalDateTime getDeadline() {
		return deadline;
	}

	public void setDeadline(LocalDateTime deadline) {
		this.deadline = deadline;
	}

	public Set<Offer> getOffers() {
		return offers;
	}

	public void setOffers(Set<Offer> offers) {
		this.offers = offers;
	}

	public PharmacyAdmin getAdminCreator() {
		return adminCreator;
	}

	public void setAdminCreator(PharmacyAdmin adminCreator) {
		this.adminCreator = adminCreator;
	}
}
