package isa.tim28.pharmacies.dtos;

public class PatientSearchDTO {
	
	public String name;
	public String surname;
	
	public PatientSearchDTO() {
		super();
	}

	public PatientSearchDTO(String name, String surname) {
		super();
		this.name = name;
		this.surname = surname;
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
}
