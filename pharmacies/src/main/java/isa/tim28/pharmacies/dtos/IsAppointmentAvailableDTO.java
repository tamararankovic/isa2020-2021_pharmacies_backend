package isa.tim28.pharmacies.dtos;

public class IsAppointmentAvailableDTO {

	private boolean available;

	public IsAppointmentAvailableDTO() {
		super();
	}

	public IsAppointmentAvailableDTO(boolean available) {
		super();
		this.available = available;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}
	
}
