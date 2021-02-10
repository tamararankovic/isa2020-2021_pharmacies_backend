package isa.tim28.pharmacies.dtos;

public class ShowCounselingDTO {
	
	private long id;
	private String date;
	private long doctorId;
	private String pharmacistName;
	private boolean cancellable;
	private int duration;
	private double price;
	private String type;
	


	public ShowCounselingDTO() {
		super();
	}
	
	public ShowCounselingDTO(long id, String date, long doctorId,String pharmacistName, boolean cancellable,int duration, double price,String type) {
		super();
		this.id = id;
		this.date = date;
		this.doctorId = doctorId;
		this.pharmacistName = pharmacistName;
		this.cancellable = cancellable;
		this.duration = duration;
		this.price = price;
		this.type = type;
	}
	
	
	
	public long getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(long doctorId) {
		this.doctorId = doctorId;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
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
