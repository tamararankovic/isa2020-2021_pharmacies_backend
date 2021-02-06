package isa.tim28.pharmacies.dtos;

import java.time.LocalDate;

public class PharmAppByWeekDTO {

	private LocalDate startDate;
	private LocalDate endDate;
	
	public PharmAppByWeekDTO() {
		super();
	}

	public PharmAppByWeekDTO(LocalDate startDate, LocalDate endDate) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
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
	
}
