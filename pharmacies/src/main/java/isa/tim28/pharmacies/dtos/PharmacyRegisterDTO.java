package isa.tim28.pharmacies.dtos;

public class PharmacyRegisterDTO {
	private String name;
	private String description;
	private String address;
	
	public PharmacyRegisterDTO() {
		super();
	}
	
	public PharmacyRegisterDTO(String name, String description, String address) {
		super();
		this.name = name;
		this.description = description;
		this.address = address;
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
	
	
}
