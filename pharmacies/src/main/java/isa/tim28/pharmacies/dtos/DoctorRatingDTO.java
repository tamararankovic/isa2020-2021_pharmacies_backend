package isa.tim28.pharmacies.dtos;

public class DoctorRatingDTO {
	private long id;
	private long pharmacyId;
	private String fullName;
	private int rating;
	
	public DoctorRatingDTO() {
		super();
	}
	
	public DoctorRatingDTO(long id,long pharmacyId, String fullName, int rating) {
		super();
		this.id = id;
		this.pharmacyId = pharmacyId;
		this.fullName = fullName;
		this.rating = rating;
	}
	
	
	
	public long getPharmacyId() {
		return pharmacyId;
	}

	public void setPharmacyId(long pharmacyId) {
		this.pharmacyId = pharmacyId;
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
