package isa.tim28.pharmacies.dtos;

public class PharmacyAdminRegisterDTO {
	private String email;
	private String password;
	private String name;
	private String surname;
	private long idOfPharmacy;
	
	public PharmacyAdminRegisterDTO() {
		super();
	}
	public PharmacyAdminRegisterDTO(String email, String password, String name, String surname, long idOfPharmacy) {
		super();
		this.email = email;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.idOfPharmacy = idOfPharmacy;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	public long getIdOfPharmacy() {
		return idOfPharmacy;
	}
	public void setIdOfPharmacy(long idOfPharmacy) {
		this.idOfPharmacy = idOfPharmacy;
	}
	
	
}
