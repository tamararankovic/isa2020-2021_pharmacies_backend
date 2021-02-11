package isa.tim28.pharmacies.dtos;

import java.time.LocalDateTime;

public class DermatologistExaminationForPatientDTO {

	private long id;
	private LocalDateTime startDateTime;
	private String dermatologist;
	private double duration;
	private double price;
	private long version;
	
	public DermatologistExaminationForPatientDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DermatologistExaminationForPatientDTO(long id,LocalDateTime startDateTime, String dermatologist,
			double duration, double price) {
		super();
		this.id = id;
		this.startDateTime = startDateTime;
		this.dermatologist = dermatologist;
		this.duration = duration;
		this.price = price;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}
}
