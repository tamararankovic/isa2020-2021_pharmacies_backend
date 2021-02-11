package isa.tim28.pharmacies.dtos;

public class AllComplaintsDTO {
	private long complaintId;
	private String text;
	private String complainedFullName;
	private String patientFullName;
	private String type;
	
	public AllComplaintsDTO() {
		super();
	}
	public AllComplaintsDTO(long complaintId, String text, String complainedFullName, String patientFullName,
			String type) {
		super();
		this.complaintId = complaintId;
		this.text = text;
		this.complainedFullName = complainedFullName;
		this.patientFullName = patientFullName;
		this.type = type;
	}
	public long getComplaintId() {
		return complaintId;
	}
	public void setComplaintId(long complaintId) {
		this.complaintId = complaintId;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getComplainedFullName() {
		return complainedFullName;
	}
	public void setComplainedFullName(String complainedFullName) {
		this.complainedFullName = complainedFullName;
	}
	public String getPatientFullName() {
		return patientFullName;
	}
	public void setPatientFullName(String patientFullName) {
		this.patientFullName = patientFullName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
}
