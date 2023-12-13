package com.famlynk.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.famlynk.dto.UserComment;
import com.famlynk.model.Comment;
import com.famlynk.model.NewsFeed;
import com.famlynk.model.Register;
import com.famlynk.repository.ICommentRepository;
import com.famlynk.repository.INewsFeedRepository;
import com.famlynk.repository.IRegisterRepository;

@Service
public class CommentService {

	@Autowired
	public ICommentRepository commentRepository;
	@Autowired
	private IRegisterRepository registerRepository;
    @Autowired
    private INewsFeedRepository newsFeedRepository;
    
	public void addComment(Comment comment) {
		Date date = (Date) Calendar.getInstance().getTime();
		Register register = registerRepository.findByUserId(comment.getUserId());
		comment.setName(register.getName());
		comment.setProfilePicture(register.getProfileImage());
		comment.setCreatedOn(date);
		commentRepository.save(comment);
	}

	public Comment updateComment(Comment comment, String id) {
		Comment commentsObj = commentRepository.findById(id).get();
		commentsObj.setComment(comment.getComment());
		Date date = (Date) Calendar.getInstance().getTime();
		commentsObj.setModifiedOn(date);
		return commentRepository.save(commentsObj);
	}

	public void delete(String id) {
		commentRepository.deleteById(id);
	}

	public List<Comment> getCommentByNewsFeedId(String newsFeedId) {
		return commentRepository.findByNewsFeedId(newsFeedId);
	}

	public void deleteByNewsFeedComments(String userId, String newsFeedId) {
		commentRepository.deleteByUserIdAndNewsFeedId(userId, newsFeedId);
	}
	
	public void deleteUserComments(String userId, List<String> commentIds) {
        commentRepository.deleteByUserIdAndIdIn(userId, commentIds);
    }
	
	public List<UserComment> userComment(String userId) {
		 List<Comment> comments = commentRepository.findByUserId(userId);
		    List<UserComment> commentObj = new ArrayList<>();
		  
		    for (Comment comment : comments) {
		     	UserComment userComment = new UserComment();
		    	NewsFeed newsFeed =  newsFeedRepository.findByNewsFeedId(comment.getNewsFeedId());
		    	userComment.setId(comment.getId());
		        userComment.setNewsFeedId(comment.getNewsFeedId());
		        userComment.setUserId(comment.getUserId());
		        userComment.setFromName(comment.getName());
		        userComment.setComment(comment.getComment());
		        userComment.setCreatedOn(comment.getCreatedOn());
		        userComment.setProfilePicture(comment.getProfilePicture());
		        if(newsFeed != null) {
		        userComment.setPhoto(newsFeed.getPhoto());
		        userComment.setToName(newsFeed.getName());
		        }
		        commentObj.add(userComment);
		    }
		    return commentObj;
	}
}