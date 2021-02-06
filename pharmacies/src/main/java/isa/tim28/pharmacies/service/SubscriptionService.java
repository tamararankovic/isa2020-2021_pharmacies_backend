package isa.tim28.pharmacies.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isa.tim28.pharmacies.dtos.DealPromotionDTO;
import isa.tim28.pharmacies.dtos.PharmacyAddAdminDTO;
import isa.tim28.pharmacies.exceptions.ForbiddenOperationException;
import isa.tim28.pharmacies.exceptions.PharmacyNotFoundException;
import isa.tim28.pharmacies.exceptions.UserDoesNotExistException;
import isa.tim28.pharmacies.model.BenefitType;
import isa.tim28.pharmacies.model.Patient;
import isa.tim28.pharmacies.model.Pharmacy;
import isa.tim28.pharmacies.model.Subscription;
import isa.tim28.pharmacies.repository.SubscriptionRepository;
import isa.tim28.pharmacies.service.interfaces.IPatientService;
import isa.tim28.pharmacies.service.interfaces.IPharmacyService;
import isa.tim28.pharmacies.service.interfaces.ISubscriptionService;

@Service
public class SubscriptionService implements ISubscriptionService {

	private SubscriptionRepository subscriptionRepository;
	private IPatientService patientService;
	private IPharmacyService pharmacyService;
	private EmailService emailService;
	
	@Autowired
	public SubscriptionService(SubscriptionRepository subscriptionRepository, IPatientService patientService, IPharmacyService pharmacyService, EmailService emailService) {
		super();
		this.subscriptionRepository = subscriptionRepository;
		this.patientService = patientService;
		this.pharmacyService = pharmacyService;
		this.emailService = emailService;
	}

	
	@Override
	public Subscription save(Subscription subscription) {
		Subscription newSubscription = subscriptionRepository.save(subscription);
		return newSubscription;
	}

	@Override
	public void cancelSubscription(long userId, long pharmacyId) throws UserDoesNotExistException, PharmacyNotFoundException {
		Patient patient = patientService.getPatientById(userId);
		Pharmacy pharmacy = pharmacyService.getPharmacyById(pharmacyId);
		
		List<Subscription> subscriptions = new ArrayList<Subscription>();
		subscriptions = subscriptionRepository.findAll();
		for(Subscription s : subscriptions) {
			if(s.getPatient().getId() == patient.getId() && s.getPharmacy().getId() == pharmacy.getId()) {
				s.setCancelled(true);
				subscriptionRepository.save(s);
			}
		}
				
	}
	@Override
	public void subscribe(long userId, long pharmacyId) throws UserDoesNotExistException, PharmacyNotFoundException {
		Patient patient = patientService.getPatientById(userId);
		Pharmacy pharmacy = pharmacyService.getPharmacyById(pharmacyId);

		Subscription subscription = new Subscription();
		subscription.setPatient(patient);
		subscription.setPharmacy(pharmacy);
		subscription.setCancelled(false);
		save(subscription);
			
		
	}


	@Override
	public boolean alreadySubscribed(long userId, long pharmacyId) throws UserDoesNotExistException, PharmacyNotFoundException {
		Patient patient = patientService.getPatientById(userId);
		Pharmacy pharmacy = pharmacyService.getPharmacyById(pharmacyId);
		List<Subscription> subscriptions = new ArrayList<Subscription>();
		subscriptions = subscriptionRepository.findAll();
		for(Subscription s : subscriptions) {
			if(s.getPatient().getId() == patient.getId() && s.getPharmacy().getId() == pharmacy.getId() && s.getCancelled()==false) {
				return false;
			
				}
		}
		return true;
	}


	@Override
	public List<PharmacyAddAdminDTO> getAllSubscribedPharmacies(long userId) throws UserDoesNotExistException {
		Patient patient = patientService.getPatientById(userId);
		List<Pharmacy> pharmacies = new ArrayList<Pharmacy>();
		List<Subscription> subscriptions = new ArrayList<Subscription>();
		subscriptions = subscriptionRepository.findAll();
		for(Subscription s : subscriptions) {
			if(s.getPatient().getId() == patient.getId() && s.getCancelled()==false) {
				pharmacies.add(s.getPharmacy());
			}
		}
		List<PharmacyAddAdminDTO> pharmaciesDto = pharmaciesToDtos(pharmacies);
		return pharmaciesDto;
	}
	
	@Override
	public void sendDealOrPromotionToAllSubscribed(DealPromotionDTO dto, Pharmacy pharmacy) throws MessagingException, ForbiddenOperationException {
		if (dto.getStartDateTime().isAfter(dto.getEndDateTime()))
			throw new ForbiddenOperationException("Start of deal/promotion can't be after it's end!");
		Set<Subscription> allPharmacySubscriptions = subscriptionRepository.findAll().stream().filter(s -> s.getPharmacy().getId() == pharmacy.getId()).collect(Collectors.toSet());
		for(Subscription s : allPharmacySubscriptions) {
			if (!s.getCancelled()) {
				emailService.sendEmailDealPromotion(s.getPatient().getUser().getEmail(), pharmacy.getName(), dto.getType() == BenefitType.DEAL ? "Akcija" : "Promocija" , dto.getText(), dto.getStartDateTime(), dto.getEndDateTime());
			}
		}
	}
	
	private List<PharmacyAddAdminDTO> pharmaciesToDtos(List<Pharmacy> pharmacies){
		List<PharmacyAddAdminDTO> dtos = new ArrayList<PharmacyAddAdminDTO>();
		for(Pharmacy p : pharmacies) {
			dtos.add(new PharmacyAddAdminDTO(p.getId(),p.getName(),p.getDescription(),p.getAddress()));
		}
		return dtos;
	}
}


