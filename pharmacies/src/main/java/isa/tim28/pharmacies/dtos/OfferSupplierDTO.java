package isa.tim28.pharmacies.dtos;

import java.time.LocalDateTime;

public class OfferSupplierDTO {
	
	private long idOrder;
	private LocalDateTime deadline;
	private double totalPrice;
	
	
	public OfferSupplierDTO() {
		super();
	}
	public OfferSupplierDTO(long idOrder, LocalDateTime deadline, double totalPrice) {
		super();
		this.idOrder = idOrder;
		this.deadline = deadline;
		this.totalPrice = totalPrice;
	}
	public long getIdOrder() {
		return idOrder;
	}
	public void setIdOrder(long idOrder) {
		this.idOrder = idOrder;
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
