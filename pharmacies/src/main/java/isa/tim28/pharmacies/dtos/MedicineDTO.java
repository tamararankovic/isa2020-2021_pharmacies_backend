package isa.tim28.pharmacies.dtos;

import java.util.ArrayList;

import java.util.List;

public class MedicineDTO {
	private String code; 
	private String name; 
	private String type; 
	private String form;
	private List<String> ingredients = new ArrayList<String>(); 
	private String manufacturer;
	private boolean withPrescription;
	private String additionalInfo;
	private List<String> compatibleMedicineCodes = new ArrayList<String>(); 
	private int points;
	private String sideEffects;
	private int advisedDailyDose;
	
	public MedicineDTO() {
		super();
	}
	
	public MedicineDTO(String code, String name, String type, String form, List<String> ingredients,
			String manufacturer, boolean withPrescription, String additionalInfo, List<String> compatibleMedicineCodes,
			int points, String sideEffects, int advisedDailyDose) {
		super();
		this.code = code;
		this.name = name;
		this.type = type;
		this.form = form;
		this.ingredients = ingredients;
		this.manufacturer = manufacturer;
		this.withPrescription = withPrescription;
		this.additionalInfo = additionalInfo;
		this.compatibleMedicineCodes = compatibleMedicineCodes;
		this.points = points;
		this.sideEffects = sideEffects;
		this.advisedDailyDose = advisedDailyDose;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getForm() {
		return form;
	}
	public void setForm(String form) {
		this.form = form;
	}
	public List<String> getIngredients() {
		return ingredients;
	}
	public void setIngredients(List<String> ingredients) {
		this.ingredients = ingredients;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public boolean getWithPrescription() {
		return withPrescription;
	}
	public void setWithPrescription(boolean withPrescription) {
		this.withPrescription = withPrescription;
	}
	public String getAdditionalInfo() {
		return additionalInfo;
	}
	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}
	public List<String> getCompatibleMedicineCodes() {
		return compatibleMedicineCodes;
	}
	public void setCompatibleMedicineCodes(List<String> compatibleMedicineCodes) {
		this.compatibleMedicineCodes = compatibleMedicineCodes;
	}
	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
	}
	public String getSideEffects() {
		return sideEffects;
	}
	public void setSideEffects(String sideEffects) {
		this.sideEffects = sideEffects;
	}
	public int getAdvisedDailyDose() {
		return advisedDailyDose;
	}
	public void setAdvisedDailyDose(int advisedDailyDose) {
		this.advisedDailyDose = advisedDailyDose;
	} 
	
	
	
}
