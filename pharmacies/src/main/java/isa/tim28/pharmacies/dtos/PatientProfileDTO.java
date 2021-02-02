package isa.tim28.pharmacies.dtos;

import java.util.ArrayList;

public class PatientProfileDTO {

	private String name;
	private String surname;
	private String email;
	private String address;
	private String city;
	private String country;
	private String phone;
	private int points;
	private String category;
	private ArrayList<String> allergies;
	
	public PatientProfileDTO() {
		super();
	}
	
	public PatientProfileDTO(String name, String surname, String email, String address, String city, String country,
			String phone, int points, String category,ArrayList<String> allergies) {
		super();
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.address = address;
		this.city = city;
		this.country = country;
		this.phone = phone;
		this.points = points;
		this.category = category;
		this.allergies = allergies;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public ArrayList<String> getAllergies() {
		return allergies;
	}

	public void setAllergies(ArrayList<String> allergies) {
		this.allergies = allergies;
	}
	
	
	
}
