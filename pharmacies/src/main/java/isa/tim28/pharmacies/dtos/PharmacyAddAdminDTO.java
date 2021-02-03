package isa.tim28.pharmacies.dtos;

public class PharmacyAddAdminDTO {
	private long id;
	private String name;
	private String description;
	private String address;
	
	public PharmacyAddAdminDTO() {
		super();
	}

	public PharmacyAddAdminDTO(long id, String name, String description, String address) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.address = address;
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
