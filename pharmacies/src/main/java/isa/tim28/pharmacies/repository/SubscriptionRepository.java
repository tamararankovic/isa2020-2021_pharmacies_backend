package isa.tim28.pharmacies.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import isa.tim28.pharmacies.model.Subscription;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

	Subscription findOneByPatient_Id (long id);
}
