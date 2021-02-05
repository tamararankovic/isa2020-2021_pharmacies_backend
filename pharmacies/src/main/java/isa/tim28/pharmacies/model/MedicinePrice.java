package isa.tim28.pharmacies.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import isa.tim28.pharmacies.exceptions.PriceInvalidException;

@Entity
public class MedicinePrice {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Medicine medicine;
	
	@Column(name = "price", nullable = false)
	private double price;

	public MedicinePrice() {
		super();
	}
	
	public MedicinePrice(long id, Medicine medicine, double price) throws PriceInvalidException {
		if (!isPriceValid(price)) 
			throw new PriceInvalidException("Price must be a number between 1 and 100000");
		this.id = id;
		this.medicine = medicine;
		this.price = price;
	}
	
	public MedicinePrice(Medicine medicine, double price) throws PriceInvalidException {
		if (!isPriceValid(price)) 
			throw new PriceInvalidException("Price must be a number between 1 and 100000");
		this.medicine = medicine;
		this.price = price;
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

	public Medicine getMedicine() {
		return medicine;
	}

	public void setMedicine(Medicine medicine) {
		this.medicine = medicine;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
}
