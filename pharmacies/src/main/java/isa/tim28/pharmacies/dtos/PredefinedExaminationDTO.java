package isa.tim28.pharmacies.dtos;

import java.time.LocalDateTime;

public class PredefinedExaminationDTO {

	private long dermatologistId;
	private LocalDateTime startDateTime;
	private double price;
	private double durationInMinutes;
	
	public PredefinedExaminationDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public PredefinedExaminationDTO(long dermatologistId, LocalDateTime startDateTime, double price,
			double durationInMinutes) {
		super();
		this.dermatologistId = dermatologistId;
		this.startDateTime = startDateTime;
		this.price = price;
		this.durationInMinutes = durationInMinutes;
	}
	
	public long getDermatologistId() {
		return dermatologistId;
	}
	public void setDermatologistId(long dermatologistId) {
		this.dermatologistId = dermatologistId;
	}
	public LocalDateTime getStartDateTime() {
		return startDateTime;
	}
	public void setStartDateTime(LocalDateTime startDateTime) {
		this.startDateTime = startDateTime;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getDurationInMinutes() {
		return durationInMinutes;
	}
	public void setDurationInMinutes(double durationInMinutes) {
		this.durationInMinutes = durationInMinutes;
	}
}
