package isa.tim28.pharmacies.dtos;


public class LeaveViewDTO {
	
	private String startDate;
	private String endDate;
	private String type;
	private String confirmed;
	
	public LeaveViewDTO() {
		super();
	}

	public LeaveViewDTO(String startDate, String endDate, String type, String confirmed) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
		this.type = type;
		this.confirmed = confirmed;
	}

	public LeaveViewDTO(String startDate, String endDate, String type, boolean confirmed) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
		this.type = type;
		if(confirmed) this.confirmed = "CONFIRMED";
		else this.confirmed = "NOT CONFIRMED";
	}
	
	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getConfirmed() {
		return confirmed;
	}

	public void setConfirmed(String confirmed) {
		this.confirmed = confirmed;
	}
	
	public void setConfirmed(boolean confirmed) {
		if(confirmed) this.confirmed = "CONFIRMED";
		else this.confirmed = "NOT CONFIRMED";
	}

}
