package isa.tim28.pharmacies.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class EngagementInPharmacy {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Pharmacy pharmacy;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<DailyEngagement> dailyEngagements = new HashSet<DailyEngagement>();

	public EngagementInPharmacy() {
		super();
	}
	
	public EngagementInPharmacy(long id, Pharmacy pharmacy, Set<DailyEngagement> dailyEngagements) {
		super();
		this.id = id;
		this.pharmacy = pharmacy;
		this.dailyEngagements = dailyEngagements;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Pharmacy getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(Pharmacy pharmacy) {
		this.pharmacy = pharmacy;
	}

	public Set<DailyEngagement> getDailyEngagements() {
		return dailyEngagements;
	}

	public void setDailyEngagements(Set<DailyEngagement> dailyEngagements) {
		this.dailyEngagements = dailyEngagements;
	}
}
