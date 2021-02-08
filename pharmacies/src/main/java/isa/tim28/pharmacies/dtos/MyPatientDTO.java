package isa.tim28.pharmacies.dtos;

public class MyPatientDTO {

	private long patientId;
	private String name;
	private String surname;
	private String appointmentDate;
	private String time;
	
	public MyPatientDTO() {
		super();
	}

	public MyPatientDTO(long patientId, String name, String surname, String appointmentDate, String time) {
		super();
		this.patientId = patientId;
		this.name = name;
		this.surname = surname;
		this.appointmentDate = appointmentDate;
		this.time = time;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public long getPatientId() {
		return patientId;
	}

	public void setPatientId(long patientId) {
		this.patientId = patientId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getAppointmentDate() {
		return appointmentDate;
	}

	public void setAppointmentDate(String appointmentDate) {
		this.appointmentDate = appointmentDate;
	}
	
}
