package isa.tim28.pharmacies.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import isa.tim28.pharmacies.model.Offer;

public interface OfferRepository  extends JpaRepository<Offer, Long> {

}
