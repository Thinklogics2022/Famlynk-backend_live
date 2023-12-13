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

import com.famlynk.dto.UserComment;
import com.famlynk.model.Comment;
import com.famlynk.service.CommentService;

@CrossOrigin("*")
@RestController
@RequestMapping("/comment")
public class CommentController {

	@Autowired
	public CommentService commentService;

	/**
	 * add {@link Comment}
	 * 
	 * @param comment
	 * @return
	 */
	@PostMapping("/createcomment")
	public String addComment(@RequestBody Comment comment) {
		commentService.addComment(comment);
		return "comments added";
	}

	/**
	 * Update comment by using id
	 * 
	 * @param comment
	 * @param id
	 * @return
	 */
	@PutMapping("/updatecommentbyid/{id}")
	public ResponseEntity<Comment> updateCommentById(@RequestBody Comment comment, @PathVariable String id) {
		Comment updateComment = commentService.updateComment(comment, id);
		return new ResponseEntity<Comment>(updateComment, HttpStatus.OK);
	}

	/**
	 * This will retrieve all the comments by newsFeedId
	 * 
	 * @param newsFeedId
	 * @return
	 */
	@GetMapping("/retrievecommentbyid/{newsFeedId}")
	public List<Comment> getCommentByNewsFeedId(@PathVariable String newsFeedId) {
		return commentService.getCommentByNewsFeedId(newsFeedId);
	}

	/**
	 * This method deletes the comment by using id
	 * 
	 * @param id
	 * @return
	 */
	@DeleteMapping("/deletecomment/{id}")
	public ResponseEntity deleteCommentById(@PathVariable("id") String id) {
		commentService.delete(id);
		return new ResponseEntity("Comments Deleted", HttpStatus.OK);
	}
	
	@GetMapping("/userComments/{userId}")
	public List<UserComment> userComments(@PathVariable String userId){
		return commentService.userComment(userId);
	}
	
	@DeleteMapping("/user/{userId}")
    public ResponseEntity<String> deleteUserComments( @PathVariable String userId,@RequestBody List<String> commentIds ) {
        commentService.deleteUserComments(userId, commentIds);
        return ResponseEntity.ok("Selected comments deleted successfully.");
    }
}
