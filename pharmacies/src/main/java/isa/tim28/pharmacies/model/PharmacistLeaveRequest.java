package isa.tim28.pharmacies.model;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class PharmacistLeaveRequest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Pharmacist pharmacist;
	
	@Column(name = "startDate", nullable = false)
	private LocalDate startDate;
	
	@Column(name = "endDate", nullable = false)
	private LocalDate endDate;
	
	@Column(name = "type", nullable = false)
	private LeaveType type;
	
	@Column(name = "confirmed", nullable = false)
	private boolean confirmed;

	public PharmacistLeaveRequest() {
		super();
	}
	
	public PharmacistLeaveRequest(long id, Pharmacist pharmacist, LocalDate startDate, LocalDate endDate,
			LeaveType type, boolean confirmed) {
		super();
		this.id = id;
		this.pharmacist = pharmacist;
		this.startDate = startDate;
		this.endDate = endDate;
		this.type = type;
		this.confirmed = confirmed;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Pharmacist getPharmacist() {
		return pharmacist;
	}

	public void setPharmacist(Pharmacist pharmacist) {
		this.pharmacist = pharmacist;
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

	public LeaveType getType() {
		return type;
	}

	public void setType(LeaveType type) {
		this.type = type;
	}

	public boolean isConfirmed() {
		return confirmed;
	}

	public void setConfirmed(boolean confirmed) {
		this.confirmed = confirmed;
	}
}
