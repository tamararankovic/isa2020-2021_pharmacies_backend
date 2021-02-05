package isa.tim28.pharmacies.dtos;

public class PharmAppDTO {
	
	private long appointmentId;
	private String startTime;
	private int durationInMinutes;
	private String patientName;
	
	public PharmAppDTO() {
		super();
	}

	public PharmAppDTO(long appointmentId, String startTime, int durationInMinutes, String patientName) {
		super();
		this.appointmentId = appointmentId;
		this.startTime = startTime;
		this.durationInMinutes = durationInMinutes;
		this.patientName = patientName;
	}

	public long getAppointmentId() {
		return appointmentId;
	}

	public void setAppointmentId(long appointmentId) {
		this.appointmentId = appointmentId;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public int getDurationInMinutes() {
		return durationInMinutes;
	}

	public void setDurationInMinutes(int durationInMinutes) {
		this.durationInMinutes = durationInMinutes;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

}
