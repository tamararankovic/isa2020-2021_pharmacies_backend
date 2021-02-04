package isa.tim28.pharmacies.dtos;

import java.util.ArrayList;
import java.util.List;

public class DermatologistReportDTO {

	private long appointmentId;
	private String diagnosis;
	private List<TherapyDTO> therapies = new ArrayList<TherapyDTO>();
	
	public DermatologistReportDTO() {
		super();
	}

	public DermatologistReportDTO(int appointmentId, String diagnosis, List<TherapyDTO> therapies) {
		super();
		this.appointmentId = appointmentId;
		this.diagnosis = diagnosis;
		this.therapies = therapies;
	}

	public long getAppointmentId() {
		return appointmentId;
	}

	public void setAppointmentId(int appointmentId) {
		this.appointmentId = appointmentId;
	}

	public String getDiagnosis() {
		return diagnosis;
	}

	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}

	public List<TherapyDTO> getTherapies() {
		return therapies;
	}

	public void setTherapies(List<TherapyDTO> therapies) {
		this.therapies = therapies;
	}
	
}
