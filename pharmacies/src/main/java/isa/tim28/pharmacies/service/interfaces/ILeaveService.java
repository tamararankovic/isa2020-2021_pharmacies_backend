package isa.tim28.pharmacies.service.interfaces;

import java.util.Set;

import javax.mail.MessagingException;

import isa.tim28.pharmacies.dtos.LeaveRequestDTO;
import isa.tim28.pharmacies.exceptions.ForbiddenOperationException;
import isa.tim28.pharmacies.exceptions.LeaveRequestnotFoundException;
import isa.tim28.pharmacies.model.Pharmacy;
import isa.tim28.pharmacies.model.PharmacyAdmin;

public interface ILeaveService {

	Set<LeaveRequestDTO> getAllWaitingReviewForPharmacists(Pharmacy pharmacy);
	
	Set<LeaveRequestDTO> getAllWaitingReviewForDermatologists();
	
	void acceptPharmacistLeaveRequest(long leaveRequestId, PharmacyAdmin admin) throws LeaveRequestnotFoundException, ForbiddenOperationException, MessagingException;
	
	void acceptDermatologistLeaveRequest(long leaveRequestId) throws ForbiddenOperationException, LeaveRequestnotFoundException, MessagingException;
	
	void declinePharmacistLeaveRequest(long leaveRequestId, PharmacyAdmin admin, String reasonDeclined) throws LeaveRequestnotFoundException, ForbiddenOperationException, MessagingException;
	
	void declineDermatologistLeaveRequest(long leaveRequestId, String reasonDeclined) throws MessagingException, ForbiddenOperationException, LeaveRequestnotFoundException;
}
