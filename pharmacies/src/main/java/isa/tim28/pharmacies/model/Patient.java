package isa.tim28.pharmacies.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

@Entity
public class Patient {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private User user;
	
	@Column(name = "address", nullable = false)
	private String address;
	
	@Column(name = "city", nullable = false)
	private String city;
	
	@Column(name = "country", nullable = false)
	private String country;
	
	@Column(name = "phone", nullable = false)
	private String phone;
	
	@Column(name = "category", nullable = false)
	private Loyalty category = Loyalty.REGULAR;
	
	@Column(name = "points", nullable = false)
	private int points = 0;
	
	@Column(name = "penalties", nullable = false)
	private int penalties = 0;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Medicine> allergies = new HashSet<Medicine>();
	
	public Patient() {
		super();
	}
	
	public Patient(User user, String address, String city, String country, String phone) {
		super();
		this.user = user;
		this.address = address;
		this.city = city;
		this.country = country;
		this.phone = phone;
	}
	
	public Patient(long id, User user, String address, String city, String country, String phone,
			Set<Medicine> allergies) {
		super();
		this.id = id;
		this.user = user;
		this.address = address;
		this.city = city;
		this.country = country;
		this.phone = phone;
		this.allergies = allergies;
	}

	public Patient(long id, User user, String address, String city, String country, String phone, Loyalty category,
			int points, int penalties, Set<Medicine> allergies) {
		super();
		this.id = id;
		this.user = user;
		this.address = address;
		this.city = city;
		this.country = country;
		this.phone = phone;
		this.category = category;
		this.points = points;
		this.penalties = penalties;
		this.allergies = allergies;
	}
	
	public Set<Medicine> getAllergies() {
		return allergies;
	}
	public void setAllergies(Set<Medicine> allergies) {
		this.allergies = allergies;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Loyalty getCategory() {
		return category;
	}
	public void setCategory(Loyalty category) {
		this.category = category;
	}
	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
	}
	public int getPenalties() {
		return penalties;
	}
	public void setPenalties(int penalties) {
		this.penalties = penalties;
	}
	
}
