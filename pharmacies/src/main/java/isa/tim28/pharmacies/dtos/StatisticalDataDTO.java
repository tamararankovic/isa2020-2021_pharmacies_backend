package isa.tim28.pharmacies.dtos;

public class StatisticalDataDTO {

	private String timeReference;
	private double value;
	
	public StatisticalDataDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public StatisticalDataDTO(String timeReference, double value) {
		super();
		this.timeReference = timeReference;
		this.value = value;
	}

	public String getTimeReference() {
		return timeReference;
	}

	public void setTimeReference(String timeReference) {
		this.timeReference = timeReference;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}
}
