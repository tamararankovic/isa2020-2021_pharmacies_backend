package isa.tim28.pharmacies.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class EPrescriptionMedicine {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "code", nullable = false)
	private String code;
	
	@Column(name = "quantity", nullable = false)
	private int quantity;

	public EPrescriptionMedicine() {
		super();
	}
	public EPrescriptionMedicine(String name, String code, int quantity) {
		super();
		this.name = name;
		this.code = code;
		this.quantity = quantity;
	}
	
	public EPrescriptionMedicine(long id, String name, String code, int quantity) {
		super();
		this.id = id;
		this.name = name;
		this.code = code;
		this.quantity = quantity;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
}
