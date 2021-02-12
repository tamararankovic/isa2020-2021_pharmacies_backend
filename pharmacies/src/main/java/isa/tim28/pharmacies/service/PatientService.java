package isa.tim28.pharmacies.service;

import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;

import isa.tim28.pharmacies.dtos.ERecepyDTO;
import isa.tim28.pharmacies.dtos.PatientProfileDTO;
import isa.tim28.pharmacies.exceptions.BadNameException;
import isa.tim28.pharmacies.exceptions.BadSurnameException;
import isa.tim28.pharmacies.exceptions.PasswordIncorrectException;
import isa.tim28.pharmacies.exceptions.PharmacyNotFoundException;
import isa.tim28.pharmacies.exceptions.UserDoesNotExistException;
import isa.tim28.pharmacies.model.EPrescription;
import isa.tim28.pharmacies.model.EPrescriptionMedicine;
import isa.tim28.pharmacies.model.Medicine;
import isa.tim28.pharmacies.model.MedicineQuantity;
import isa.tim28.pharmacies.model.Patient;
import isa.tim28.pharmacies.model.Pharmacy;
import isa.tim28.pharmacies.model.Rating;
import isa.tim28.pharmacies.model.User;
import isa.tim28.pharmacies.repository.MedicineQuantityRepository;
import isa.tim28.pharmacies.repository.PatientRepository;
import isa.tim28.pharmacies.repository.UserRepository;
import isa.tim28.pharmacies.service.interfaces.IPatientService;

@Service
public class PatientService implements IPatientService {

	private PatientRepository patientRepository;
	private UserRepository userRepository;
	private MedicineService medicineService;
	private PharmacyService pharmacyService;
	private EPrescriptionService ePrescriptionService;
	private EPrescriptionMedicineService ePrescriptionMedicineService;
	private MedicineQuantityRepository medicineQuantityRepository;
	
	@Autowired
	public PatientService(PatientRepository patientRepository, UserRepository userRepository,
			MedicineService medicineService, PharmacyService pharmacyService, 
			EPrescriptionService ePrescriptionService, EPrescriptionMedicineService ePrescriptionMedicineService,
			 MedicineQuantityRepository medicineQuantityRepository) {
		super();
		this.patientRepository = patientRepository;
		this.userRepository = userRepository;
		this.medicineService = medicineService;
		this.pharmacyService = pharmacyService;
		this.ePrescriptionService = ePrescriptionService;
		this.ePrescriptionMedicineService = ePrescriptionMedicineService;
		this.medicineQuantityRepository = medicineQuantityRepository;
		
	}
	@Override
	public void choosePharmacy(Patient patient, ERecepyDTO dto) throws PharmacyNotFoundException {
		EPrescription prescription = new EPrescription();
		prescription.setCode(String.join(";", dto.getMedicineCodes()));
		prescription.setPatientFullName(String.join(" ", patient.getUser().getName(), patient.getUser().getSurname()));
		LocalDate lt = LocalDate.now();
		prescription.setDatePrescribed(lt);
		Set<EPrescriptionMedicine> eMeds = new HashSet<EPrescriptionMedicine>();
		for(String code : dto.getMedicineCodes()) {
			Medicine medicine = medicineService.getMedicineByCode(code);
			updateMedicineQuantity(medicine, dto.getPharmacyId());
			EPrescriptionMedicine med = new EPrescriptionMedicine(medicine.getName(), medicine.getCode(), 1);
			ePrescriptionMedicineService.save(med);
			eMeds.add(med);
		}
		prescription.setPatient(patient);
		Pharmacy pharmacy = pharmacyService.getPharmacyById(dto.getPharmacyId());
		prescription.setPharmacy(pharmacy);
		prescription.setPrescriptionMedicine(eMeds);
		ePrescriptionService.save(prescription);		
		
	}
	public void updateMedicineQuantity(Medicine medicine, long pharmacyId) throws PharmacyNotFoundException {
		Pharmacy pharmacy = pharmacyService.getPharmacyById(pharmacyId);
		Set<MedicineQuantity> medicines = pharmacy.getMedicines();
		for (MedicineQuantity mq : medicines) {
			if (mq.getMedicine().getId() == medicine.getId()) {
				mq.setQuantity(mq.getQuantity() - 1);
				medicineQuantityRepository.save(mq);
				return;
			}
		}
		
	}
	
	@Override
	public List<ERecepyDTO> sortByPrice(List<ERecepyDTO> dtos){
		Collections.sort(dtos, new Comparator<ERecepyDTO>() {
		    @Override
		    public int compare(ERecepyDTO c1, ERecepyDTO c2) {
		        return Double.compare(c1.getTotalPrice(), c2.getTotalPrice());
		    }
		});
		return dtos;
	}
	
	@Override
	public List<ERecepyDTO> sortByRating(List<ERecepyDTO> dtos){
		Collections.sort(dtos, new Comparator<ERecepyDTO>() {
		    @Override
		    public int compare(ERecepyDTO c1, ERecepyDTO c2) {
		        return Double.compare(c1.getAverageRating(), c2.getAverageRating());
		    }
		});
		return dtos;
	}
	
	@Override
	public List<ERecepyDTO> sortByPharmacyName(List<ERecepyDTO> dtos){
		Collections.sort(dtos, new Comparator<ERecepyDTO>() {
		    @Override
		    public int compare(ERecepyDTO c1, ERecepyDTO c2) {
		        return c1.getNameOfPharmacy().compareTo(c2.getNameOfPharmacy());
		    }
		});
		return dtos;
	}
	
	@Override
	public List<ERecepyDTO> sortByPharmacyAddress(List<ERecepyDTO> dtos){
		Collections.sort(dtos, new Comparator<ERecepyDTO>() {
		    @Override
		    public int compare(ERecepyDTO c1, ERecepyDTO c2) {
		        return c1.getAddressOfPharmacy().compareTo(c2.getAddressOfPharmacy());
		    }
		});
		return dtos;
	}
	
	@Override
	public List<ERecepyDTO> getPharmaciesWithMedicines(String decodedQr){
		List<Medicine> medicines = new ArrayList<Medicine>();
		List<String> codes = new ArrayList<String>();
		String[] tokens = decodedQr.split(";");
		for(String t : tokens) {
			medicines.add(medicineService.getMedicineByCode(t));
			codes.add(t);
		}
		List<ERecepyDTO> pharmacies = new ArrayList<ERecepyDTO>();
		
			for(Pharmacy pharmacy : pharmacyService.getAll()) {
				if(!hasAllMedicines(pharmacy,medicines)) {
					continue;
				}else {
					double totalPrice = findTotalPrice(pharmacy,medicines);
					double averageRating = getPharmacyRating(pharmacy);
					ERecepyDTO er = new ERecepyDTO(codes,pharmacy.getId(), totalPrice, averageRating, pharmacy.getName(),pharmacy.getAddress());
					pharmacies.add(er);
				}
		}
		return pharmacies;	
	}
	private double getPharmacyRating(Pharmacy pharmacy) {
		double averageRating= 0.0;
		int sumOfRatings = 0;
		Set<Rating> ratings = pharmacy.getRatings();
		if(!ratings.isEmpty()) {
			for(Rating r : ratings) {
				sumOfRatings += r.getRating();
			}
			averageRating = sumOfRatings/ratings.size();
		}
		return averageRating;
	}
	
	private double findTotalPrice(Pharmacy pharmacy, List<Medicine> medicines) {
		double totalPrice = 0.0;
		for(Medicine m : medicines) {
			totalPrice += pharmacy.getCurrentPrice(m);
		}
		return totalPrice;
	}
	
	private boolean hasAllMedicines(Pharmacy pharmacy, List<Medicine> medicines) {
		int counter = 0;
		for(Medicine m : medicines) {
			for(MedicineQuantity medi : pharmacy.getMedicines()) {
			
				if(medi.getMedicine().getId() == m.getId() && medi.getQuantity()>0){ 
					++counter;
					break;
				}
			}
		}
		if(counter>=medicines.size()) {
			return true;
		}else {
			return false;
		}
	}
	public static String decodeQrCode(Path filePath) throws IOException {
		File img = new File(filePath.toString());
		BufferedImage bufferedImage = ImageIO.read(img);
        LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        
        try {
            Result result = new MultiFormatReader().decode(bitmap);
            return result.getText();
        } catch (NotFoundException e) {
            System.out.println("There is no QR code in the image");
            return null;
        }
	}

	@Override
	public Patient getPatientById(long id) throws UserDoesNotExistException {
		if (patientRepository.findOneByUser_Id(id) == null)
			throw new UserDoesNotExistException("Patient does not exist!");
		else
			return patientRepository.findOneByUser_Id(id);
	}

	@Override
	public User getUserPart(long id) throws UserDoesNotExistException  {
		User user = userRepository.findOneById(id);
		if (user == null)
			throw new UserDoesNotExistException("Patient does not exist!");
		else
			return user;
	}
	
	

	@Override
	public Patient editPatient(PatientProfileDTO newPatient, long id)
			throws UserDoesNotExistException, BadNameException, BadSurnameException {
		User user;
		user = getUserPart(id);
		user.setName(newPatient.getName());
		user.setSurname(newPatient.getSurname());

		Patient patient = getPatientById(id);
		patient.setAddress(newPatient.getAddress());
		patient.setCity(newPatient.getCity());
		patient.setCountry(newPatient.getCountry());
		patient.setPhone(newPatient.getPhone());

		ArrayList<String> allergies = newPatient.getAllergies();
		Set<Medicine> medicine = new HashSet<Medicine>();
		for (String s : allergies) {
			medicine.add(medicineService.getByName(s));
		}
		patient.setAllergies(medicine);

		if (!user.isNameValid())
			throw new BadNameException("Bad name. Try again.");
		if (!user.isSurnameValid())
			throw new BadSurnameException("Bad surname. Try again.");

		userRepository.save(user);
		patientRepository.save(patient);
		return patient;
	}

	@Override
	public void changePassword(long id, String newPassword) throws UserDoesNotExistException {
		User user = getUserPart(id);
		user.setPassword(newPassword);

		userRepository.save(user);
	}

	@Override
	public boolean checkOldPassword(long id, String oldPassword)
			throws UserDoesNotExistException, PasswordIncorrectException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<String> getAllMedicine(Patient patient) {
		List<Medicine> medicine = medicineService.getAllMedicine();
		ArrayList<String> allergies = getAllAllergies(patient);

		boolean medExistsInAllergies = false;
		ArrayList<String> res = new ArrayList<String>();
		if (allergies.isEmpty()) {
			for (Medicine me : medicine) {
				res.add(me.getName());
			}
		} else {
			for (Medicine m : medicine) {
				medExistsInAllergies = false;
				for (String s : allergies) {
					if (m.getName().equals(s)) {
						medExistsInAllergies = true;
					}
				}
				if (!medExistsInAllergies) {
					res.add(m.getName());
				}

			}
		}
		return res;
	}

	@Override
	public Patient save(Patient patient) {
		Patient newPatient = patientRepository.save(patient);
		return newPatient;
	}

	@Override
	public ArrayList<String> getAllAllergies(Patient patient) {
		ArrayList<String> allergy = new ArrayList<String>();

		Set<Medicine> allergies = patient.getAllergies();
		for (Medicine m : allergies) {
			allergy.add(m.getName());
		}
		return allergy;
	}

}
