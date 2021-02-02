package isa.tim28.pharmacies.mapper;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import isa.tim28.pharmacies.dtos.DermatologistDTO;
import isa.tim28.pharmacies.dtos.DermatologistToEmployDTO;
import isa.tim28.pharmacies.model.Dermatologist;
import isa.tim28.pharmacies.model.EngagementInPharmacy;
import isa.tim28.pharmacies.model.Rating;

@Component
public class DermatologistMapper {
	
	public DermatologistDTO dermatologistToDermatologistDTO(Dermatologist dermatologist) {
		double sumRating = 0;
		for(Rating r : dermatologist.getRatings())
			sumRating += r.getRating();	
		Set<String> pharmacies = new HashSet<String>();
		for (EngagementInPharmacy e : dermatologist.getEngegementInPharmacies())
			pharmacies.add(e.getPharmacy().getName());
		pharmacies = pharmacies.stream().distinct().collect(Collectors.toSet());
		return new DermatologistDTO(dermatologist.getId(), dermatologist.getUser().getName(), dermatologist.getUser().getSurname(), 
					dermatologist.getRatings().size() > 0 ? sumRating / dermatologist.getRatings().size() : 0, pharmacies);
	}
	
	public DermatologistToEmployDTO dermatologistToDermatologistToEmployDTO(Dermatologist dermatologist) {
		return new DermatologistToEmployDTO(dermatologist.getId(), dermatologist.getUser().getName(), dermatologist.getUser().getSurname(), dermatologist.getUser().getEmail());
	}
}
