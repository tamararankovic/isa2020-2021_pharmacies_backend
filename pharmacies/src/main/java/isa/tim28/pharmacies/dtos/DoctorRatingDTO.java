package isa.tim28.pharmacies.dtos;

public class DoctorRatingDTO {
	private long id;
	private String fullName;
	private int rating;
	
	public DoctorRatingDTO() {
		super();
	}
	
	public DoctorRatingDTO(long id, String fullName, int rating) {
		super();
		this.id = id;
		this.fullName = fullName;
		this.rating = rating;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	
	
	
}
