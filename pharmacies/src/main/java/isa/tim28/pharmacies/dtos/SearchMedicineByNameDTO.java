package isa.tim28.pharmacies.dtos;

public class SearchMedicineByNameDTO {
	private String name;

	public SearchMedicineByNameDTO() {
		super();
	}

	public SearchMedicineByNameDTO(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
