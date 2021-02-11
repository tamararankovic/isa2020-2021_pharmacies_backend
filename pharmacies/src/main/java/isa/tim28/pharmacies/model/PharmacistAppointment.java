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
public class PharmacistAppointment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	private Patient patient;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	private Pharmacist pharmacist;
	
	@Column(name = "startDateTime", nullable = false)
	private LocalDateTime startDateTime;
	
	@Column(name = "patientWasPresent", nullable = false)
	private boolean patientWasPresent;
	
	@Column(name = "defaultDurationInMinutes", nullable = false)
	private final int defaultDurationMinutes = 30;
	
	@Column(name = "done", nullable = false)
	private boolean done = false;
	
	@Column(name = "pointsAfterAdvising", nullable = false)
	private int pointsAfterAdvising = 0;
	
	@Column(name = "price", nullable = false)
	private double price;

	public PharmacistAppointment() {
		super();
	}
	
	public PharmacistAppointment(long id, Patient patient, Pharmacist pharmacist, LocalDateTime startDateTime,
			boolean patientWasPresent, boolean done) {
		super();
		this.id = id;
		this.patient = patient;
		this.pharmacist = pharmacist;
		this.startDateTime = startDateTime;
		this.patientWasPresent = patientWasPresent;
		this.done = done;
	}

	public PharmacistAppointment(long id, Patient patient, Pharmacist pharmacist, LocalDateTime startDateTime,
			boolean patientWasPresent) {
		super();
		this.id = id;
		this.patient = patient;
		this.pharmacist = pharmacist;
		this.startDateTime = startDateTime;
		this.patientWasPresent = patientWasPresent;
	}

	public PharmacistAppointment(long id, Patient patient, Pharmacist pharmacist, LocalDateTime startDateTime,
			boolean patientWasPresent, boolean done, int pointsAfterAdvising) {
		super();
		this.id = id;
		this.patient = patient;
		this.pharmacist = pharmacist;
		this.startDateTime = startDateTime;
		this.patientWasPresent = patientWasPresent;
		this.done = done;
		this.pointsAfterAdvising = pointsAfterAdvising;
	}
	
	
	public PharmacistAppointment(long id, Patient patient, Pharmacist pharmacist, LocalDateTime startDateTime,
			boolean patientWasPresent, boolean done, int pointsAfterAdvising, double price) {
		super();
		this.id = id;
		this.patient = patient;
		this.pharmacist = pharmacist;
		this.startDateTime = startDateTime;
		this.patientWasPresent = patientWasPresent;
		this.done = done;
		this.pointsAfterAdvising = pointsAfterAdvising;
		this.price = price;
	}

	
	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getPointsAfterAdvising() {
		return pointsAfterAdvising;
	}

	public void setPointsAfterAdvising(int pointsAfterAdvising) {
		this.pointsAfterAdvising = pointsAfterAdvising;
	}

	public int getDefaultDurationMinutes() {
		return defaultDurationMinutes;
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

	public Pharmacist getPharmacist() {
		return pharmacist;
	}

	public void setPharmacist(Pharmacist pharmacist) {
		this.pharmacist = pharmacist;
	}

	public LocalDateTime getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(LocalDateTime startDateTime) {
		this.startDateTime = startDateTime;
	}

	public boolean isPatientWasPresent() {
		return patientWasPresent;
	}

	public void setPatientWasPresent(boolean patientWasPresent) {
		this.patientWasPresent = patientWasPresent;
	}
	
	public int getDefaultDurationInMinutes() {
		return defaultDurationMinutes;
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

}
