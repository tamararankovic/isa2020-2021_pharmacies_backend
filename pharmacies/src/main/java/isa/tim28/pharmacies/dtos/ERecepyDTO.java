package isa.tim28.pharmacies.dtos;

import java.util.List;

public class ERecepyDTO {
	private List<String> medicineCodes;
	private long pharmacyId;
	private double totalPrice;
	private double averageRating;
	private String nameOfPharmacy;
	private String addressOfPharmacy;
	
	public ERecepyDTO() {
		super();
	}
	public ERecepyDTO(List<String> medicineCodes, long pharmacyId, double totalPrice, double averageRating, String nameOfPharmacy,
			String addressOfPharmacy) {
		super();
		this.medicineCodes = medicineCodes;
		this.pharmacyId = pharmacyId;
		this.totalPrice = totalPrice;
		this.averageRating = averageRating;
		this.nameOfPharmacy = nameOfPharmacy;
		this.addressOfPharmacy = addressOfPharmacy;
	}
	public List<String> getMedicineCodes() {
		return medicineCodes;
	}
	public void setMedicineCodes(List<String> medicineCodes) {
		this.medicineCodes = medicineCodes;
	}
	public long getPharmacyId() {
		return pharmacyId;
	}
	public void setPharmacyId(long pharmacyId) {
		this.pharmacyId = pharmacyId;
	}
	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public double getAverageRating() {
		return averageRating;
	}
	public void setAverageRating(double averageRating) {
		this.averageRating = averageRating;
	}
	public String getNameOfPharmacy() {
		return nameOfPharmacy;
	}
	public void setNameOfPharmacy(String nameOfPharmacy) {
		this.nameOfPharmacy = nameOfPharmacy;
	}
	public String getAddressOfPharmacy() {
		return addressOfPharmacy;
	}
	public void setAddressOfPharmacy(String addressOfPharmacy) {
		this.addressOfPharmacy = addressOfPharmacy;
	}
	
	
}
