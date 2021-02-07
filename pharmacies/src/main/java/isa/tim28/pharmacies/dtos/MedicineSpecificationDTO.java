package isa.tim28.pharmacies.dtos;

import java.util.Set;

public class MedicineSpecificationDTO {
	private String sideEffects;
	private int advisedDailyDose;
	private Set<String> ingredients;
	private Set<CompatibleMedicinesDTO> compatibleMedicines;
	
	
	
	public MedicineSpecificationDTO() {
		super();
	}
	public MedicineSpecificationDTO(String sideEffects, int advisedDailyDose, Set<String> ingredients,
			Set<CompatibleMedicinesDTO> compatibleMedicines) {
		super();
		this.sideEffects = sideEffects;
		this.advisedDailyDose = advisedDailyDose;
		this.ingredients = ingredients;
		this.compatibleMedicines = compatibleMedicines;
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
	public Set<String> getIngredients() {
		return ingredients;
	}
	public void setIngredients(Set<String> ingredients) {
		this.ingredients = ingredients;
	}
	public Set<CompatibleMedicinesDTO> getCompatibleMedicines() {
		return compatibleMedicines;
	}
	public void setCompatibleMedicines(Set<CompatibleMedicinesDTO> compatibleMedicines) {
		this.compatibleMedicines = compatibleMedicines;
	}
	
	
	
}
