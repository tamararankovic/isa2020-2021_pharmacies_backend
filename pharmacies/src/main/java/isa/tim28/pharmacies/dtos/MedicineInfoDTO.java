package isa.tim28.pharmacies.dtos;

public class MedicineInfoDTO {

	private String name;
	private String info;
	private int dose;
	private String form;
	private String manufacturer;
	private String type;
	private int points;
	
	public MedicineInfoDTO() {
		super();
	}
	
	public MedicineInfoDTO(String name, String info, int dose, String form, String manufacturer, String type,
			int points) {
		super();
		this.name = name;
		this.info = info;
		this.dose = dose;
		this.form = form;
		this.manufacturer = manufacturer;
		this.type = type;
		this.points = points;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public int getDose() {
		return dose;
	}
	public void setDose(int dose) {
		this.dose = dose;
	}
	public String getForm() {
		return form;
	}
	public void setForm(String form) {
		this.form = form;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
	}
	
	
}
