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
public class DermatologistAppointment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	private Patient patient;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	private Dermatologist dermatologist;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	private Pharmacy pharmacy;
	
	@Column(name = "startDateTime", nullable = false)
	private LocalDateTime startDateTime;
	
	@Column(name = "durationInMinutes", nullable = false)
	private int durationInMinutes;
	
	@Column(name = "scheduled", nullable = false)
	private boolean scheduled;
	
	@Column(name = "price", nullable = false)
	private long price;
	
	@Column(name = "patientWasPresent", nullable = false)
	private boolean patientWasPresent;

	@Column(name = "defaultDurationInMinutes", nullable = false)
	private final int defaultDurationInMinutes = 30;
	
	public DermatologistAppointment() {
		super();
	}
	
	public DermatologistAppointment(long id, Patient patient, Dermatologist dermatologist, Pharmacy pharmacy,
			LocalDateTime startDateTime, int durationInMinutes, boolean scheduled, long price,
			boolean patientWasPresent) {
		super();
		this.id = id;
		this.patient = patient;
		this.dermatologist = dermatologist;
		this.pharmacy = pharmacy;
		this.startDateTime = startDateTime;
		this.durationInMinutes = durationInMinutes;
		this.scheduled = scheduled;
		this.price = price;
		this.patientWasPresent = patientWasPresent;
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

	public Dermatologist getDermatologist() {
		return dermatologist;
	}

	public void setDermatologist(Dermatologist dermatologist) {
		this.dermatologist = dermatologist;
	}

	public Pharmacy getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(Pharmacy pharmacy) {
		this.pharmacy = pharmacy;
	}

	public LocalDateTime getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(LocalDateTime startDateTime) {
		this.startDateTime = startDateTime;
	}

	public int getDurationInMinutes() {
		return durationInMinutes;
	}

	public void setDurationInMinutes(int durationInMinutes) {
		this.durationInMinutes = durationInMinutes;
	}

	public boolean isScheduled() {
		return scheduled;
	}

	public void setScheduled(boolean scheduled) {
		this.scheduled = scheduled;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public boolean isPatientWasPresent() {
		return patientWasPresent;
	}

	public void setPatientWasPresent(boolean patientWasPresent) {
		this.patientWasPresent = patientWasPresent;
	}
	
	public int getDefaultDurationInMinutes() {
		return defaultDurationInMinutes;
	}
	
}
