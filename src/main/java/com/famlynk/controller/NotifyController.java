package com.famlynk.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.famlynk.model.FamilyMembers;
import com.famlynk.model.Notify;
import com.famlynk.service.FamilyMembersService;
import com.famlynk.service.NotifyService;

@RestController
@RequestMapping("/notify")
@CrossOrigin("*")

public class NotifyController {
	@Autowired
	public NotifyService notifyService;

	@Autowired
	public FamilyMembersService familyMembersService;

	/**
	 * It will show the response{link:Notify} of the added members
	 * 
	 * @param toUniqueUserID
	 * @return
	 */
	@GetMapping("/retrievenotifybyuniqueuserid/{toUniqueUserID}")
	public List<Notify> getNotifyByUniqueUserID(@PathVariable String toUniqueUserID) {
		return notifyService.getNotifyByUniqueUserID(toUniqueUserID);
	}

	/**
	 * Accept family members
	 * 
	 * @param userID
	 * @param uniqueUserID
	 * @param relation
	 * @return
	 */
	@GetMapping("/acceptFamilyMember/{userID}/{uniqueUserID}/{relation}/{toUniqueUserID}")
	public FamilyMembers getAcceptFamilyMember(@PathVariable String userID, @PathVariable String uniqueUserID,
			@PathVariable String relation, @PathVariable String toUniqueUserID) {
		return familyMembersService.acceptFamilyMember(userID, uniqueUserID, relation, toUniqueUserID);
	}
}
