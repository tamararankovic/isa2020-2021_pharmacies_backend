package isa.tim28.pharmacies.model;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Offer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Supplier supplier;
	
	private LocalDateTime deadline;
	
	@Column(name = "accepted", nullable = false)
	private boolean accepted;
	
	@Column(name = "totalPrice", nullable = false)
	private double totalPrice;

	public Offer() {
		super();
	}
	
	public Offer(long id, Supplier supplier, LocalDateTime deadline ,boolean accepted, double totalPrice) {
		super();
		this.id = id;
		this.supplier = supplier;
		this.deadline = deadline;
		this.accepted = accepted;
		this.totalPrice = totalPrice;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public boolean isAccepted() {
		return accepted;
	}

	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public LocalDateTime getDeadline() {
		return deadline;
	}

	public void setDeadline(LocalDateTime deadline) {
		this.deadline = deadline;
	}

}
