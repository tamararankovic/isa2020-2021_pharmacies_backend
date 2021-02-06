package isa.tim28.pharmacies.dtos;

import java.time.LocalDateTime;
import java.util.Set;

public class OrderForSupplierDTO {
	
	private long id;
	private Set<OrderedMedicineForSupplierDTO> medicines;
	private LocalDateTime deadline;
	private String state;
	private String pharmacyName;
	
	public OrderForSupplierDTO() {
		super();
	}

	public OrderForSupplierDTO(long id, Set<OrderedMedicineForSupplierDTO> medicines, LocalDateTime deadline, String state, String pharmacyName) {
		super();
		this.id = id;
		this.medicines = medicines;
		this.deadline = deadline;
		this.state = state;
		this.pharmacyName = pharmacyName;
	}

	
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPharmacyName() {
		return pharmacyName;
	}

	public void setPharmacyName(String pharmacyName) {
		this.pharmacyName = pharmacyName;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Set<OrderedMedicineForSupplierDTO> getMedicines() {
		return medicines;
	}

	public void setMedicines(Set<OrderedMedicineForSupplierDTO> medicines) {
		this.medicines = medicines;
	}

	public LocalDateTime getDeadline() {
		return deadline;
	}

	public void setDeadline(LocalDateTime deadline) {
		this.deadline = deadline;
	}
	
	
}
