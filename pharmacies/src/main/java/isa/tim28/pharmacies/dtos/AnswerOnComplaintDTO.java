package isa.tim28.pharmacies.dtos;

public class AnswerOnComplaintDTO {
	private String answer;
	private String type;
	private long complaintId;
	
	public AnswerOnComplaintDTO() {
		super();
	}
	public AnswerOnComplaintDTO(String answer, String type, long complaintId) {
		super();
		this.answer = answer;
		this.type = type;
		this.complaintId = complaintId;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public long getComplaintId() {
		return complaintId;
	}
	public void setComplaintId(long complaintId) {
		this.complaintId = complaintId;
	}
	
	
}
