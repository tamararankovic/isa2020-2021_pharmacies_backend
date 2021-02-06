package isa.tim28.pharmacies.dtos;

import java.time.LocalDateTime;

public class OfferUpdateDTO {
	private long idOffer;
	private LocalDateTime deadline;
	private double totalPrice;
	
	
	public OfferUpdateDTO() {
		super();
	}
	public OfferUpdateDTO(long idOffer, LocalDateTime deadline, double totalPrice) {
		super();
		this.idOffer = idOffer;
		this.deadline = deadline;
		this.totalPrice = totalPrice;
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
	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	
}
