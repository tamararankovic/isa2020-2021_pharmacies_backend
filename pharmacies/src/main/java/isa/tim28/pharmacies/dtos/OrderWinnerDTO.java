package isa.tim28.pharmacies.dtos;

public class OrderWinnerDTO {
	
	private long orderId;
	private long winningOfferId;
	
	public OrderWinnerDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public OrderWinnerDTO(long orderId, long winningOfferId) {
		super();
		this.orderId = orderId;
		this.winningOfferId = winningOfferId;
	}
	
	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	public long getWinningOfferId() {
		return winningOfferId;
	}
	public void setWinningOfferId(long winningOfferId) {
		this.winningOfferId = winningOfferId;
	}
}
