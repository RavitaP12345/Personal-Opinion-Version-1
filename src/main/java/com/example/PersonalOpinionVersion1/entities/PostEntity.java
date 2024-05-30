package com.example.PersonalOpinionVersion1.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "post_details")
public class PostEntity extends SharedEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long postId;
	private String title;
	@Lob
	@Column(columnDefinition = "TEXT")
	private String message;
	@ManyToOne()
	@JoinColumn(name = "user_id")
	private UserEntity user;
	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
	private List<ReplyEntity> reply;
	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
	private List<LikeDislikeEntity> likeDislike;
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
	public UserEntity getUser() {
		return user;
	}
	public void setUser(UserEntity user) {
		this.user = user;
	}
	public List<ReplyEntity> getReply() {
		return reply;
	}
	public void setReply(List<ReplyEntity> reply) {
		this.reply = reply;
	}
	public List<LikeDislikeEntity> getLikeDislike() {
		return likeDislike;
	}
	public void setLikeDislike(List<LikeDislikeEntity> likeDislike) {
		this.likeDislike = likeDislike;
	}
	
	

}
