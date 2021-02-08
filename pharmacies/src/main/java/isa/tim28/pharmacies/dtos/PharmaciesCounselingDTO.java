package isa.tim28.pharmacies.dtos;

public class PharmaciesCounselingDTO {

	private long id;
	private String name;
	private String address;
	private double rating;
	private double price;
	
	public PharmaciesCounselingDTO() {
		super();
	}
	
	public PharmaciesCounselingDTO(long id, String name, String address, double rating, double price) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.rating = rating;
		this.price = price;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	
}
