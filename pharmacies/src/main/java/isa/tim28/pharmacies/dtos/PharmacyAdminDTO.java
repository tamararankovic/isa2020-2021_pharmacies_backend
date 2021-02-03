package isa.tim28.pharmacies.dtos;

public class PharmacyAdminDTO {

	private String name;
	private String surname;
	private String email;
	
	public PharmacyAdminDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PharmacyAdminDTO(String name, String surname, String email) {
		super();
		this.name = name;
		this.surname = surname;
		this.email = email;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
