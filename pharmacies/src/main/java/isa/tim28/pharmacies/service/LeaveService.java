package isa.tim28.pharmacies.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isa.tim28.pharmacies.dtos.LeaveRequestDTO;
import isa.tim28.pharmacies.exceptions.ForbiddenOperationException;
import isa.tim28.pharmacies.exceptions.LeaveRequestnotFoundException;
import isa.tim28.pharmacies.model.DermatologistLeaveRequest;
import isa.tim28.pharmacies.model.LeaveType;
import isa.tim28.pharmacies.model.PharmacistLeaveRequest;
import isa.tim28.pharmacies.model.Pharmacy;
import isa.tim28.pharmacies.model.PharmacyAdmin;
import isa.tim28.pharmacies.repository.DermatologistLeaveRequestRepository;
import isa.tim28.pharmacies.repository.PharmacistLeaveRequestRepository;
import isa.tim28.pharmacies.service.interfaces.IDermatologistAppointmentService;
import isa.tim28.pharmacies.service.interfaces.ILeaveService;
import isa.tim28.pharmacies.service.interfaces.IPharmacistAppointmentService;

@Service
public class LeaveService implements ILeaveService {

	private DermatologistLeaveRequestRepository dermLeaveRequestRepository;
	private PharmacistLeaveRequestRepository pharmLeaveRequestRepository;
	private IDermatologistAppointmentService dermAppService;
	private IPharmacistAppointmentService pharmAppService;
	private EmailService emailService;
	
	@Autowired
	public LeaveService(DermatologistLeaveRequestRepository dermLeaveRequestRepository,
			PharmacistLeaveRequestRepository pharmLeaveRequestRepository, EmailService emailService,
			IDermatologistAppointmentService dermAppService, IPharmacistAppointmentService pharmAppService) {
		super();
		this.dermLeaveRequestRepository = dermLeaveRequestRepository;
		this.pharmLeaveRequestRepository = pharmLeaveRequestRepository;
		this.dermAppService = dermAppService;
		this.pharmAppService = pharmAppService;
		this.emailService = emailService;
	}

	@Override
	public Set<LeaveRequestDTO> getAllWaitingReviewForPharmacists(Pharmacy pharmacy) {
		Set<LeaveRequestDTO> ret = new HashSet<LeaveRequestDTO>();
		Set<PharmacistLeaveRequest> requests = pharmLeaveRequestRepository.findAll().stream().filter(l -> l.isWaitingOnReview() && l.getPharmacist().getEngegementInPharmacy().getPharmacy().getId() == pharmacy.getId()).collect(Collectors.toSet());
		for(PharmacistLeaveRequest r : requests) {
			ret.add(new LeaveRequestDTO(r.getId(), r.getPharmacist().getUser().getFullName(), r.getStartDate(), r.getEndDate(), r.getType().toString()));
		}
		return ret;
	}

	@Override
	public Set<LeaveRequestDTO> getAllWaitingReviewForDermatologists() {
		Set<LeaveRequestDTO> ret = new HashSet<LeaveRequestDTO>();
		Set<DermatologistLeaveRequest> requests = dermLeaveRequestRepository.findAll().stream().filter(l -> l.isWaitingOnReview()).collect(Collectors.toSet());
		for(DermatologistLeaveRequest r : requests) {
			ret.add(new LeaveRequestDTO(r.getId(), r.getDermatologist().getUser().getFullName(), r.getStartDate(), r.getEndDate(), r.getType().toString()));
		}
		return ret;
	}

	@Override
	public void acceptPharmacistLeaveRequest(long leaveRequestId, PharmacyAdmin admin) throws LeaveRequestnotFoundException, ForbiddenOperationException, MessagingException {
		Optional<PharmacistLeaveRequest> requestOpt = pharmLeaveRequestRepository.findById(leaveRequestId);
		if (requestOpt.isEmpty())
			throw new LeaveRequestnotFoundException("You can't accept leave request that doesn't exist!");
		PharmacistLeaveRequest request = requestOpt.get();
		if(request.getPharmacist().getEngegementInPharmacy().getPharmacy().getId() != admin.getPharmacy().getId())
			throw new ForbiddenOperationException("You can't accept leave request for pharmacist from another pharmacy!");
		if(!request.isWaitingOnReview())
			throw new ForbiddenOperationException("You can't accept leave request that is not waiting for review!");
		if(pharmAppService.pharmacisttHasAppointmentsInTimInterval(request.getPharmacist(), request.getStartDate(), request.getEndDate()))
			throw new ForbiddenOperationException("Pharmacist has appointments in this time interval. Request can't be accepted!");
		request.accept();
		pharmLeaveRequestRepository.save(request);
		emailService.notifyEmployeeLeaveRequestIsAccepted(request.getPharmacist().getUser().getEmail(), request.getPharmacist().getUser().getFullName(), request.getType() == LeaveType.ANNUAL_LEAVE ? "odmor" : "odsustvo", request.getStartDate(), request.getEndDate());
	}

	@Override
	public void acceptDermatologistLeaveRequest(long leaveRequestId) throws ForbiddenOperationException, LeaveRequestnotFoundException, MessagingException {
		Optional<DermatologistLeaveRequest> requestOpt = dermLeaveRequestRepository.findById(leaveRequestId);
		if (requestOpt.isEmpty())
			throw new LeaveRequestnotFoundException("You can't accept leave request that doesn't exist!");
		DermatologistLeaveRequest request = requestOpt.get();
		if(!request.isWaitingOnReview())
			throw new ForbiddenOperationException("You can't accept leave request that is not waiting for review!");
		if(dermAppService.dermatologistHasAppointmentsInTimInterval(request.getDermatologist(), request.getStartDate(), request.getEndDate()))
			throw new ForbiddenOperationException("Dermatologist has appointments in this time interval. Request can't be accepted!");
		request.accept();
		dermLeaveRequestRepository.save(request);
		emailService.notifyEmployeeLeaveRequestIsAccepted(request.getDermatologist().getUser().getEmail(), request.getDermatologist().getUser().getFullName(), request.getType() == LeaveType.ANNUAL_LEAVE ? "odmor" : "odsustvo", request.getStartDate(), request.getEndDate());
	}

	@Override
	public void declinePharmacistLeaveRequest(long leaveRequestId, PharmacyAdmin admin, String reasonDeclined) throws LeaveRequestnotFoundException, ForbiddenOperationException, MessagingException {
		Optional<PharmacistLeaveRequest> requestOpt = pharmLeaveRequestRepository.findById(leaveRequestId);
		if (requestOpt.isEmpty())
			throw new LeaveRequestnotFoundException("You can't decline leave request that doesn't exist!");
		PharmacistLeaveRequest request = requestOpt.get();
		if(request.getPharmacist().getEngegementInPharmacy().getPharmacy().getId() != admin.getPharmacy().getId())
			throw new ForbiddenOperationException("You can't decline leave request for pharmacist from another pharmacy!");
		if(!request.isWaitingOnReview())
			throw new ForbiddenOperationException("You can't decline leave request that is not waiting for review!");
		request.decline();
		pharmLeaveRequestRepository.save(request);
		emailService.notifyEmployeeLeaveRequestIsDeclined(request.getPharmacist().getUser().getEmail(), request.getPharmacist().getUser().getFullName(), request.getType() == LeaveType.ANNUAL_LEAVE ? "odmor" : "odsustvo", request.getStartDate(), request.getEndDate(), reasonDeclined);
	}

	@Override
	public void declineDermatologistLeaveRequest(long leaveRequestId, String reasonDeclined) throws MessagingException, ForbiddenOperationException, LeaveRequestnotFoundException {
		Optional<DermatologistLeaveRequest> requestOpt = dermLeaveRequestRepository.findById(leaveRequestId);
		if (requestOpt.isEmpty())
			throw new LeaveRequestnotFoundException("You can't decline leave request that doesn't exist!");
		DermatologistLeaveRequest request = requestOpt.get();
		if(!request.isWaitingOnReview())
			throw new ForbiddenOperationException("You can't decline leave request that is not waiting for review!");
		request.decline();
		dermLeaveRequestRepository.save(request);
		emailService.notifyEmployeeLeaveRequestIsDeclined(request.getDermatologist().getUser().getEmail(), request.getDermatologist().getUser().getFullName(), request.getType() == LeaveType.ANNUAL_LEAVE ? "odmor" : "odsustvo", request.getStartDate(), request.getEndDate(), reasonDeclined);
	}
}
