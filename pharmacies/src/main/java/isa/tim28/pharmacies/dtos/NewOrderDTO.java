package isa.tim28.pharmacies.dtos;

import java.time.LocalDateTime;
import java.util.Set;

public class NewOrderDTO {
	
	Set<NewMedicineQuantityDTO> medicines;
	LocalDateTime deadline;
	
	public NewOrderDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public NewOrderDTO(Set<NewMedicineQuantityDTO> medicines, LocalDateTime deadline) {
		super();
		this.medicines = medicines;
		this.deadline = deadline;
	}
	
	public Set<NewMedicineQuantityDTO> getMedicines() {
		return medicines;
	}
	public void setMedicines(Set<NewMedicineQuantityDTO> medicines) {
		this.medicines = medicines;
	}
	public LocalDateTime getDeadline() {
		return deadline;
	}
	public void setDeadline(LocalDateTime deadline) {
		this.deadline = deadline;
	}
}
