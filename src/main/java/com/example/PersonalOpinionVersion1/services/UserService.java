package com.example.PersonalOpinionVersion1.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.PersonalOpinionVersion1.entities.ContactEntity;
import com.example.PersonalOpinionVersion1.entities.LikeDislikeEntity;
import com.example.PersonalOpinionVersion1.entities.PostEntity;
import com.example.PersonalOpinionVersion1.entities.ReplyEntity;
import com.example.PersonalOpinionVersion1.entities.UserEntity;
import com.example.PersonalOpinionVersion1.models.ContactModel;
import com.example.PersonalOpinionVersion1.models.LikeDislikeModel;
import com.example.PersonalOpinionVersion1.models.PostModel;
import com.example.PersonalOpinionVersion1.models.ReplyModel;
import com.example.PersonalOpinionVersion1.models.UserModel;
import com.example.PersonalOpinionVersion1.models.UserModelV1;
import com.example.PersonalOpinionVersion1.repositories.ContactRepository;
import com.example.PersonalOpinionVersion1.repositories.LikeDislikeRepository;
import com.example.PersonalOpinionVersion1.repositories.PostRepository;
import com.example.PersonalOpinionVersion1.repositories.ReplyRepository;
import com.example.PersonalOpinionVersion1.repositories.UserRepository;

import jakarta.persistence.EntityExistsException;
import jakarta.transaction.Transactional;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	ContactRepository contactRepository;
	@Autowired
	PostRepository postRepository;
	@Autowired
	ReplyRepository replyRepository;
	@Autowired
	LikeDislikeRepository likeDislikeRepository;
	
	
	
	
	//UserDetails start..............................
	@Transactional
	public String saveUserDetails(UserModel userModel) {
		try {
			UserEntity userEntity = new UserEntity();
			userEntity.setName(userModel.getName());
			userEntity.setEmail(userModel.getEmail());
			userEntity.setMobileNumber(userModel.getMobileNumber());
			userEntity.setPassword(passwordEncoder.encode(userModel.getPassword()));
			userEntity.setProfile(userModel.getProfile());
			userEntity.setRole(userModel.getRole());
			userEntity.setCreatedBy(String.valueOf(userEntity.getUserId()));
			userEntity.setUpdatedBy(String.valueOf(userEntity.getUserId()));
			userEntity.setCreateionDate(new Date());
			userEntity.setUpdateDate(new Date());
			userEntity.setStatus("Active");
			userEntity.setAbout(userModel.getAbout());
			userRepository.save(userEntity);
			return "success";
		}catch (Exception e) {
			return "failed";
		}
		
	}
	
	public UserModel getUserByUserEmail(String email) {
		UserModel userModel = new UserModel();
		try {
			UserEntity userEntity = userRepository.findByEmail(email);
			if(userEntity.getStatus().equalsIgnoreCase("Active")) {
				userModel.setEmail(userEntity.getEmail());
				userModel.setPassword(userEntity.getPassword());
				userModel.setRole(userEntity.getRole());
				userModel.setUserId(userEntity.getUserId());
				return userModel;
			}else {
				return userModel;
			}
			
			
		}catch (Exception e) {
			return userModel;
		}
	}
	
	@Transactional
	public String updateUserDetailsByUserId(UserModelV1 userModel) {
		System.out.println("userModel=====>"+userModel);
		try {
			UserEntity userEntity = userRepository.findByUserId(userModel.getUserId());
			System.out.println("userEntity====>"+userEntity);
			userEntity.setName(userModel.getName());
			userEntity.setEmail(userModel.getEmail());
			userEntity.setMobileNumber(userModel.getMobileNumber());
			if(userModel.getPassword()==null || userModel.getPassword().equalsIgnoreCase("")) {
				
			}else {
				userEntity.setPassword(passwordEncoder.encode(userModel.getPassword()));
			}
			if(userModel.getProfile()==null || userModel.getProfile().equalsIgnoreCase("")) {
				
			}else {
				userEntity.setProfile(userModel.getProfile());
			}
			userEntity.setUpdatedBy(String.valueOf(userEntity.getUserId()));
			userEntity.setUpdateDate(new Date());
			userEntity.setAbout(userModel.getAbout());
			userRepository.save(userEntity);
			return "update";
		}catch (EntityExistsException e) {
			return "Exist";
			
		}catch (Exception e) {
			return "failed";
		}
	}
	
	
	public UserModel getUserDetailsByUserId(String userId) {
		UserEntity userEntity = userRepository.findByUserId(Long.valueOf(userId));
		UserModel userModel = new UserModel();
		userModel.setUserId(userEntity.getUserId());
		userModel.setName(userEntity.getName());
		userModel.setEmail(userEntity.getEmail());
		userModel.setMobileNumber(userEntity.getMobileNumber());
		userModel.setProfile(userEntity.getProfile());
		userModel.setUpdatedBy(userEntity.getUpdatedBy());
		userModel.setUpdateDate(String.valueOf(userEntity.getUpdateDate()));
		userModel.setAbout(userEntity.getAbout());
		return userModel;
	}
	
	public List<UserModel> getAllActiveUsers(){
		List<UserModel> userModels = new ArrayList<>();
		List<UserEntity> userEntities = userRepository.findAll();
		userEntities.stream().filter(user -> user.getStatus().equalsIgnoreCase("Active")).forEach(data->{
			UserModel userModel = new UserModel();
			userModel.setUserId(data.getUserId());
			userModel.setName(data.getName());
			userModel.setEmail(data.getEmail());
			userModel.setMobileNumber(data.getMobileNumber());
			userModel.setProfile(data.getProfile());
			userModel.setUpdatedBy(data.getUpdatedBy());
			userModel.setUpdateDate(String.valueOf(data.getUpdateDate()));
			userModel.setAbout(data.getAbout());
			userModels.add(userModel);
			
		});
		return userModels;
	}
	@Transactional
	public String deleteUserByUserId(String userId) {
		try {
			UserEntity userEntity = userRepository.findByUserId(Long.valueOf(userId));
			userEntity.setStatus("Inactive");
			userRepository.save(userEntity);
			return "success";
		}catch (Exception e) {
			return "failed";
		}
		
		
	}
	
	
	//UserDetails end........................

	//ContactDetails start...........................
	@Transactional
	public String saveContactDetails(ContactModel contactModel) {
		try {
			ContactEntity contactEntity = new ContactEntity();
			contactEntity.setName(contactModel.getName());
			contactEntity.setEmail(contactModel.getEmail());
			contactEntity.setMessage(contactModel.getMessage());
			contactEntity.setCreatedBy(contactModel.getName());
			contactEntity.setUpdatedBy(contactModel.getName());
			contactEntity.setStatus("Active");
			contactEntity.setUpdateDate(new Date());
			contactEntity.setCreateionDate(new Date());
			contactRepository.save(contactEntity);
			return "success";
		}catch (Exception e) {
			return "failed";
		}
		
	}

	@Transactional
	public String updateContactDetails(ContactModel contactModel) {
		try {
			ContactEntity contactEntity = contactRepository.findByContactId(contactModel.getContactId());
			contactEntity.setEmail(contactModel.getEmail());
			contactEntity.setMessage(contactModel.getMessage());
			contactEntity.setName(contactModel.getName());
			contactEntity.setCreatedBy(contactModel.getCreatedBy());
			contactEntity.setUpdatedBy(contactModel.getCreatedBy());
			contactEntity.setUpdateDate(new Date());
			contactRepository.save(contactEntity);
			return "success";
		}catch (Exception e) {
			return "failed";
		}
	}
	
	@Transactional
	public String deleteContactDetails(String contactId) {
		try {
			ContactEntity contactEntity = contactRepository.findByContactId(Long.valueOf(contactId));
			contactEntity.setStatus("Inactive");
			contactRepository.save(contactEntity);
			return "success";
		}catch (Exception e) {
			return "failed";
		}
	}
	
	public ContactModel getActiveContactDetailsByContactId(String contactId) {
		List<ContactEntity> contactEntities = contactRepository.findAllByContactId(Long.valueOf(contactId));
		ContactModel contactModel = new ContactModel();
		contactEntities.stream().filter(contact -> contact.getStatus().equalsIgnoreCase("Active")).forEach(data->{
			contactModel.setContactId(data.getContactId());
			contactModel.setEmail(data.getEmail());
			contactModel.setMessage(data.getMessage());
			contactModel.setName(data.getName());
			contactModel.setUpdatedBy(data.getUpdatedBy());
			contactModel.setUpdateDate(String.valueOf(data.getUpdateDate()));
		});
		return contactModel;
	}
	
	public List<ContactModel> getAllActiveContactDetails(){

		List<ContactModel> contactModels = new ArrayList<>();
		List<ContactEntity> contactEntities = contactRepository.findAll();
		contactEntities.stream().filter(contact-> contact.getStatus().equalsIgnoreCase("Active")).forEach(data->{
			ContactModel contactModel = new ContactModel();
			contactModel.setContactId(data.getContactId());
			contactModel.setName(data.getName());
			contactModel.setMessage(data.getMessage());
			contactModel.setEmail(data.getEmail());
			contactModel.setUpdatedBy(data.getUpdatedBy());
			contactModel.setUpdateDate(String.valueOf(data.getUpdateDate()));
			contactModels.add(contactModel);
		});
		return contactModels;
		
	}
	//Contact Details end...................
	
	//Post Details start...................
	@Transactional
	public String savePostDetails(PostModel postModel) {
		System.out.println("postModel===========>"+postModel);
		try {
			PostEntity postEntity = new PostEntity();
			postEntity.setTitle(postModel.getTitle());
			postEntity.setMessage(postModel.getMessage());
			postEntity.setCreatedBy(String.valueOf(postModel.getUser().getUserId()));
			postEntity.setUpdatedBy(String.valueOf(postModel.getUser().getUserId()));
			postEntity.setCreateionDate(new Date());
			postEntity.setUpdateDate(new Date());
			postEntity.setStatus("Active");
			UserEntity userEntity = userRepository.findByUserId(postModel.getUser().getUserId());
			System.out.println("userEntity====>"+userEntity);
			postEntity.setUser(userEntity);
			postRepository.save(postEntity);
			return "success";
		}catch (Exception e) {
			return "Failed";
		}
		
	}
	
	public List<PostModel> getAllActivePostsByUserId(String userId) {
		List<PostModel> postModels = new ArrayList<>();
		List<PostEntity> postEntities = postRepository.findByUser(userRepository.findByUserId(Long.valueOf(userId)));
		postEntities.stream().filter(post-> post.getStatus().equalsIgnoreCase("Active")).forEach(data->{
			PostModel postModel = new PostModel();
			postModel.setPostId(data.getPostId());
			postModel.setMessage(data.getMessage());
			postModel.setTitle(data.getTitle());
			List<ReplyModel> replyModels = new ArrayList<>();
			List<LikeDislikeModel> likeDislikeModels = new ArrayList<>();
			data.getReply().stream().filter(reply-> reply.getStatus().equalsIgnoreCase("Active")).forEach(rpl->{
				ReplyModel replyModel = new ReplyModel();
				replyModel.setReplyId(rpl.getReplyId());
				UserModel replierModel = getUserDetailsByUserId(rpl.getUserId());
				replyModel.setUserModel(replierModel);
				replyModels.add(replyModel);
			});
			data.getLikeDislike().stream().filter(linkeDeslike-> linkeDeslike.getStatus().equalsIgnoreCase("Active")).forEach(ld->{
				LikeDislikeModel likeDislikeModel = new LikeDislikeModel();
				likeDislikeModel.setId(ld.getId());
				likeDislikeModel.setContent(ld.getContent());
				UserModel replierModel = getUserDetailsByUserId(ld.getUserId());
				likeDislikeModel.setUserModel(replierModel);
				likeDislikeModels.add(likeDislikeModel);
			});
			postModel.setReplyList(replyModels);
			postModel.setReplyList(replyModels);
			postModel.setUser(getUserDetailsByUserId(String.valueOf(data.getUser().getUserId())));
			postModel.setUpdateDate(String.valueOf(data.getUpdateDate()));
			postModels.add(postModel);
			
		});
		return postModels;
		
	}
	
	
	public PostModel getPostDetailsByPostId(Long postId) {
		PostEntity postEntity = postRepository.findByPostId(postId);
		PostModel postModel = new PostModel();
		postModel.setPostId(postEntity.getPostId());
		postModel.setTitle(postEntity.getTitle());
		postModel.setMessage(postEntity.getMessage());
		postModel.setUpdatedBy(postEntity.getUpdatedBy());
		return postModel;
	}
	
	public List<PostModel> getAllActivePosts(){
		List<PostModel> postModels = new ArrayList<>();
		List<PostEntity> postEntities = postRepository.findAll();
		postEntities.stream().filter(post-> post.getStatus().equalsIgnoreCase("Active")).forEach(data->{
			PostModel postModel = new PostModel();
			postModel.setPostId(data.getPostId());
			postModel.setMessage(data.getMessage());
			postModel.setTitle(data.getTitle());
			long likeCount = likeDislikeRepository.findAll().stream().filter(action -> action.getContent().equalsIgnoreCase("like") && action.getPost().getPostId()== data.getPostId()).count();
			long dislikeCount = likeDislikeRepository.findAll().stream().filter(action -> action.getContent().equalsIgnoreCase("dislike") && action.getPost().getPostId()== data.getPostId()).count();

			postModel.setLikeCount(String.valueOf(likeCount));
			postModel.setDislikeCount(String.valueOf(dislikeCount));
			
			long commentCount = replyRepository.findAll().stream().filter(reply -> reply.getPost().getPostId()== data.getPostId()).count();
			postModel.setCommentCount(String.valueOf(commentCount));
			UserEntity userEntity = userRepository.findByUserId(data.getUser().getUserId());
			UserModel userModel = new UserModel();
			userModel.setName(userEntity.getName());
			userModel.setProfile(userEntity.getProfile());
			postModel.setUser(userModel);
			postModel.setUpdateDate(String.valueOf(data.getUpdateDate()));
			List<ReplyModel> replyModels = new ArrayList<>();
			List<LikeDislikeModel> likeDislikeModels = new ArrayList<>();
			data.getReply().stream().filter(reply-> reply.getStatus().equalsIgnoreCase("Active")).forEach(rpl->{
				ReplyModel replyModel = new ReplyModel();
				replyModel.setReplyId(rpl.getReplyId());
				replyModel.setReplyText(rpl.getReplyText());
				replyModel.setUpdateDate(String.valueOf(rpl.getUpdateDate()));
				System.out.println("rpl.getUserId()====>"+rpl.getUserId());
				UserModel replierModel = getUserDetailsByUserId(rpl.getUserId());
				System.out.println("replierModel====>"+replierModel);
				replyModel.setUserModel(replierModel);
				replyModels.add(replyModel);
			});
			data.getLikeDislike().stream().filter(linkeDeslike-> linkeDeslike.getStatus().equalsIgnoreCase("Active")).forEach(ld->{
				LikeDislikeModel likeDislikeModel = new LikeDislikeModel();
				likeDislikeModel.setId(ld.getId());
				likeDislikeModel.setContent(ld.getContent());
				UserModel replierModel = getUserDetailsByUserId(ld.getUserId());
				likeDislikeModel.setUserModel(replierModel);
				likeDislikeModels.add(likeDislikeModel);
			});
			postModel.setReplyList(replyModels);
			postModel.setUser(getUserDetailsByUserId(String.valueOf(data.getUser().getUserId())));
			postModels.add(postModel);
			
		});
		return postModels;
		
	}
	@Transactional
	public String updatePost(PostModel postModel) {
		try {
			PostEntity postEntity = postRepository.findByPostId(postModel.getPostId());
			postEntity.setTitle(postModel.getTitle());
			postEntity.setMessage(postModel.getMessage());
			postEntity.setUpdatedBy(String.valueOf(postModel.getUser().getUserId()));
			postEntity.setUpdateDate(new Date());
			return "update";
		}catch (Exception e) {
			return "failed";
		}
		
	}
	
	@Transactional
	public String deletePostByPostId(String postId) {
		try {
			PostEntity postEntity = postRepository.findByPostId(Long.valueOf(postId));
			postEntity.setStatus("Inactive");
			postEntity.setUpdatedBy(String.valueOf(postEntity.getUser().getUserId()));
			postEntity.setUpdateDate(new Date());
			postRepository.save(postEntity);
			return "success";
		}catch (Exception e) {
			return "failed";
		}
	}
	//Post Details end..................
	
	
	
	//Reply Details start.......
	@Transactional
	public String saveReply(ReplyModel replyModel) {
		try {
			ReplyEntity replyEntity = new ReplyEntity();
			replyEntity.setReplyText(replyModel.getReplyText());
			replyEntity.setCreatedBy(String.valueOf(replyModel.getUserModel().getUserId()));
			replyEntity.setUpdatedBy(String.valueOf(replyModel.getUserModel().getUserId()));
			replyEntity.setCreateionDate(new Date());
			replyEntity.setUpdateDate(new Date());
			replyEntity.setStatus("Active");
			PostEntity postEntity = postRepository.findByPostId(replyModel.getPost().getPostId());
			replyEntity.setPost(postEntity);
			replyEntity.setUserId(String.valueOf(replyModel.getUserModel().getUserId()));
			replyRepository.save(replyEntity);
			return "success";
		}catch (Exception e) {
			return "failed";
		}
		
	}
	@Transactional
	public String saveLikeDislike(LikeDislikeModel likeDislikeModel) {
		try {
			PostEntity postEntity = postRepository.getByPostId(likeDislikeModel.getPost().getPostId());
			UserEntity userEntity = userRepository.findByUserId(likeDislikeModel.getUserModel().getUserId());
			LikeDislikeEntity likeDislikeEntity = new LikeDislikeEntity();
			likeDislikeEntity.setContent(likeDislikeModel.getContent());
			likeDislikeEntity.setCreatedBy(likeDislikeModel.getCreatedBy());
			likeDislikeEntity.setUpdatedBy(likeDislikeModel.getUpdatedBy());
			likeDislikeEntity.setCreateionDate(new Date());
			likeDislikeEntity.setUpdateDate(new Date());
			likeDislikeEntity.setPost(postEntity);
			likeDislikeEntity.setStatus("Active");
			likeDislikeEntity.setUserId(String.valueOf(userEntity.getUserId()));
			likeDislikeRepository.save(likeDislikeEntity);
			return "success";
		}catch (Exception e) {
			return "Failed";
			// TODO: handle exception
		}
		
		// TODO Auto-generated method stub
		
	}
	
	
	
	//Reply Details end....................

}
