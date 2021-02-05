package isa.tim28.pharmacies.dtos;

import java.time.LocalDateTime;

import javax.mail.search.DateTerm;
import javax.print.attribute.standard.DateTimeAtCompleted;

import isa.tim28.pharmacies.model.Patient;

public class ReservationDTO {

	private long id;
	private String medicine;
	private String pharmacy;
	private String date;
	private String received;
	private boolean cancellable;
	
	

	public ReservationDTO() {
		super();
	}
	
	public ReservationDTO(long id,String medicine, String pharmacy, String date, String received, boolean cancellable) {
		super();
		this.id = id;
		this.medicine = medicine;
		this.pharmacy = pharmacy;
		this.date = date;
		this.received = received;
		this.cancellable = cancellable;
	}
	
	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean isCancellable() {
		return cancellable;
	}

	public void setCancellable(boolean cancellable) {
		this.cancellable = cancellable;
	}
	
	public String getReceived() {
		return received;
	}

	public void setReceived(String received) {
		this.received = received;
	}

	public String getMedicine() {
		return medicine;
	}
	public void setMedicine(String medicine) {
		this.medicine = medicine;
	}
	public String getPharmacy() {
		return pharmacy;
	}
	public void setPharmacy(String pharmacy) {
		this.pharmacy = pharmacy;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
}
