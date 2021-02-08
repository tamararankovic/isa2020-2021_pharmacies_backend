package isa.tim28.pharmacies.dtos;

public class PatientSearchDTO {
	
	private long id;
	private String name;
	private String surname;
	
	public PatientSearchDTO() {
		super();
	}

	public PatientSearchDTO(long id, String name, String surname) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
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
}
