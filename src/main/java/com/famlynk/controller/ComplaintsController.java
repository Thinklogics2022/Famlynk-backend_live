package com.famlynk.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.famlynk.dto.AdminCount;
import com.famlynk.model.Complaint;
import com.famlynk.service.ComplaintsService;

@RequestMapping("/complaint")
@CrossOrigin("*")
@RestController
public class ComplaintsController {

	@Autowired
	ComplaintsService complaintService;

	@PostMapping("/add")
	public Complaint add(@RequestBody Complaint complaint) {
		return complaintService.save(complaint,complaint.getNewsFeedId());
	}
	
	@PutMapping("/update/{id}")
	public void update(@PathVariable String id, @RequestBody Complaint complaint,@RequestParam String newsFeedId) {
		complaint.setCommentId(id);
		complaintService.save(complaint,newsFeedId);
	}

	/**
	 * delete the newsFeed
	 * @param id
	 */
	@DeleteMapping("/delete/{id}")
	public void delete(@PathVariable String id) {
		complaintService.delete(id);
	}
	
	@GetMapping("/retrieve")
	public List<Complaint> getAllComplaints(){
		return complaintService.get();
	}
	
	@GetMapping("/newsFeedComplaints")
	public List<Complaint> getNewsFeedComplaints(){
		return complaintService.getNewsFeedComplaints();
	}
	
	@GetMapping("/commentComplaints")
	public List<Complaint> getCommentComplaints(){
		return complaintService.getCommentComplaints();
	}
	
	@GetMapping("/count")
    public AdminCount getComplaintCounts() {
        return complaintService.getComplaintCounts();
    }
}