package com.famlynk.service;

import java.util.List;

import com.famlynk.model.Notify;

public interface INotifyService {

	public Notify addFamilyNotify(String userId, String uniqueUserID, String relation);

	public List<Notify> getNotifyByUniqueUserID(String toUniqueUserID);

	public String deleteNotify(String userId, String uniqueUserID);

}
