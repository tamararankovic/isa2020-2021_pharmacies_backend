package isa.tim28.pharmacies.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import isa.tim28.pharmacies.model.Rating;

public interface RatingRepository extends JpaRepository<Rating, Long> {
	
	List<Rating> findByPatient_Id(long id);

}
