package isa.tim28.pharmacies.dtos;

import java.time.LocalDate;

import isa.tim28.pharmacies.model.LeaveType;

public class LeaveDTO {
	
	private LocalDate startDate;
	private LocalDate endDate;
	private String type;
	
	public LeaveDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LeaveDTO(LocalDate startDate, LocalDate endDate, String type) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
		this.type = type;
	}
	
	public LeaveDTO(LocalDate startDate, LocalDate endDate, LeaveType type) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
		this.type = type.toString();
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public void setType(LeaveType type) {
		this.type = type.toString();
	}

}
