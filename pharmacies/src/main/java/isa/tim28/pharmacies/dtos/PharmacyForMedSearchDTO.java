package isa.tim28.pharmacies.dtos;

public class PharmacyForMedSearchDTO {
	private String pharmacyName;
	private double medicinePrice;
	
	public PharmacyForMedSearchDTO() {
		super();
	}
	public PharmacyForMedSearchDTO(String pharmacyName, double medicinePrice) {
		super();
		this.pharmacyName = pharmacyName;
		this.medicinePrice = medicinePrice;
	}
	public String getPharmacyName() {
		return pharmacyName;
	}
	public void setPharmacyName(String pharmacyName) {
		this.pharmacyName = pharmacyName;
	}
	public double getMedicinePrice() {
		return medicinePrice;
	}
	public void setMedicinePrice(double medicinePrice) {
		this.medicinePrice = medicinePrice;
	}
	
	
}
