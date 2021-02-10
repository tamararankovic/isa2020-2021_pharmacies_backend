package isa.tim28.pharmacies.dtos;

public class CurrentlyHasAppointmentDTO {

	private boolean hasAppointment;

	public CurrentlyHasAppointmentDTO() {
		super();
	}

	public CurrentlyHasAppointmentDTO(boolean hasAppointment) {
		super();
		this.hasAppointment = hasAppointment;
	}

	public boolean isHasAppointment() {
		return hasAppointment;
	}

	public void setHasAppointment(boolean hasAppointment) {
		this.hasAppointment = hasAppointment;
	}
}
