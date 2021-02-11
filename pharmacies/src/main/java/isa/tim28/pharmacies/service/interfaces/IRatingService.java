package isa.tim28.pharmacies.service.interfaces;

import java.util.List;

import isa.tim28.pharmacies.model.Rating;

public interface IRatingService {

	List<Rating> getRatingsByPatientId(long id);

	Rating saveRating(Rating r);
}
