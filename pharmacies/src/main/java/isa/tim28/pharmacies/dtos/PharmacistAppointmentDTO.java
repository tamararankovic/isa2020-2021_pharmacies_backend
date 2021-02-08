package isa.tim28.pharmacies.dtos;

public class PharmacistAppointmentDTO {

	private String date;
	private long pharmacistId;
	
	public PharmacistAppointmentDTO() {
		super();
	}
	
	public PharmacistAppointmentDTO(String date, long pharmacistId) {
		super();
		this.date = date;
		this.pharmacistId = pharmacistId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public long getPharmacistId() {
		return pharmacistId;
	}
	public void setPharmacistId(long pharmacistId) {
		this.pharmacistId = pharmacistId;
	}
	
	
}
