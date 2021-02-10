package isa.tim28.pharmacies.dtos;

public class ComplaintDTO {
	private long id;
	private String text;
	
	public ComplaintDTO() {
		super();
	}
	public ComplaintDTO(long id, String text) {
		super();
		this.id = id;
		this.text = text;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public boolean isTextValid() {
		if(this.text.equals("") || this.text.length() < 2 || this.text.length() > 3000) return false;
		return true;
	}
	
}
