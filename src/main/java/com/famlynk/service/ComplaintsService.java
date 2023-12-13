package com.famlynk.service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.famlynk.dto.AdminCount;
import com.famlynk.model.Complaint;
import com.famlynk.repository.IComplaintsRepository;
import com.famlynk.repository.INewsFeedRepository;
import com.famlynk.repository.IRegisterRepository;

@Service
public class ComplaintsService {

	@Autowired
	IComplaintsRepository complaintRepository;

	@Autowired
	INewsFeedRepository newsFeedRepository;
	
	@Autowired
	IRegisterRepository registerRepository;

	public Complaint save(Complaint complaint,String newsFeedId) {
		Date date = (Date) Calendar.getInstance().getTime();
		complaint.setCreatedOn(date);
		complaint.setNewsFeedId(newsFeedId); 
		return complaintRepository.save(complaint);
	}

	public void delete(String id) {
		newsFeedRepository.deleteById(id);
	}
	
	public List<Complaint> get(){
		return complaintRepository.findAll();
	}
	
	public List<Complaint> getNewsFeedComplaints(){
		return complaintRepository.findByType("newsfeed");
	}
	
	public List<Complaint> getCommentComplaints(){
	    return complaintRepository.findByType("comment");	
	}
	
	public AdminCount getComplaintCounts() {
	    long totalCount = complaintRepository.count();
	    long newsfeedCount = complaintRepository.countByType("newsfeed");
	    long commentCount = complaintRepository.countByType("comment");
        long registerCount = registerRepository.count();
	    AdminCount adminCount = new AdminCount();
	    adminCount.setTotalCount(totalCount);
	    adminCount.setNewsFeedCount(newsfeedCount);
	    adminCount.setCommentCount(commentCount);
        adminCount.setRegisterCount(registerCount);
	    return adminCount;
	}
}