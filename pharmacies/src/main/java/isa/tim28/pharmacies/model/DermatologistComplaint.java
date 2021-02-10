package isa.tim28.pharmacies.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class DermatologistComplaint {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "text", nullable = false)
	private String text;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Dermatologist dermatologist;
	
	@Column(name = "reply", nullable = false)
	private String reply;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Patient patient;
	
	public DermatologistComplaint() {
		super();
	}

	public DermatologistComplaint(long id, String text, Dermatologist dermatologist, String reply, Patient patient) {
		super();
		this.id = id;
		this.text = text;
		this.dermatologist = dermatologist;
		this.reply = reply;
		this.patient = patient;
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

	public Dermatologist getDermatologist() {
		return dermatologist;
	}

	public void setDermatologist(Dermatologist dermatologist) {
		this.dermatologist = dermatologist;
	}

	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	public boolean isTextValid() {
		if(this.text.equals("") || this.text.length() < 2 || this.text.length() > 3000) return false;
		return true;
	}
}
