package isa.tim28.pharmacies.dtos;

public class SearchMedicineDTO {
	
	private String code;
	private String name;
	private String manufacturer;
	
	public SearchMedicineDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public SearchMedicineDTO(String code, String name, String manufacturer) {
		super();
		this.code = code;
		this.name = name;
		this.manufacturer = manufacturer;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
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
