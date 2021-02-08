package isa.tim28.pharmacies.dtos;

import java.time.LocalDateTime;

public class NotificationDTO {

	private String medicineCode;
	private String medicineName;
	private LocalDateTime timestamp;
	
	public NotificationDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public NotificationDTO(String medicineCode, String medicineName, LocalDateTime timestamp) {
		super();
		this.medicineName = medicineName;
		this.timestamp = timestamp;
		this.medicineCode = medicineCode;
	}
	
	public String getMedicineName() {
		return medicineName;
	}
	
	public void setMedicineName(String medicineName) {
		this.medicineName = medicineName;
	}
	
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public String getMedicineCode() {
		return medicineCode;
	}

	public void setMedicineCode(String medicineCode) {
		this.medicineCode = medicineCode;
	}
}
