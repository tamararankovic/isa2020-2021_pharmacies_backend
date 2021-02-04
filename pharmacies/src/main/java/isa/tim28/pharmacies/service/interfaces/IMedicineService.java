package isa.tim28.pharmacies.service.interfaces;


import java.util.List;
import isa.tim28.pharmacies.model.Medicine;

public interface IMedicineService {

	List<Medicine> getAllMedicine();

	Medicine getByName(String name);

}
