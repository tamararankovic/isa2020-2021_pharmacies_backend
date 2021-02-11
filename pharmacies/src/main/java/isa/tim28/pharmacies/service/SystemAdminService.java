package isa.tim28.pharmacies.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isa.tim28.pharmacies.dtos.LoyaltyDTO;
import isa.tim28.pharmacies.exceptions.UserDoesNotExistException;
import isa.tim28.pharmacies.model.Loyalty;
import isa.tim28.pharmacies.model.LoyaltyPoints;
import isa.tim28.pharmacies.model.Patient;
import isa.tim28.pharmacies.model.SystemAdmin;
import isa.tim28.pharmacies.model.User;
import isa.tim28.pharmacies.repository.LoyaltyPointsRepository;
import isa.tim28.pharmacies.repository.PatientRepository;
import isa.tim28.pharmacies.repository.SystemAdminRepository;
import isa.tim28.pharmacies.service.interfaces.ISystemAdminService;

@Service
public class SystemAdminService implements ISystemAdminService{

	private SystemAdminRepository systemAdminRepository;
	private LoyaltyPointsRepository loyaltyPointsRepository;
	private PatientRepository patientRepository;
	
	@Autowired
	public SystemAdminService(SystemAdminRepository systemAdminRepository, LoyaltyPointsRepository loyaltyPointsRepository, PatientRepository patientRepository) {
		super();
		this.systemAdminRepository = systemAdminRepository;
		this.loyaltyPointsRepository = loyaltyPointsRepository;
		this.patientRepository = patientRepository;
	}
	
	@Override
	public SystemAdmin save(SystemAdmin systemAdmin) {
		SystemAdmin newSystemAdmin = systemAdminRepository.save(systemAdmin);
		return newSystemAdmin;
	}

	@Override
	public SystemAdmin findByUser(User user) throws UserDoesNotExistException {
		Optional<SystemAdmin> adminOpt = systemAdminRepository.findAll().stream().filter(a -> a.getUser().getId() == user.getId()).findFirst();
		if(adminOpt.isEmpty())
			throw new UserDoesNotExistException("System administrator couldn't be found!");
		return adminOpt.get();
	}
	
	@Override
	public void createLoyalty(LoyaltyDTO dto) {
		LoyaltyPoints lp = new LoyaltyPoints(dto.getPointsAfterAppointment(),dto.getPointsAfterAdvising(),
				dto.getPointsForRegular(),dto.getDiscountForRegular(), dto.getPointsForSilver(), dto.getDiscountForSilver(), dto.getPointsForGold(), dto.getDiscountForGold());
		
		loyaltyPointsRepository.save(lp);
	
		updateCathegoryOfPatients();
	}
	
	public void updateCathegoryOfPatients() {
		if(loyaltyPointsRepository.findAll() == null) {
			return;
		}else 
		{
			List<LoyaltyPoints> points = loyaltyPointsRepository.findAll();
			if(!points.isEmpty()) {
				LoyaltyPoints lp = points.get(points.size() - 1);
				List<Patient> patients = patientRepository.findAll();
				for(Patient p : patients) {
					if(p.getPoints()>=lp.getPointsForGold()) {
						p.setCategory(Loyalty.GOLD);
						patientRepository.save(p);
						continue;
					}
					else if(p.getPoints()>=lp.getPointsForSilver()) {
						p.setCategory(Loyalty.SILVER);
						patientRepository.save(p);
					}
				}
				
			}else { 
				return;
			}
		}
	}
	public void updateCathegoryOfPatient(Patient p) {
		if(loyaltyPointsRepository.findAll() == null) {
			return;
		}else 
		{
			List<LoyaltyPoints> points = loyaltyPointsRepository.findAll();
			if(!points.isEmpty()) {
				LoyaltyPoints lp = points.get(points.size() - 1);
					if(p.getPoints()>=lp.getPointsForGold()) {
						p.setCategory(Loyalty.GOLD);
						patientRepository.save(p);
						return;
					 }
					else if(p.getPoints()>=lp.getPointsForSilver()) {
						p.setCategory(Loyalty.SILVER);
						patientRepository.save(p);
					}
				
			}else { 
				return;
			}
		}
	}
}
