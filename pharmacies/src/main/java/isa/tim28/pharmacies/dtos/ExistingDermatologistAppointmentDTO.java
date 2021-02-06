package isa.tim28.pharmacies.dtos;

public class ExistingDermatologistAppointmentDTO {
	
	private long id;
	private String startDateTime;
	private long durationInMinutes;
	private long price;
	
	public ExistingDermatologistAppointmentDTO() {
		super();
	}

	public ExistingDermatologistAppointmentDTO(long id, String startDateTime, long durationInMinutes, long price) {
		super();
		this.id = id;
		this.startDateTime = startDateTime;
		this.durationInMinutes = durationInMinutes;
		this.price = price;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(String startDateTime) {
		this.startDateTime = startDateTime;
	}

	public long getDurationInMinutes() {
		return durationInMinutes;
	}

	public void setDurationInMinutes(long durationInMinutes) {
		this.durationInMinutes = durationInMinutes;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

}
