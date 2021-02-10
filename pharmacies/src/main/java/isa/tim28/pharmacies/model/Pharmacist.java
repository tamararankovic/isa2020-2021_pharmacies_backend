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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Pharmacist {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private User user;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private EngagementInPharmacy engegementInPharmacy;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Rating> ratings = new HashSet<Rating>();
	
	@Column(name = "currentlyHasAppointment", nullable = false)
	private boolean currentlyHasAppointment = false;

	public Pharmacist() {
		super();
	}
	
	public Pharmacist(long id, User user, EngagementInPharmacy engegementInPharmacy, Set<Rating> ratings) {
		super();
		this.id = id;
		this.user = user;
		this.engegementInPharmacy = engegementInPharmacy;
		this.ratings = ratings;
		this.currentlyHasAppointment = false;
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

	public EngagementInPharmacy getEngegementInPharmacy() {
		return engegementInPharmacy;
	}

	public void setEngegementInPharmacy(EngagementInPharmacy engegementInPharmacy) {
		this.engegementInPharmacy = engegementInPharmacy;
	}

	public Set<Rating> getRatings() {
		return ratings;
	}

	public void setRatings(Set<Rating> ratings) {
		this.ratings = ratings;
	}
	
	public boolean isCurrentlyHasAppointment() {
		return currentlyHasAppointment;
	}

	public void setCurrentlyHasAppointment(boolean currentlyHasAppointment) {
		this.currentlyHasAppointment = currentlyHasAppointment;
	}
	
}
