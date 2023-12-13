package com.famlynk.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.famlynk.dto.FamilyTreeResponse;
import com.famlynk.dto.MutualConnectionResponse;
import com.famlynk.model.FamilyMemberMapping;
import com.famlynk.model.FamilyMembers;
import com.famlynk.model.SecondLevelRelation;
import com.famlynk.model.ThirdLevelRelation;
import com.famlynk.service.FamilyMembersService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/familymembers")
public class FamilyMembersController {

	@Autowired
	private FamilyMembersService familyMembersService;

	/**
	 * add {@link FamilyMembers}
	 * 
	 * @param familyMember
	 * @param email
	 * @return
	 */
	@PostMapping("/createfamilymembers/{email}")
	public ResponseEntity<?> addFamilyMembers(@RequestBody FamilyMembers familyMember, @PathVariable String email) {
		if (familyMember.getMobileNo() != null && familyMember.getEmail() != null) {
			boolean emailExists = familyMember.getEmail() != null
					&& familyMembersService.isEmailExists(familyMember.getEmail());
			boolean phoneExists = familyMember.getMobileNo() != null
					&& familyMembersService.isMobileNoExist(familyMember.getMobileNo());

			if (emailExists && phoneExists) {
				return ResponseEntity.badRequest().body("Email and phone number already exist");
			} else if (emailExists) {
				return ResponseEntity.badRequest().body("Email already exists");
			} else if (phoneExists) {
				return ResponseEntity.badRequest().body("Phone number already exists");
			}
		}
		FamilyMembers familyMemberObj = familyMembersService.addFamilyMember(familyMember);
		return new ResponseEntity<FamilyMembers>(familyMemberObj, HttpStatus.CREATED);
	}

	/**
	 * Update {@link FamilyMembers} by famid also in {@link FamilyMemberMapping}
	 * 
	 * @param familyMember
	 * @param famid
	 * @return
	 */
	@PutMapping("/updatefamilymembers/{famid}")
	public ResponseEntity<FamilyMembers> updateFamilyMemberByFamid(@RequestBody FamilyMembers familyMember,
			@PathVariable String famid) {
		FamilyMembers updateFamilyMember = familyMembersService.updateFamilyMember(familyMember, famid);
		return new ResponseEntity<FamilyMembers>(updateFamilyMember, HttpStatus.ACCEPTED);

	}

	/**
	 * retrieve {@link FamilyMembers} by using userId
	 * 
	 * @param userId
	 * @return
	 */
	@GetMapping("/retrieveFamilyMembers/{userId}")
	public List<FamilyMembers> retrieveFamilyMemberByUserId(@PathVariable String userId) {
		return familyMembersService.getFamilyMembersByuserId(userId);
	}

	/**
	 * gets the list of {link :FamilyTreeResponse} based on {link :
	 * {@link FamilyMemberMapping} using userId , uniqueUserID and level
	 * 
	 * @param userId
	 * @param uniqueUserID
	 * @param level
	 * @return
	 */
	@GetMapping("/getFamilyTree/{userId}/{uniqueUserID}/{level}")
	public List<FamilyTreeResponse> getFamilyTreeByUserIdAndUniqueUserIDandLevel(@PathVariable String userId,
			@PathVariable String uniqueUserID, @PathVariable String level) {
		return familyMembersService.getFamilyTree(userId, uniqueUserID, level);
	}

	/**
	 * delete family Members by userId and uniqueUserID on {@link FamilyMembers} and
	 * {@link FamilyMemberMapping}
	 * 
	 * @param userId
	 * @param uniqueUserID
	 */
	@DeleteMapping("/deletefamilymember/{userId}/{uniqueUserID}")
	public void deleteFamilyMemberByUserIdAndUniqueUserID(@PathVariable String userId,
			@PathVariable String uniqueUserID) {
		familyMembersService.deleteFamilyMember(userId, uniqueUserID);
	}

	/**
	 * add a registered user as a {@link FamilyMembers}
	 * 
	 * @param userId
	 * @param uniqueUserID
	 * @param familymMember
	 * @return
	 */
	@PostMapping("/addfamily/{userId}/{uniqueUserID}")
	public FamilyMembers addFamilyMemberByUserIdAndUniqueUserID(@PathVariable String userId,
			@PathVariable String uniqueUserID, @RequestBody FamilyMembers familymMember) {
		return familyMembersService.addFamilyMemberByUserIdAndUniqueUserID(userId, uniqueUserID, familymMember, true);
	}

	/**
	 * decline member request
	 * 
	 * @param userId
	 * @param uniqueUserID
	 * @return
	 */
	@DeleteMapping("/declinefamilymember/{userId}/{uniqueUserID}")
	public String declineFamilyMemberByUserIdAndUniqueUserID(@PathVariable String userId,
			@PathVariable String uniqueUserID) {
		familyMembersService.declineFamilyMember(userId, uniqueUserID);
		return uniqueUserID;
	}

	/**
	 * Get second level relation List
	 * 
	 * @param firstLevelRelation
	 * @return
	 */
	@GetMapping("/getsecondlevelrelation/{firstLevelRelation}")
	public List<SecondLevelRelation> getSecondLevelRelationByFirstLevelRelation(
			@PathVariable String firstLevelRelation) {
		return familyMembersService.getSecondLevelRelation(firstLevelRelation);
	}

	/**
	 * get Third Level Relation List
	 * 
	 * @param secondLevelRelation
	 * @return
	 */
	@GetMapping("/getthirdlevelrelation/{secondLevelRelation}")
	public List<ThirdLevelRelation> getThirdLevelRelationBySecondLevelRelation(
			@PathVariable String secondLevelRelation) {
		return familyMembersService.getThirdLevelRelation(secondLevelRelation);
	}

	/**
	 * get Mutual Connection
	 * 
	 * @param userId
	 * @param uniqueUserID
	 * @return
	 */

	@GetMapping("/getmutualconnection/{userId}/{uniqueUserID}")
	public List<FamilyMembers> getMutualConnectionsByUserIdAndUniqueUserID(@PathVariable("userId") String userId,
			@PathVariable("uniqueUserID") String uniqueUserID) {
		return familyMembersService.getMutualConnections(userId, uniqueUserID);
	}

	/**
	 * get mutual connection names
	 * 
	 * @param userId
	 * @param uniqueUserID
	 * @return
	 */
	@GetMapping("/getmutualconnectionnames/{userId}/{uniqueUserID}")
	public List<MutualConnectionResponse> getMutualConnectionsNamesByUserIdAndUniqueUserID(
			@PathVariable("userId") String userId, @PathVariable("uniqueUserID") String uniqueUserID) {
		return familyMembersService.getMutualConnectionsNames(userId, uniqueUserID);
	}
}
