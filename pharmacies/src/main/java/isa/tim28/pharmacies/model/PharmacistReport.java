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
public class PharmacistReport {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private PharmacistAppointment appointment;
	
	@Column(name = "diagnosis", nullable = false)
	private String diagnosis;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Therapy> therapies =  new HashSet<Therapy>();
	
	public PharmacistReport() {
		super();
	}

	public PharmacistReport(long id, PharmacistAppointment appointment, String diagnosis, Set<Therapy> therapies) {
		super();
		this.id = id;
		this.appointment = appointment;
		this.diagnosis = diagnosis;
		this.therapies = therapies;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public PharmacistAppointment getAppointment() {
		return appointment;
	}

	public void setAppointment(PharmacistAppointment appointment) {
		this.appointment = appointment;
	}

	public String getDiagnosis() {
		return diagnosis;
	}

	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}

	public Set<Therapy> getTherapies() {
		return therapies;
	}

	public void setTherapies(Set<Therapy> therapies) {
		this.therapies = therapies;
	}

}
