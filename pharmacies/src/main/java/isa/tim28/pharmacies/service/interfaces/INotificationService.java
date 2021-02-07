package isa.tim28.pharmacies.service.interfaces;

import java.util.Set;

import isa.tim28.pharmacies.dtos.NotificationDTO;
import isa.tim28.pharmacies.model.Pharmacy;

public interface INotificationService {

	Set<NotificationDTO> getAll(Pharmacy pharmacy);
}
