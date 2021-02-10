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
import javax.persistence.Version;

@Entity
public class Dermatologist {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Version
	private Long version;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private User user;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<EngagementInPharmacy> engegementInPharmacies = new HashSet<EngagementInPharmacy>();
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Rating> ratings = new HashSet<Rating>();

	@Column(name = "currentlyHasAppointment", nullable = false)
	private boolean currentlyHasAppointment = false;
	
	public Dermatologist() {
		super();
	}
	
	public Dermatologist(long id, User user, Set<EngagementInPharmacy> engegementInPharmacies, Set<Rating> ratings) {
		super();
		this.id = id;
		this.user = user;
		this.engegementInPharmacies = engegementInPharmacies;
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

	public Set<EngagementInPharmacy> getEngegementInPharmacies() {
		return engegementInPharmacies;
	}

	public void setEngegementInPharmacies(Set<EngagementInPharmacy> engegementInPharmacies) {
		this.engegementInPharmacies = engegementInPharmacies;
	}

	public Set<Rating> getRatings() {
		return ratings;
	}

	public void setRatings(Set<Rating> ratings) {
		this.ratings = ratings;
	}

	public boolean hasEngagementInPharmacy(Pharmacy pharmacy) {
		for (EngagementInPharmacy e : engegementInPharmacies)
			if(e.getPharmacy().getId() == pharmacy.getId())
				return true;
		return false;
	}

	public boolean isCurrentlyHasAppointment() {
		return currentlyHasAppointment;
	}

	public void setCurrentlyHasAppointment(boolean currentlyHasAppointment) {
		this.currentlyHasAppointment = currentlyHasAppointment;
	}
	
	
	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}
}
