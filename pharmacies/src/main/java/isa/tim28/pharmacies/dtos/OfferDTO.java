package isa.tim28.pharmacies.dtos;

public class OfferDTO {

	private long id;
	private String supplierName;
	private double price;
	
	public OfferDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public OfferDTO(long id, String supplierName, double price) {
		super();
		this.id = id;
		this.supplierName = supplierName;
		this.price = price;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
}
