package isa.tim28.pharmacies.dtos;

import java.util.HashSet;
import java.util.Set;

public class PharmacyInfoForPatientDTO {

	private String name;
	private String description;
	private String address;
	private double avgRating;
	private Set<String> pharmacists = new HashSet<String>();
	private Set<String> dermatologists = new HashSet<String>();
	private Set<String> medicines = new HashSet<String>();
	private Set<DermatologistExaminationForPatientDTO> examinations = new HashSet<DermatologistExaminationForPatientDTO>();
	
	public PharmacyInfoForPatientDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PharmacyInfoForPatientDTO(String name, String description, String address, double avgRating,
			Set<String> pharmacists, Set<String> dermatologists, Set<String> medicines,
			Set<DermatologistExaminationForPatientDTO> examinations) {
		super();
		this.name = name;
		this.description = description;
		this.address = address;
		this.avgRating = avgRating;
		this.pharmacists = pharmacists;
		this.dermatologists = dermatologists;
		this.medicines = medicines;
		this.examinations = examinations;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public double getAvgRating() {
		return avgRating;
	}

	public void setAvgRating(double avgRating) {
		this.avgRating = avgRating;
	}

	public Set<String> getPharmacists() {
		return pharmacists;
	}

	public void setPharmacists(Set<String> pharmacists) {
		this.pharmacists = pharmacists;
	}

	public Set<String> getDermatologists() {
		return dermatologists;
	}

	public void setDermatologists(Set<String> dermatologists) {
		this.dermatologists = dermatologists;
	}

	public Set<String> getMedicines() {
		return medicines;
	}

	public void setMedicines(Set<String> medicines) {
		this.medicines = medicines;
	}

	public Set<DermatologistExaminationForPatientDTO> getExaminations() {
		return examinations;
	}

	public void setExaminations(Set<DermatologistExaminationForPatientDTO> examinations) {
		this.examinations = examinations;
	}
}
