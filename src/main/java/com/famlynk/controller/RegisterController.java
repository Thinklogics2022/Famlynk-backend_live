package com.famlynk.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.famlynk.dto.User;
import com.famlynk.dto.MembersDetails;
import com.famlynk.dto.RegisterMemberDetail;
import com.famlynk.model.FamilyMemberMapping;
import com.famlynk.model.FamilyMembers;
import com.famlynk.model.Register;
import com.famlynk.service.IRegisterService;
import com.famlynk.util.EmailSender;

@CrossOrigin("*")
@RestController
@RequestMapping("/register")
public class RegisterController {

	@Autowired
	private IRegisterService registerService;
	@Autowired
	private EmailSender emailSender;

	/**
	 * This is used for creating user and also added to {@link FamilyMembers} It
	 * checks the email already exists or not
	 * 
	 * @param register
	 * @return
	 */
	@PostMapping("/createregister")
	public ResponseEntity<?> addRegister(@RequestBody Register register) {
		register.setId(register.getEmail());
		String email = register.getEmail();
		if (registerService.isEmailExists(email)) {
			return ResponseEntity.badRequest().body("Email already exists");
		}
		Register addRegister = registerService.addRegister(register);
		return new ResponseEntity<Register>(addRegister, HttpStatus.CREATED);
	}

	/**
	 * It will fetch the particular registered member details using userId
	 * 
	 * @param id ---userId
	 * @return
	 */
	@GetMapping("/retrieveregisterbyid/{userId}")
	public Register getRegisterByUserId(@PathVariable String userId) {
		return registerService.getRegisterByUserId(userId);
	}

	/**
	 * user update Also update in {@link FamilyMembers} and
	 * {@link FamilyMemberMapping}
	 * 
	 * @param register
	 * @return
	 */
	@PutMapping("/updateregisterbyid/{userId}")
	public ResponseEntity<Register> updateRegister(@RequestBody Register register) {
		Register updateRegister = registerService.updateRegister(register);
		return new ResponseEntity<Register>(updateRegister, HttpStatus.OK);
	}

	/**
	 * update password by using email
	 * 
	 * @param register
	 * @param email
	 * @return
	 */
	@PutMapping("/updatepasswordbyemail/{email}")
	public ResponseEntity<Register> updatePassword(@RequestBody Register register, @PathVariable String email) {
		Register updatePassword = registerService.updatePassword(register, email);
		return new ResponseEntity<Register>(updatePassword, HttpStatus.OK);
	}

	/**
	 * This method retrieves a Register by using email and sends an OTP to that
	 * email
	 * 
	 * @param email
	 * @return
	 */
	@GetMapping("/retrieveregisterbyemailforpassword/{email}")
	public Register getEmail(@PathVariable("email") String email) {
		return registerService.getRegisterByEmail(email);
	}

	/**
	 * verify a person by using OTP
	 * 
	 * @param otp
	 * @return
	 */
	@GetMapping("/verifyotp/{otp}")
	public String verifyOtp(@PathVariable("otp") String otp) {
		return emailSender.verifyOtp(otp);
	}

	/**
	 * Generates a random OTP for the specified user, updates it in the Register
	 * object, and sends an email notification to expire the previous OTP.
	 *
	 * @param userId the ID of the user to update the OTP for
	 * @return
	 */

	@GetMapping("/resend/{userId}")
	public Register resendOtp(@PathVariable("userId") String userId) {
		return registerService.updateOtp(userId);
	}

	/**
	 * retrieve members from Register excluding FamilyMembers
	 * 
	 * @param userId
	 * @return
	 */
	@GetMapping("/retrievemembers/{userId}")
	public List<Register> getMembersByUserId(@PathVariable String userId) {
		return registerService.getRegisterMembersByUserId(userId);
	}

	@GetMapping("/retrievemembersbyuserid/{userId}")
	public List<MembersDetails> getMemberDetails(@PathVariable String userId) {
		return registerService.getMembersDetails(userId);
	}

	/**
	 * This method uses ModelMapper to perform the mapping of data between
	 * {@link Register} and the {@link RegisterMemberDetail} DTO (Data Transfer
	 * Object) class.
	 * 
	 * @param uniqueUserID
	 * @return
	 */
	@GetMapping("/getmemberprofile/{uniqueUserID}")
	public RegisterMemberDetail getMemberDetailByUniqueUserID(@PathVariable String uniqueUserID) {
		return registerService.getMemberDetailsByUniqueUserID(uniqueUserID);
	}
	
	/**
	 * retrieve all register users
	 */
	@GetMapping("/retrieveRegisterUsers")
	 public List<Register> getAllRegisteredUsers(){
		return registerService.getAllRegisters();	
    }
	
	/**
	 * view one user preview in admin dashboard 
	 * @param uniqueUserID
	 * @return
	 */
	@GetMapping("/users")
	public List<User> getUser() {
		return registerService.getUser();
	}
}