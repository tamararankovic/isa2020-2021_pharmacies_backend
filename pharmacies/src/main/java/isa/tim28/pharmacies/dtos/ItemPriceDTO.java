package isa.tim28.pharmacies.dtos;

public class ItemPriceDTO {

	private long itemId;
	private String itemName;
	private double price;
	private boolean undefined;
	
	public ItemPriceDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ItemPriceDTO(long itemId, String itemName, double price, boolean undefined) {
		super();
		this.itemId = itemId;
		this.itemName = itemName;
		this.price = price;
		this.undefined = undefined;
	}
	
	public long getItemId() {
		return itemId;
	}
	public void setItemId(long itemId) {
		this.itemId = itemId;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public boolean isUndefined() {
		return undefined;
	}
	public void setUndefined(boolean undefined) {
		this.undefined = undefined;
	}
}
