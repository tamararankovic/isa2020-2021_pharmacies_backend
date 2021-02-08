package isa.tim28.pharmacies.dtos;

public class PharmacyBasicInfoDTO {
	
	private String name;
	private String description;
	private String address;
	private double avgRating;
	
	public PharmacyBasicInfoDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public PharmacyBasicInfoDTO(String name, String description, String address, double avgRating) {
		super();
		this.name = name;
		this.description = description;
		this.address = address;
		this.avgRating = avgRating;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	public double getAvgRating() {
		return avgRating;
	}

	public void setAvgRating(double avgRating) {
		this.avgRating = avgRating;
	}
}
