package isa.tim28.pharmacies.dtos;

public class PharmAppByMonthDTO {
	
	private int year;
	private int month;
	
	public PharmAppByMonthDTO() {
		super();
	}

	public PharmAppByMonthDTO(int year, int month) {
		super();
		this.year = year;
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

}
