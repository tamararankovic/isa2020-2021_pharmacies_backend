package isa.tim28.pharmacies.dtos;

import java.util.ArrayList;
import java.util.List;

import isa.tim28.pharmacies.model.Medicine;

public class MedicineDetailsDTO {
	private String name;
	private String code;
	private String type;
	private String form;
	private boolean withPrescription;
	private List<String> ingredients;
	private String manufacturer;
	private String additionalInfo;
	private int points;
	private String sideEffects;
	private int advisedDailyDose;
	
	public MedicineDetailsDTO() {
		super();
	}

	public MedicineDetailsDTO(String name, String code, String type, String form, boolean withPrescription,
			List<String> ingredients, String manufacturer, String additionalInfo, int points, String sideEffects,
			int advisedDailyDose) {
		super();
		this.name = name;
		this.code = code;
		this.type = type;
		this.form = form;
		this.withPrescription = withPrescription;
		this.ingredients = ingredients;
		this.manufacturer = manufacturer;
		this.additionalInfo = additionalInfo;
		this.points = points;
		this.sideEffects = sideEffects;
		this.advisedDailyDose = advisedDailyDose;
	}
	
	
	public MedicineDetailsDTO(Medicine medicine) {
		super();
		this.name = medicine.getName();
		this.code = medicine.getCode();
		this.type = medicine.getType().toString();
		this.form = medicine.getForm().toString();
		this.withPrescription = medicine.isWithPrescription();
		this.manufacturer = medicine.getManufacturer();
		this.additionalInfo = medicine.getAdditionalInfo();
		this.points = medicine.getPoints();
		this.sideEffects = medicine.getSideEffects();
		this.advisedDailyDose = medicine.getAdvisedDailyDose();
		this.ingredients = new ArrayList<String>();
		
		for(String ingredient : medicine.getIngredients()) {
			this.ingredients.add(ingredient);
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public boolean isWithPrescription() {
		return withPrescription;
	}

	public void setWithPrescription(boolean withPrescription) {
		this.withPrescription = withPrescription;
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

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
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
