package com.example.PersonalOpinionVersion1.models;


public class ReplyModel extends SharedModel{
	private Long replyId;
	private String replyText;
	private PostModel post;
	private UserModel userModel;
	public Long getReplyId() {
		return replyId;
	}
	public void setReplyId(Long replyId) {
		this.replyId = replyId;
	}
	public String getReplyText() {
		return replyText;
	}
	public void setReplyText(String replyText) {
		this.replyText = replyText;
	}
	public PostModel getPost() {
		return post;
	}
	public void setPost(PostModel post) {
		this.post = post;
	}
	public UserModel getUserModel() {
		return userModel;
	}
	public void setUserModel(UserModel userModel) {
		this.userModel = userModel;
	}
	@Override
	public String toString() {
		return "ReplyModel [replyId=" + replyId + ", replyText=" + replyText + ", post=" + post + ", userModel="
				+ userModel + "]";
	}
	
	
	

}
