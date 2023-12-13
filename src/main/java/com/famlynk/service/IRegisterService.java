package com.famlynk.service;

import java.util.List;

import com.famlynk.dto.User;
import com.famlynk.dto.MembersDetails;
import com.famlynk.dto.RegisterMemberDetail;
import com.famlynk.model.Register;

public interface IRegisterService {
	public Register addRegister(Register register);

	public Register getRegisterByUserId(String userId);

	public Register updateRegister(Register register);

	public Register getRegisterByEmail(String email);

	public Register updatePassword(Register register, String email);

	public boolean isEmailExists(String email);

	public Register updateOtp(String userId);

	public List<Register> getRegisterMembersByUserId(String userId);

	public List<MembersDetails> getMembersDetails(String userId);

	public RegisterMemberDetail getMemberDetailsByUniqueUserID(String uniqueUserID);

	public List<Register> getAllRegisters();

	public List<User> getUser();

	
}