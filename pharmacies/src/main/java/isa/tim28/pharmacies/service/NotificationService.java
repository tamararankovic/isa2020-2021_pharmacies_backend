package isa.tim28.pharmacies.service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isa.tim28.pharmacies.dtos.NotificationDTO;
import isa.tim28.pharmacies.model.MedicineMissingNotification;
import isa.tim28.pharmacies.model.Pharmacy;
import isa.tim28.pharmacies.repository.MedicineMissingNotificationRepository;
import isa.tim28.pharmacies.service.interfaces.INotificationService;

@Service
public class NotificationService implements INotificationService {

	private MedicineMissingNotificationRepository repository;
	
	@Autowired
	public NotificationService(MedicineMissingNotificationRepository repository) {
		super();
		this.repository = repository;
	}

	@Override
	public Set<NotificationDTO> getAll(Pharmacy pharmacy) {
		Set<MedicineMissingNotification> notifications = repository.findAll().stream().filter(n -> n.getPharmacy().getId() == pharmacy.getId()).collect(Collectors.toSet());
		Set<NotificationDTO> ret = new HashSet<NotificationDTO>();
		for(MedicineMissingNotification n : notifications) {
			ret.add(new NotificationDTO(n.getMedicine().getCode(), n.getMedicine().getName(), n.getTimestamp()));
		}
		return ret;
	}

}
