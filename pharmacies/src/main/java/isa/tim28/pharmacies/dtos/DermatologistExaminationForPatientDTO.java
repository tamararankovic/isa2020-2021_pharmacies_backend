package isa.tim28.pharmacies.dtos;

import java.time.LocalDateTime;

public class DermatologistExaminationForPatientDTO {

	private LocalDateTime startDateTime;
	private String dermatologist;
	private double duration;
	private double price;
	
	public DermatologistExaminationForPatientDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DermatologistExaminationForPatientDTO(LocalDateTime startDateTime, String dermatologist,
			double duration, double price) {
		super();
		this.startDateTime = startDateTime;
		this.dermatologist = dermatologist;
		this.duration = duration;
		this.price = price;
	}

	public LocalDateTime getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(LocalDateTime startDateTime) {
		this.startDateTime = startDateTime;
	}

	public String getDermatologist() {
		return dermatologist;
	}

	public void setDermatologist(String dermatologist) {
		this.dermatologist = dermatologist;
	}

	public double getDuration() {
		return duration;
	}

	public void setDuration(double duration) {
		this.duration = duration;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
}
