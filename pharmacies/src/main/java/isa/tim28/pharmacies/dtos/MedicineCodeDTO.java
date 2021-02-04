package isa.tim28.pharmacies.dtos;

public class MedicineCodeDTO {
	private String code; 
	private String name;
	
	
	public MedicineCodeDTO() {
		super();
	}
	public MedicineCodeDTO(String code, String name) {
		super();
		this.code = code;
		this.name = name;
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
	
	
}
