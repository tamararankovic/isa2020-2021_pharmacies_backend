package isa.tim28.pharmacies.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "surname", nullable = false)
	private String surname;
	
	@Column(name = "email", nullable = false)
	private String email;
	
	@Column(name = "password", nullable = false)
	private String password;
	
	@Column(name = "role", nullable = false)
	private Role role;
	
	@Column(name = "active", nullable = false)
	private boolean active;
	
	@Column(name = "loged", nullable = false)
	private boolean loged;
	
	public User() {
		super();
	}
	public User(long id, String name, String surname, String email, String password, Role role, boolean active, boolean loged) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.password = password;
		this.role = role;
		this.active = active;
		this.loged = loged;
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
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getFullName() {
		return this.name + " " + this.surname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
		
	public boolean getActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public boolean getLoged() {
		return loged;
	}
	public void setLoged(boolean loged) {
		this.loged = loged;
	}
	public boolean isNameValid() {
		if(this.name == "" || this.name.length() < 2 || this.name.length() > 30) return false;
		return true;
	}
	
	public boolean isSurnameValid() {
		if(this.surname == "" || this.surname.length() < 2 || this.surname.length() > 30) return false;
		return true;
	}
	
	public boolean isEmailValid() {
		if(this.name == "" || this.name.length() < 3 || this.name.length() > 30 || !this.email.contains("@")) return false;
		return true;
	}
	
	public boolean isPasswordValid() {
		return this.password.length() >=4 && this.password.length() <= 30;
	}
	
}
