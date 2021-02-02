package isa.tim28.pharmacies.dtos;

public class MedicineDTO {
	
	private long id;
	private String name;
	private String manufacturer;
	
	public MedicineDTO() {
		super();
	}

	public MedicineDTO(long id, String name, String manufacturer) {
		super();
		this.id = id;
		this.name = name;
		this.manufacturer = manufacturer;
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

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	
}
