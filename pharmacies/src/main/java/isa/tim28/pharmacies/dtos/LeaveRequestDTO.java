package isa.tim28.pharmacies.dtos;

import java.time.LocalDate;

public class LeaveRequestDTO {

	private long id;
	private String employeeName;
	private LocalDate startDate;
	private LocalDate endDate;
	private String leaveType;
	
	public LeaveRequestDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public LeaveRequestDTO(long id, String employeeName, LocalDate startDate, LocalDate endDate, String leaveType) {
		super();
		this.id = id;
		this.employeeName = employeeName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.leaveType = leaveType;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
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
	public String getLeaveType() {
		return leaveType;
	}
	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}
}
