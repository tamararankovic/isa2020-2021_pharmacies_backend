package isa.tim28.pharmacies.dtos;

public class DermatologistAppointmentDTO {

	private long appointmentId;
	private long patientId;
	private String patientName;
	
	public DermatologistAppointmentDTO() {
		super();
	}

	public DermatologistAppointmentDTO(long appointmentId, long patientId, String patientName) {
		super();
		this.appointmentId = appointmentId;
		this.patientId = patientId;
		this.patientName = patientName;
	}

	public long getAppointmentId() {
		return appointmentId;
	}

	public void setAppointmentId(long appointmentId) {
		this.appointmentId = appointmentId;
	}
	
	public long getPatientId() {
		return patientId;
	}

	public void setPatientId(long patientId) {
		this.patientId = patientId;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	
}
