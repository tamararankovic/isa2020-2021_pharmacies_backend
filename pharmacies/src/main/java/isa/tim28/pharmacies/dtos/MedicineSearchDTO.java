package isa.tim28.pharmacies.dtos;

import java.util.Set;

public class MedicineSearchDTO {
	private long id;
	private String name;
	private String type;
	private double averageRating;
	private MedicineSpecificationDTO specification;
	private Set<PharmacyForMedSearchDTO> pharmacies;
	
	public MedicineSearchDTO() {
		super();
	}
	public MedicineSearchDTO(long id, String name, String type, double averageRating,
			MedicineSpecificationDTO specification, Set<PharmacyForMedSearchDTO> pharmacies) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.averageRating = averageRating;
		this.specification = specification;
		this.pharmacies = pharmacies;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public double getAverageRating() {
		return averageRating;
	}
	public void setAverageRating(double averageRating) {
		this.averageRating = averageRating;
	}
	public MedicineSpecificationDTO getSpecification() {
		return specification;
	}
	public void setSpecification(MedicineSpecificationDTO specification) {
		this.specification = specification;
	}
	public Set<PharmacyForMedSearchDTO> getPharmacies() {
		return pharmacies;
	}
	public void setPharmacies(Set<PharmacyForMedSearchDTO> pharmacies) {
		this.pharmacies = pharmacies;
	}	
	
	
	
	
	
}
