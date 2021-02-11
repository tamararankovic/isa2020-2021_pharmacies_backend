package isa.tim28.pharmacies.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Medicine {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "code", nullable = false)
	private String code;
	
	@Column(name = "type", nullable = false)
	private MedicineType type = MedicineType.ANESTHETIC;
	
	@Column(name = "form", nullable = false)
	private MedicineForm form= MedicineForm.CREAM;
	
	@Column(name = "withPrescription", nullable = false)
	private boolean withPrescription = true;
	
	@ElementCollection(fetch = FetchType.LAZY)
	private Set<String> ingredients = new HashSet<String>();
	
	@Column(name = "manufacturer", nullable = false)
	private String manufacturer;
	
	@ElementCollection(fetch = FetchType.LAZY)
	private Set<String> compatibleMedicineCodes = new HashSet<String>();
	
	@Column(name = "additionalInfo", nullable = false)
	private String additionalInfo;
	
	@Column(name = "points", nullable = false)
	private int points = 0;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Rating> ratings = new HashSet<Rating>();
	
	@Column(name = "sideEffects", nullable = false)
	private String sideEffects;
	
	@Column(name = "advisedDailyDose", nullable = false)
	private int advisedDailyDose;

	public Medicine() {
		super();
	}
	
	public Medicine(long id, String name, String code, MedicineType type, MedicineForm form, boolean withPrescription,
			Set<String> ingredients, String manufacturer, Set<String> compatibleMedicineCodes, String additionalInfo,
			int points, Set<Rating> ratings, String sideEffects, int advisedDailyDose) {
		super();
		this.id = id;
		this.name = name;
		this.code = code;
		this.type = type;
		this.form = form;
		this.withPrescription = withPrescription;
		this.ingredients = ingredients;
		this.manufacturer = manufacturer;
		this.compatibleMedicineCodes = compatibleMedicineCodes;
		this.additionalInfo = additionalInfo;
		this.points = points;
		this.ratings = ratings;
		this.sideEffects = sideEffects;
		this.advisedDailyDose = advisedDailyDose;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public MedicineType getType() {
		return type;
	}

	public void setType(MedicineType type) {
		this.type = type;
	}

	public MedicineForm getForm() {
		return form;
	}

	public void setForm(MedicineForm form) {
		this.form = form;
	}

	public boolean isWithPrescription() {
		return withPrescription;
	}

	public void setWithPrescription(boolean withPrescription) {
		this.withPrescription = withPrescription;
	}

	public Set<String> getIngredients() {
		return ingredients;
	}

	public void setIngredients(Set<String> ingredients) {
		this.ingredients = ingredients;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public Set<String> getCompatibleMedicineCodes() {
		return compatibleMedicineCodes;
	}

	public void setCompatibleMedicineCodes(Set<String> compatibleMedicineCodes) {
		this.compatibleMedicineCodes = compatibleMedicineCodes;
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

	public Set<Rating> getRatings() {
		return ratings;
	}

	public void setRatings(Set<Rating> ratings) {
		this.ratings = ratings;
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
	public boolean isNameValid() {
		if(this.name.equals("") || this.name.length() < 2 || this.name.length() > 30) return false;
		return true;
	}
	public boolean isCodeValid() {
		if(this.code.equals("")  || this.code.length() < 2 || this.code.length() > 30) return false;
		return true;
	}
	public boolean isManufacturerValid() {
		if(this.manufacturer.equals("") || this.manufacturer.length() < 2 || this.manufacturer.length() > 30) return false;
		return true;
	}
	public boolean isAdditionalInfoValid() {
		if(this.additionalInfo.equals("")  || this.additionalInfo.length() < 2 || this.additionalInfo.length() > 30) return false;
		return true;
	}
	public boolean isSideEffectsValid() {
		if(this.sideEffects.equals("") || this.sideEffects.length() < 2 || this.sideEffects.length() > 30) return false;
		return true;
	}
	public boolean isAdvisedDailyDose() {
		if(this.advisedDailyDose >=1 && this.advisedDailyDose <=10) return true;
		return false;
	}
	
	
	
	
}
