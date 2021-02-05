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
public class CancelledReservation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Patient patient;
	
	@Column(name = "pharmacy", nullable = false)
	private String pharmacy;
	
	@Column(name = "medicine", nullable = false)
	private String medicine;
	
	@Column(name = "dueDate", nullable = false)
	private LocalDateTime dueDate;
	
	@Column(name = "status", nullable = false)
	private ReservationStatus status;
	
	@Column(name = "received", nullable = false)
	private boolean received;
	
	public CancelledReservation() {
		super();
	}

	public CancelledReservation(long id, Patient patient, String pharmacy, String medicine, LocalDateTime dueDate,
			ReservationStatus status, boolean received) {
		super();
		this.id = id;
		this.patient = patient;
		this.pharmacy = pharmacy;
		this.medicine = medicine;
		this.dueDate = dueDate;
		this.status = status;
		this.received = received;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public String getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(String string) {
		this.pharmacy = string;
	}

	public String getMedicine() {
		return medicine;
	}

	public void setMedicine(String medicine) {
		this.medicine = medicine;
	}

	public LocalDateTime getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDateTime dueDate) {
		this.dueDate = dueDate;
	}

	public ReservationStatus getStatus() {
		return status;
	}

	public void setStatus(ReservationStatus status) {
		this.status = status;
	}

	public boolean isReceived() {
		return received;
	}

	public void setReceived(boolean received) {
		this.received = received;
	}
	
	
}
