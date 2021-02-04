package isa.tim28.pharmacies.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import isa.tim28.pharmacies.model.MedicineMissingNotification;

public interface MedicineMissingNotificationRepository extends JpaRepository<MedicineMissingNotification, Long> {

}
