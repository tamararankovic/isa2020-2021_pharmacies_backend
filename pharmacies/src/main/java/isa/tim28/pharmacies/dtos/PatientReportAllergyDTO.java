package isa.tim28.pharmacies.dtos;

public class PatientReportAllergyDTO {
	
	private long patientId;
	private long medicineId;
	
	public PatientReportAllergyDTO() {
		super();
	}

	public PatientReportAllergyDTO(long patientId, long medicineId) {
		super();
		this.patientId = patientId;
		this.medicineId = medicineId;
	}

	public long getPatientId() {
		return patientId;
	}

	public void setPatientId(long patientId) {
		this.patientId = patientId;
	}

	public long getMedicineId() {
		return medicineId;
	}

	public void setMedicineId(long medicineId) {
		this.medicineId = medicineId;
	}
	
}
