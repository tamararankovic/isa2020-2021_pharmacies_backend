package isa.tim28.pharmacies.dtos;

public class DermPharmacyDTO {
	
	private long pharmacyId;
	private String name;
	
	public DermPharmacyDTO() {
		super();
	}

	public DermPharmacyDTO(long pharmacyId, String name) {
		super();
		this.pharmacyId = pharmacyId;
		this.name = name;
	}

	public long getPharmacyId() {
		return pharmacyId;
	}

	public void setPharmacyId(long pharmacyId) {
		this.pharmacyId = pharmacyId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
