package isa.tim28.pharmacies.dtos;

import java.time.LocalDateTime;

public class DermatologistSaveAppointmentDTO {
	
	private long lastAppointmentId;
	private LocalDateTime startDateTime;
	private long price;
	
	public DermatologistSaveAppointmentDTO() {
		super();
	}

	public DermatologistSaveAppointmentDTO(long lastAppointmentId, LocalDateTime startDateTime, long price) {
		super();
		this.lastAppointmentId = lastAppointmentId;
		this.startDateTime = startDateTime;
		this.price = price;
	}

	public long getLastAppointmentId() {
		return lastAppointmentId;
	}

	public void setLastAppointmentId(long lastAppointmentId) {
		this.lastAppointmentId = lastAppointmentId;
	}

	public LocalDateTime getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(LocalDateTime startDateTime) {
		this.startDateTime = startDateTime;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

}
