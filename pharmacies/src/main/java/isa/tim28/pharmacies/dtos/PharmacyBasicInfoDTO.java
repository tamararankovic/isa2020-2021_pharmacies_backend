package isa.tim28.pharmacies.dtos;

public class PharmacyBasicInfoDTO {
	
	private String name;
	private String description;
	private String address;
	
	public PharmacyBasicInfoDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public PharmacyBasicInfoDTO(String name, String description, String address) {
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
