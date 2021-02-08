package isa.tim28.pharmacies.dtos;

public class ShowCounselingDTO {
	
	private long id;
	private String date;
	private String pharmacistName;
	private boolean cancellable;
	private String type;
	


	public ShowCounselingDTO() {
		super();
	}
	
	public ShowCounselingDTO(long id, String date, String pharmacistName, boolean cancellable, String type) {
		super();
		this.id = id;
		this.date = date;
		this.pharmacistName = pharmacistName;
		this.cancellable = cancellable;
		this.type = type;
	}
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getPharmacistName() {
		return pharmacistName;
	}
	public void setPharmacistName(String pharmacistName) {
		this.pharmacistName = pharmacistName;
	}
	public boolean isCancellable() {
		return cancellable;
	}
	public void setCancellable(boolean cancellable) {
		this.cancellable = cancellable;
	}

	
}
