package com.famlynk.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.famlynk.model.FamilyMembers;
import com.famlynk.model.Notify;
import com.famlynk.model.Register;
import com.famlynk.repository.IFamilyMembersRepository;
import com.famlynk.repository.INotifyRepository;
import com.famlynk.repository.IRegisterRepository;

@Service
public class NotifyService implements INotifyService {

	@Autowired
	public IRegisterRepository registerRepository;
	@Autowired
	public IFamilyMembersRepository familyMembersRepository;
	@Autowired
	public INotifyRepository notifyRepository;

	/**
	 * Adds a family notification, retrieve details from {@link Register} and
	 * {@link FamilyMembers} based on userId and uniqueUserID.
	 * 
	 * @param userId
	 * @param uniqueUserID
	 * @param relation
	 * @return notify
	 */
	@Override
	public Notify addFamilyNotify(String userId, String uniqueUserID, String relation) {
		Register fromFamily = registerRepository.findByUserId(userId);
		FamilyMembers toFamily = familyMembersRepository.findByUniqueUserIDAndRelation(uniqueUserID, "user").get(0);
		Notify notify = new Notify();
		notify.setFromUniqueUserID(fromFamily.getUniqueUserID());
		notify.setFromUserId(userId);
		notify.setFromName(fromFamily.getName());
		notify.setToUniqueUserID(uniqueUserID);
		notify.setToName(toFamily.getName());
		notify.setProfileImage(fromFamily.getProfileImage());
		notify.setRelation(relation);
		notify.setEmail(fromFamily.getEmail());
		notify.setStatus("inactive");
		notify.setIsUsed("no");
		return notifyRepository.save(notify);
	}

	@Override
	public List<Notify> getNotifyByUniqueUserID(String toUniqueUserID) {
		return notifyRepository.findByToUniqueUserID(toUniqueUserID);
	}

	@Override
	public String deleteNotify(String userId, String uniqueUserID) {
		return notifyRepository.deleteByFromUserIdAndToUniqueUserID(userId, uniqueUserID);
	}
}
