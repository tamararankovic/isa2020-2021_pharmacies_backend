package isa.tim28.pharmacies.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isa.tim28.pharmacies.model.Rating;
import isa.tim28.pharmacies.repository.RatingRepository;
import isa.tim28.pharmacies.service.interfaces.IRatingService;

@Service
public class RatingService implements IRatingService {

	private RatingRepository ratingRepository;
	
	@Autowired
	public RatingService(RatingRepository ratingRepository) {
		this.ratingRepository = ratingRepository;
	}
	
	@Override
	public List<Rating> getRatingsByPatientId(long id) {
		return ratingRepository.findByPatient_Id(id);
	}
	
	@Override 
	public Rating saveRating(Rating r) {
		Rating rating = ratingRepository.save(r);
		return rating;
	}
	

}
