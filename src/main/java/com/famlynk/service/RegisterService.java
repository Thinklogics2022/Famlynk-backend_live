package com.famlynk.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.famlynk.dto.User;
import com.famlynk.dto.MembersDetails;
import com.famlynk.dto.RegisterMemberDetail;
import com.famlynk.model.FamilyMemberMapping;
import com.famlynk.model.FamilyMembers;
import com.famlynk.model.Register;
import com.famlynk.repository.IFamilyMemberMappingRepository;
import com.famlynk.repository.IFamilyMembersRepository;
import com.famlynk.repository.IRegisterRepository;
import com.famlynk.util.Constants;
import com.famlynk.util.EmailSender;
import com.famlynk.util.IdGenerator;

@Service
public class RegisterService implements IRegisterService {

	private final IRegisterRepository registerRepository;

	private final PasswordEncoder passwordEncoder;

	private final IFamilyMembersRepository familyMembersRepository;

	@Autowired
	EmailSender emailSender;
	@Autowired
	public FamilyMembersService familyMembersService;
	@Autowired
	public IFamilyMemberMappingRepository familyMappingRepo;

	public RegisterService(IRegisterRepository registerRepositoryObj, PasswordEncoder passwordEncoder,
			IFamilyMembersRepository familyMembersRepository) {
		this.registerRepository = registerRepositoryObj;
		this.passwordEncoder = passwordEncoder;
		this.familyMembersRepository = familyMembersRepository;

	}

	@Override
	public Register addRegister(Register register) {
		register.setPassword(passwordEncoder.encode(register.getPassword()));
		register.setRole("USER");
		register.setStatus(true);
		Date date = (Date) Calendar.getInstance().getTime();
		register.setCreatedOn(date);
		register.setUserId(IdGenerator.randomIdGenerator());
		register.setEnabled(false);
		String uUserId = IdGenerator.randomUniqueUserId(register.getName());
		register.setUniqueUserID(uUserId);

//		family members
		FamilyMembers familyMember = new FamilyMembers();
		familyMember.setName(register.getName());
		familyMember.setUserId(register.getUserId());
		familyMember.setDob(register.getDateOfBirth());
		familyMember.setGender(register.getGender());
		familyMember.setEmail(register.getEmail());
		familyMember.setMobileNo(register.getMobileNo());
		familyMember.setImage(register.getProfileImage());
		familyMember.setRelation(Constants.USER);
		familyMember.setFirstLevelRelation(Constants.USER);
		familyMember.setUniqueUserID(uUserId);
		familyMember.setRegisterUser(true);
		registerRepository.save(register);
		familyMembersRepository.save(familyMember);

		String otpString = emailSender.createOtpString();
		register.setOtp(otpString);
		Register registerObj = registerRepository.save(register);
		emailSender.sendEmail(register.getEmail(), register.getName(), otpString);
		emailSender.expireOTP(register);
		return registerObj;
	}

	@Override
	public Register updateRegister(Register register) {
		List<FamilyMembers> familyMembers = familyMembersRepository.findByUniqueUserID(register.getUniqueUserID());
		List<FamilyMemberMapping> familyMemberMapping = familyMappingRepo
				.findBySecondaryMemberId(register.getUniqueUserID());
		Date date = (Date) Calendar.getInstance().getTime();
		register.setModifiedOn(date);
		Register registerObj = registerRepository.save(register);
		for (FamilyMembers familyMembersObj : familyMembers) {
			familyMembersObj.setImage(registerObj.getProfileImage());
			familyMembersObj.setName(registerObj.getName());
			familyMembersObj.setGender(registerObj.getGender());
			familyMembersObj.setMaritalStatus(registerObj.getMaritalStatus());
			familyMembersObj.setAddress(registerObj.getAddress());
			familyMembersObj.setHometown(registerObj.getHometown());
			familyMembersRepository.save(familyMembersObj);
		}
		for (FamilyMemberMapping familyMemberMappingObj : familyMemberMapping) {
			familyMemberMappingObj.setImage(registerObj.getProfileImage());
			familyMappingRepo.save(familyMemberMappingObj);
		}
		familyMembersService.evictFamilyTreeCache();
		return registerObj;
	}

	@Override
	public Register getRegisterByUserId(String userId) {
		return registerRepository.findByUserId(userId);
	}

	public boolean isEmailExists(String email) {
		return registerRepository.existsByEmail(email);
	}

	@Override
	public Register updatePassword(Register register, String email) {
		Register registerObj = registerRepository.findByEmail(email);
		registerObj.setPassword(passwordEncoder.encode(register.getPassword()));
		return registerRepository.save(registerObj);

	}

	@Override
	public Register getRegisterByEmail(String email) {
		Register register = registerRepository.findByEmail(email);
		if (register != null) {
			String otpString = emailSender.createOtpString();
			register.setOtp(otpString);
			registerRepository.save(register);
			emailSender.sendEmail(register.getEmail(), register.getName(), otpString);
			emailSender.expireOTP(register);
		}
		return register;
	}

	@Override
	public Register updateOtp(String userId) {
		Register register = registerRepository.findByUserId(userId);
		Random randomotp = new Random();
		int otp = 100000 + randomotp.nextInt(900000);
		String otpString = Integer.toString(otp);
		register.setOtp(otpString);
		emailSender.sendEmail(register.getEmail(), register.getName(), otpString);
		emailSender.expireOTP(register);
		registerRepository.save(register);
		return register;
	}

	public List<Register> getRegisterMembersByUserId(String userId) {
		List<FamilyMembers> familyMembers = familyMembersRepository.findByUserId(userId);
		Set<String> familyMembersObj = familyMembers.stream().map(FamilyMembers::getUniqueUserID)
				.collect(Collectors.toSet());
		return registerRepository.findByUniqueUserIDNotIn(familyMembersObj);
	}

	/**
	 * It will list the particular member details{link: MembersDetails} based on
	 * {link :User}
	 * 
	 * @param userId
	 * @return result show the MembersDetails
	 */
	@Override
	public List<MembersDetails> getMembersDetails(String userId) {
		List<MembersDetails> result = new ArrayList<>();
		MembersDetails memberDetails = new MembersDetails();
		Register registerDetails = registerRepository.findByUserId(userId);
		memberDetails.setName(registerDetails.getName());
		memberDetails.setGender(registerDetails.getGender());
		memberDetails.setEmail(registerDetails.getEmail());
		memberDetails.setDateOfBirth(registerDetails.getDateOfBirth());
		memberDetails.setUniqueUserID(registerDetails.getUniqueUserID());
		memberDetails.setMobileNo(registerDetails.getMobileNo());
		memberDetails.setUserId(userId);
		memberDetails.setMaritalStatus(registerDetails.getMaritalStatus());
		memberDetails.setHometown(registerDetails.getHometown());
		memberDetails.setAddress(registerDetails.getAddress());
		memberDetails.setProfileImage(registerDetails.getProfileImage());
		result.add(memberDetails);
		return result;
	}

	@Override
	public RegisterMemberDetail getMemberDetailsByUniqueUserID(String uniqueUserID) {
		ModelMapper modelMapper = new ModelMapper();
		Register register = registerRepository.findByUniqueUserID(uniqueUserID);
		RegisterMemberDetail registerMemberDetail = new RegisterMemberDetail();
		if (register != null) {
			registerMemberDetail = modelMapper.map(register, RegisterMemberDetail.class);
			registerMemberDetail.setIsRegisterUser(true);
		} else {
			List<FamilyMembers> familyMembers = familyMembersRepository.findByUniqueUserID(uniqueUserID);
			if (!familyMembers.isEmpty()) {
				FamilyMembers familyMembersObj = familyMembers.get(0);
				registerMemberDetail = modelMapper.map(familyMembersObj, RegisterMemberDetail.class);
				registerMemberDetail.setIsRegisterUser(false);
			}
		}
		return registerMemberDetail;
	}
	
	public List<Register> getAllRegisters() {
		return registerRepository.findByRole("USER");
	}
	
	@Override
	public List<User> getUser() {
		 List<User> userList = new ArrayList<>(); 
		    List<Register> registerList = registerRepository.findAll();
		    for (Register register : registerList) {
		        User user = new User();
		        user.setName(register.getName());
		        user.setDateOfBirth(register.getDateOfBirth());
		        user.setProfileImage(register.getProfileImage());
		        user.setUniqueUserID(register.getUniqueUserID());
		        user.setUserId(register.getUserId());
		        user.setGender(register.getGender());
		        userList.add(user); 
		    }
		return userList;
	}
}