package isa.tim28.pharmacies.dtos;

import java.time.LocalDateTime;

public class OfferSupplierProfileDTO {
	
	private long idOffer;
	private LocalDateTime deadline;
	private String accepted;
	private double totalPrice;
	private String pharmacyName;
	
	public OfferSupplierProfileDTO() {
		super();
	}
	public OfferSupplierProfileDTO(LocalDateTime deadline, String accepted, double totalPrice,  String pharmacyName) {
		super();
		this.deadline = deadline;
		this.accepted = accepted;
		this.totalPrice = totalPrice;
		this.pharmacyName = pharmacyName;
	}
	public OfferSupplierProfileDTO(long idOffer, LocalDateTime deadline, String accepted, double totalPrice,  String pharmacyName) {
		super();
		this.idOffer = idOffer;
		this.deadline = deadline;
		this.accepted = accepted;
		this.totalPrice = totalPrice;
		this.pharmacyName = pharmacyName;
	}
	
	
	public String getPharmacyName() {
		return pharmacyName;
	}
	public void setPharmacyName(String pharmacyName) {
		this.pharmacyName = pharmacyName;
	}
	public long getIdOffer() {
		return idOffer;
	}
	public void setIdOffer(long idOffer) {
		this.idOffer = idOffer;
	}
	public LocalDateTime getDeadline() {
		return deadline;
	}
	public void setDeadline(LocalDateTime deadline) {
		this.deadline = deadline;
	}
	public String getAccepted() {
		return accepted;
	}
	public void setAccepted(String accepted) {
		this.accepted = accepted;
	}
	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	
}
