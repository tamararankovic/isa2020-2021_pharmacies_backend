package isa.tim28.pharmacies.dtos;

public class PharmacistDTO {

	private long id;
	private String name;
	private String surname;
	private double avgRating;
	private String pharmacy;
	
	public PharmacistDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PharmacistDTO(long id, String name, String surname, double avgRating, String pharmacy) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.avgRating = avgRating;
		this.pharmacy = pharmacy;
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

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public double getAvgRating() {
		return avgRating;
	}

	public void setAvgRating(double avgRating) {
		this.avgRating = avgRating;
	}

	public String getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(String pharmacy) {
		this.pharmacy = pharmacy;
	}
}
