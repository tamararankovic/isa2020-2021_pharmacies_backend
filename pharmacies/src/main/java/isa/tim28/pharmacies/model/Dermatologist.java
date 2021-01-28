package isa.tim28.pharmacies.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Dermatologist {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private User user;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<EngagementInPharmacy> engegementInPharmacies = new HashSet<EngagementInPharmacy>();
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Rating> ratings = new HashSet<Rating>();

	public Dermatologist() {
		super();
	}
	
	public Dermatologist(long id, User user, Set<EngagementInPharmacy> engegementInPharmacies, Set<Rating> ratings) {
		super();
		this.id = id;
		this.user = user;
		this.engegementInPharmacies = engegementInPharmacies;
		this.ratings = ratings;
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
	
}
