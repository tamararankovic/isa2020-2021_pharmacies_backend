package isa.tim28.pharmacies.dtos;

import java.time.LocalDateTime;

public class PharmacistSaveAppointmentDTO {
	
	private long lastAppointmentId;
	private LocalDateTime startDateTime;
	
	public PharmacistSaveAppointmentDTO() {
		super();
	}

	public PharmacistSaveAppointmentDTO(long lastAppointmentId, LocalDateTime startDateTime) {
		super();
		this.lastAppointmentId = lastAppointmentId;
		this.startDateTime = startDateTime;
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

}
