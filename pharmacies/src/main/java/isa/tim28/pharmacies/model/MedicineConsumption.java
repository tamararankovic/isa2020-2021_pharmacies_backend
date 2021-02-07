package isa.tim28.pharmacies.model;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import isa.tim28.pharmacies.exceptions.ForbiddenOperationException;

@Entity
public class MedicineConsumption {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	private Medicine medicine;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	private Pharmacy pharmacy;
	
	@Column(name = "quantity", nullable = false)
	private int quantity;
	
	@Column(name = "dateCreated", nullable = false)
	private LocalDate dateCreated;

	public MedicineConsumption(Medicine medicine, Pharmacy pharmacy, int quantity) throws ForbiddenOperationException {
		if(!pharmacy.offers(medicine))
			throw new ForbiddenOperationException("Pharmacy doesn't offer selected medicine");
		this.medicine = medicine;
		this.pharmacy = pharmacy;
		this.quantity = quantity;
		this.dateCreated = LocalDate.now();
	}

	public long getId() {
		return id;
	}

	public Medicine getMedicine() {
		return medicine;
	}

	public Pharmacy getPharmacy() {
		return pharmacy;
	}

	public int getQuantity() {
		return quantity;
	}

	public LocalDate getDateCreated() {
		return dateCreated;
	}
}
