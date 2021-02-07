package isa.tim28.pharmacies.dtos;

public class CompatibleMedicinesDTO {
	private String medicineName;
	private String medicineCode;
	
	public CompatibleMedicinesDTO() {
		super();
	}
	
	public CompatibleMedicinesDTO(String medicineName, String medicineCode) {
		super();
		this.medicineName = medicineName;
		this.medicineCode = medicineCode;
	}
	public String getMedicineName() {
		return medicineName;
	}
	public void setMedicineName(String medicineName) {
		this.medicineName = medicineName;
	}
	public String getMedicineCode() {
		return medicineCode;
	}
	public void setMedicineCode(String medicineCode) {
		this.medicineCode = medicineCode;
	}
	
	
	
}
