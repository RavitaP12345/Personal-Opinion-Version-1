package com.example.PersonalOpinionVersion1.models;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
public class PostModel extends SharedModel{
	private Long postId;
	@NotNull(message = "Title must not be null.")
	@NotBlank(message = "Title must not be blank.")
	@NotEmpty(message = "Title must not be empty.")
	private String title;
	@NotNull(message = "Message must not be null.")
	@NotBlank(message = "Messaage must not be blank.")
	@NotEmpty(message = "Message must not be empty.")
	private String message;
	private UserModel user;
	private List<ReplyModel> replyList;
	private List<LikeDislikeModel> likeDislikeList;
	private String likeCount;
	private String dislikeCount;
	private String commentCount;
	public Long getPostId() {
		return postId;
	}
	public void setPostId(Long postId) {
		this.postId = postId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public UserModel getUser() {
		return user;
	}
	public void setUser(UserModel user) {
		this.user = user;
	}
	public List<ReplyModel> getReplyList() {
		return replyList;
	}
	public void setReplyList(List<ReplyModel> replyList) {
		this.replyList = replyList;
	}
	public List<LikeDislikeModel> getLikeDislikeList() {
		return likeDislikeList;
	}
	public void setLikeDislikeList(List<LikeDislikeModel> likeDislikeList) {
		this.likeDislikeList = likeDislikeList;
	}
	
	public String getLikeCount() {
		return likeCount;
	}
	public void setLikeCount(String likeCount) {
		this.likeCount = likeCount;
	}
	public String getDislikeCount() {
		return dislikeCount;
	}
	public void setDislikeCount(String dislikeCount) {
		this.dislikeCount = dislikeCount;
	}
	public String getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(String commentCount) {
		this.commentCount = commentCount;
	}
	@Override
	public String toString() {
		return "PostModel [postId=" + postId + ", title=" + title + ", message=" + message + ", user=" + user
				+ ", replyList=" + replyList + ", likeDislikeList=" + likeDislikeList + "]";
	}
	
	
	

}
