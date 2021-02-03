package isa.tim28.pharmacies.mapper;

import org.springframework.stereotype.Component;

import isa.tim28.pharmacies.dtos.PharmacistDTO;
import isa.tim28.pharmacies.model.Pharmacist;
import isa.tim28.pharmacies.model.Rating;

@Component
public class PharmacistMapper {
	
	public PharmacistDTO pharmacistToPharmacistDTO(Pharmacist pharmacist) {
		double sumRating = 0;
		for(Rating r : pharmacist.getRatings())
			sumRating += r.getRating();	
		return new PharmacistDTO(pharmacist.getId(), pharmacist.getUser().getName(), pharmacist.getUser().getSurname(), 
				pharmacist.getRatings().size() > 0 ? sumRating / pharmacist.getRatings().size() : 0, pharmacist.getEngegementInPharmacy().getPharmacy().getName());
	}
}
