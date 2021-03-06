package isa.tim28.pharmacies.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class EPrescription {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "code", nullable = false)
	private String code;
	
	@Column(name = "patientFullName", nullable = false)
	private String patientFullName;
	
	@Column(name = "datePrescribed", nullable = false)
	private LocalDate datePrescribed;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<EPrescriptionMedicine> prescriptionMedicine = new HashSet<EPrescriptionMedicine>();

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Pharmacy pharmacy;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Patient patient;
	
	public EPrescription() {
		super();
	}
	
	public EPrescription(long id, String code, String patientFullName, LocalDate datePrescribed,
			Set<EPrescriptionMedicine> prescriptionMedicine, Pharmacy pharmacy, Patient patient) {
		super();
		this.id = id;
		this.code = code;
		this.patientFullName = patientFullName;
		this.datePrescribed = datePrescribed;
		this.prescriptionMedicine = prescriptionMedicine;
		this.pharmacy = pharmacy;
		this.patient = patient;
	}
	
	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Pharmacy getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(Pharmacy pharmacy) {
		this.pharmacy = pharmacy;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPatientFullName() {
		return patientFullName;
	}

	public void setPatientFullName(String patientFullName) {
		this.patientFullName = patientFullName;
	}

	public LocalDate getDatePrescribed() {
		return datePrescribed;
	}

	public void setDatePrescribed(LocalDate datePrescribed) {
		this.datePrescribed = datePrescribed;
	}

	public Set<EPrescriptionMedicine> getPrescriptionMedicine() {
		return prescriptionMedicine;
	}

	public void setPrescriptionMedicine(Set<EPrescriptionMedicine> prescriptionMedicine) {
		this.prescriptionMedicine = prescriptionMedicine;
	}

}
