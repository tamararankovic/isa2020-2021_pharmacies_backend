package isa.tim28.pharmacies.dtos;

import java.util.HashSet;
import java.util.Set;

public class DermatologistDTO {
	
	private long id;
	private String name;
	private String surname;
	private double avgRating;
	private Set<String> pharmacies = new HashSet<String>();
	
	public DermatologistDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DermatologistDTO(long id, String name, String surname, double avgRating, Set<String> pharmacies) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.avgRating = avgRating;
		this.pharmacies = pharmacies;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public double getAvgRating() {
		return avgRating;
	}

	public void setAvgRating(double avgRating) {
		this.avgRating = avgRating;
	}

	public Set<String> getPharmacies() {
		return pharmacies;
	}

	public void setPharmacies(Set<String> pharmacies) {
		this.pharmacies = pharmacies;
	}
}
