package isa.tim28.pharmacies.dtos;

public class TherapyDTO {
	
	private long medicineId;
	private int durationInDays;
	
	public TherapyDTO() {
		super();
	}

	public TherapyDTO(long medicineId, int durationInDays) {
		super();
		this.medicineId = medicineId;
		this.durationInDays = durationInDays;
	}

	public long getMedicineId() {
		return medicineId;
	}

	public void setMedicineId(long medicineId) {
		this.medicineId = medicineId;
	}

	public int getDurationInDays() {
		return durationInDays;
	}

	public void setDurationInDays(int durationInDays) {
		this.durationInDays = durationInDays;
	}
	
}
