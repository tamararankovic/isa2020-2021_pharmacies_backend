package isa.tim28.pharmacies.dtos;

public class UpdateOrderDTO {
	
	private long id;
	private NewOrderDTO order;
	
	public UpdateOrderDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public UpdateOrderDTO(long id, NewOrderDTO order) {
		super();
		this.id = id;
		this.order = order;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public NewOrderDTO getOrder() {
		return order;
	}
	public void setOrder(NewOrderDTO order) {
		this.order = order;
	}
	
}
