package com.example.PersonalOpinionVersion1.resources;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.PersonalOpinionVersion1.helper.Message;
import com.example.PersonalOpinionVersion1.models.ContactModel;
import com.example.PersonalOpinionVersion1.models.LikeDislikeModel;
import com.example.PersonalOpinionVersion1.models.PostModel;
import com.example.PersonalOpinionVersion1.models.ReplyModel;
import com.example.PersonalOpinionVersion1.models.UserModel;
import com.example.PersonalOpinionVersion1.models.UserModelV1;
import com.example.PersonalOpinionVersion1.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class HomeController {
	@Value("${upload.dir}")
	private String uploadDir;
	@Autowired
	private UserService userService;

	public String getCurrentUserRole() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userRole = "guest"; // Default role

		if (authentication != null) {
			Object principal = authentication.getPrincipal();

			if (principal instanceof UserDetails) {
				UserDetails userDetails = (UserDetails) principal;
				userRole = getUserRole(userDetails);
			}
		}
		return userRole;
	}

	public String getUserRole(UserDetails userDetails) {
		Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
		return authorities.stream().findFirst().map(GrantedAuthority::getAuthority).orElse("guest"); // Default if no
																										// roles are
																										// found
	}

	public String getLoggedInUsername() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.isAuthenticated()) {
			Object principal = authentication.getPrincipal();
			if (principal instanceof UserDetails) {
				return ((UserDetails) principal).getUsername();
			}
		}
		return null; // Return null if user is not authenticated or UserDetails is not available
	}

	@GetMapping("/")
	public String getIndexPage(Model model) {
		model.addAttribute("contact", new ContactModel());
		List<UserModel> userModels = userService.getAllActiveUsers();
		System.out.println("userModels===>"+userModels.size());
		model.addAttribute("users", userModels);
		return "Landing";
	}

	@GetMapping("/getLandingPage")
	public String getLandingPage() {
		return "Landing";
	}

	@GetMapping("/getIndexPage")
	public String getHomePage(Model model) {
		model.addAttribute("contact", new ContactModel());
		List<UserModel> userModels = userService.getAllActiveUsers();
		System.out.println("userModels===>"+userModels.size());
		model.addAttribute("users", userModels);
		String userRole = getCurrentUserRole();
		model.addAttribute("userRole", userRole);
		return "index";
	}

	@GetMapping("/getAboutPage")
	public String getAboutPage(Model model) {
		String userRole = getCurrentUserRole();
		model.addAttribute("userRole", userRole);
		return "about";
	}

	// Contact start.........................
	@GetMapping("/getContactPage")
	public String getContactPage(Model model) {
		String userRole = getCurrentUserRole();
		model.addAttribute("userRole", userRole);
		model.addAttribute("contact", new ContactModel());
		return "contact";
	}
	
	@GetMapping("/getContactList")
	public String getContactList(Model model) {
		String userRole = getCurrentUserRole();
		model.addAttribute("userRole", userRole);
		List<ContactModel> contactModels = userService.getAllActiveContactDetails();
		model.addAttribute("contacts", contactModels);
		return "contactList";
	}

	@PostMapping("/do_contact")
	public String do_contact(@Valid @ModelAttribute("contact") ContactModel contact, BindingResult result, Model model,
			HttpSession session) {
		String userRole = getCurrentUserRole();
		model.addAttribute("userRole", userRole);
		try {
			if (result.hasErrors()) {
				session.setAttribute("message",
						new Message(result.getFieldError().getDefaultMessage(), "alert-danger"));
				return "contact";
			} else {
				String msg = userService.saveContactDetails(contact);
				if (msg.equalsIgnoreCase("success")) {
					session.setAttribute("message", new Message("Contact details has sent!", "alert-success"));
				} else {
					session.setAttribute("message", new Message("Something went wrong!", "alert-danger"));
				}

				System.out.println("contact====>" + contact);

				return "contact";
			}
		} catch (Exception e) {
			session.setAttribute("message", new Message("Something went wrong!", "alert-danger"));
			return "contact";
		}
	}
	// Contact End.............

	// Registration start..................
	@GetMapping("/signUp")
	public String signUp(Model model) {
		String userRole = getCurrentUserRole();
		model.addAttribute("userRole", userRole);
		model.addAttribute("user", new UserModel());
		return "register";
	}

	/*
	 * @PostMapping("/do_register") public String
	 * do_register(@Valid @ModelAttribute("user") UserModel user, BindingResult
	 * result, HttpSession session, Model model) { System.out.println("user====>" +
	 * user); if (result.hasErrors()) { System.out.println("result====>" +
	 * result.toString()); if ((!(user.getName().isBlank() ||
	 * user.getName().isEmpty())) && (!(user.getEmail().isBlank() ||
	 * user.getEmail().isEmpty()))) { session.setAttribute("message", new
	 * Message(result.getFieldError().getDefaultMessage(), "alert-danger")); }
	 * model.addAttribute("user", user); return "register"; } else {
	 * user.setRole("USER_ROLE"); String output = userService.saveUserDetails(user);
	 * if (output.equalsIgnoreCase("success")) { model.addAttribute("user", new
	 * UserModel()); session.setAttribute("message", new
	 * Message("You have successfully registered.", "alert-success")); return
	 * "redirect:/login"; } else if (output.equalsIgnoreCase("Exist")) {
	 * model.addAttribute("user", user); session.setAttribute("message", new
	 * Message("Already Exist. Please Login.", "alert-danger")); return "register";
	 * } else { model.addAttribute("user", user); session.setAttribute("message",
	 * new Message("Something went wrong.", "alert-danger")); return "register"; } }
	 * 
	 * }
	 */

	@PostMapping("/do_register")
	public String do_register(@Valid @ModelAttribute("user") UserModel user, BindingResult result, HttpSession session,
			Model model, @RequestParam("profileFile") MultipartFile file) {
		System.out.println("user====>" + user);
		String output = "failed";
		if (result.hasErrors()) {
			System.out.println("result====>" + result.toString());
			if ((!(user.getName().isBlank() || user.getName().isEmpty()))
					&& (!(user.getEmail().isBlank() || user.getEmail().isEmpty()))) {
				session.setAttribute("message",
						new Message(result.getFieldError().getDefaultMessage(), "alert-danger"));
			}
			model.addAttribute("user", user);
			return "register";
		} else {
			user.setRole("USER_ROLE");

			// Save the file to the local directory
			if (!file.isEmpty()) {
				try {
					// Save the file locally
					byte[] bytes = file.getBytes();
					Path path = Paths.get(uploadDir + File.separator + file.getOriginalFilename());
					Files.write(path, bytes);

					// Save the path to the database
					String filePath = "uploads/" + file.getOriginalFilename();
					user.setProfile(filePath);
				} catch (Exception e) {
					e.printStackTrace();
					session.setAttribute("message", new Message("File upload failed.", "alert-danger"));
					model.addAttribute("user", user);
					return "register";
				}
			}
			output = userService.saveUserDetails(user);

			if (output.equalsIgnoreCase("success")) {
				model.addAttribute("user", new UserModel());
				session.setAttribute("message", new Message("You have successfully registered.", "alert-success"));
				return "register";
			} else if (output.equalsIgnoreCase("Exist")) {
				model.addAttribute("user", user);
				session.setAttribute("message", new Message("Already Exist. Please Login.", "alert-danger"));
				return "register";
			} else {
				model.addAttribute("user", user);
				session.setAttribute("message", new Message("Something went wrong.", "alert-danger"));
				return "register";
			}
		}
	}

	// Registration end.....

	// Login start...........
	@GetMapping("/login")
	public String getLoginPage(Model model) {
		try {
			String userRole = getCurrentUserRole();
			System.out.println("userRole===>" + userRole);
			model.addAttribute("userRole", userRole);
			if (userRole.equalsIgnoreCase("guest")) {
				return "login";
			}
			return "redirect:/getIndexPage";
		} catch (Exception e) {
			return "redirect:/login";
		}

	}
	// Login end..............

	// Post start.................

	@GetMapping("/getAllPosts")
	public String getAllPosts(Model model) {
		String userRole = getCurrentUserRole();
		model.addAttribute("userRole", userRole);
		List<PostModel> postModels = userService.getAllActivePosts();
		System.out.println("postModels====>" + postModels);
		model.addAttribute("posts", postModels);
		return "posts";
	}

	@GetMapping("/getAllMyPost")
	public String getAllMyPosts(Model model) {
		String loggedUserName = getLoggedInUsername();
		String userRole = getCurrentUserRole();
		System.out.println("userRole===>" + userRole);
		System.out.println("loggedUserName=====>" + loggedUserName);
		UserModel userModel = userService.getUserByUserEmail(loggedUserName);
		List<PostModel> postModels = userService.getAllActivePostsByUserId(String.valueOf(userModel.getUserId()));
		model.addAttribute("userRole", userRole);
		model.addAttribute("posts", postModels);
		return "myPosts";
	}

	@GetMapping("/getWritePost")
	public String getWritePost(@ModelAttribute("post") PostModel postModel, Model model) {
		String userRole = getCurrentUserRole();
		model.addAttribute("userRole", userRole);
		if(postModel==null) {
			model.addAttribute("post", new PostModel());
		}else {
			model.addAttribute("post", postModel);
		}
		
		return "writePost";
	}

	@PostMapping("/UpdateMyPost")
	public String getUpdatePostPage(@RequestParam("postId") Long postId, Model model) {
		System.out.println("postId======>" + postId);
		String userRole = getCurrentUserRole();
		model.addAttribute("userRole", userRole);
		PostModel post = userService.getPostDetailsByPostId(postId);
		System.out.println("post.getTitle()-===>" + post.getTitle());
		System.out.println("post.getMessage()===>" + post.getMessage());
		model.addAttribute("post", post);
		return "writePost"; // Assuming your "writePost.html" page is mapped to this template name
	}

	@PostMapping("/do_writePost")
	public String do_writePost(@Valid @ModelAttribute("post") PostModel post, BindingResult result, Model model,
			HttpSession session) {
		String output = "failed";
		if (result.hasErrors()) {
			session.setAttribute("message", new Message(result.getFieldError().getDefaultMessage(), "alert-danger"));
			model.addAttribute("post", post);
			return "redirect:/getWritePost";
		} else {
			System.out.println("userService.getUserByUserEmail(getLoggedInUsername())=======>"
					+ userService.getUserByUserEmail(getLoggedInUsername()));
			UserModel userModel = userService.getUserByUserEmail(getLoggedInUsername());
			post.setUser(userModel);
			post.setCreatedBy(String.valueOf(userModel.getUserId()));
			post.setUpdatedBy(String.valueOf(userModel.getUserId()));
			System.out.println("post.getPostId()====>" + post.getPostId());
			if (post.getPostId() == null || post.getPostId() == 0) {
				System.out.println("In save block...........");
				output = userService.savePostDetails(post);
			} else {
				System.out.println("In update block..");
				output = userService.updatePost(post);
			}
			System.out.println("Output====>" + output);
			if (output.equalsIgnoreCase("success")) {
				model.addAttribute("post", new PostModel());
				session.setAttribute("message",
						new Message("Your post has saved successfully! Please open POSTS section.", "alert-success"));
				return "redirect:/getWritePost";
				/* return "redirect:/getAllPosts"; */
			} else if (output.equalsIgnoreCase("update")) {
				model.addAttribute("post", new PostModel());
				session.setAttribute("message", new Message(
						"Your post has updated successfully! Please open MY POSTS section.", "alert-success"));
				return "redirect:/getWritePost";
			} else {
				model.addAttribute("post", post);
				session.setAttribute("message", new Message("Something went wrong.", "alert-danger"));
				return "redirect:/getWritePost";
			}
		}
	}

	@PostMapping("/deleteMyPost")
	public String deleteMyPost(@RequestParam("postId") Long postId) {
		String result = userService.deletePostByPostId(String.valueOf(postId));
		if (result.equalsIgnoreCase("success")) {
			return "redirect:/getAllMyPost";
		} else {
			return "redirect:/getAllMyPost";
		}
	}

	// Post End.............

	// Account section start..................
	@GetMapping("/getMyAccount")
	public String getMyAccount(Model model) {
		String loggedUserName = getLoggedInUsername();
		String userRole = getCurrentUserRole();
		System.out.println("userRole===>" + userRole);
		System.out.println("loggedUserName=====>" + loggedUserName);
		UserModel userModel = userService.getUserByUserEmail(loggedUserName);
		UserModel user = userService.getUserDetailsByUserId(String.valueOf(userModel.getUserId()));
		model.addAttribute("userRole", userRole);
		model.addAttribute("user", user);
		return "myAccount";
	}

	@PostMapping("/update_myAccount")
	public String update_myAccount(@Valid @ModelAttribute("user") UserModelV1 user, BindingResult result,
			HttpSession session, Model model, @RequestParam("profileFile") MultipartFile file) {
		String output = "failed";
		if (result.hasErrors()) {
			System.out.println("result====>" + result.toString());
			if ((!(user.getName().isBlank() || user.getName().isEmpty()))
					&& (!(user.getEmail().isBlank() || user.getEmail().isEmpty()))) {
				session.setAttribute("message",
						new Message(result.getFieldError().getDefaultMessage(), "alert-danger"));
			}
			model.addAttribute("user", user);
			return "myAccount";
		} else {

			// Save the file to the local directory
			if (!file.isEmpty()) {
				try {
					// Save the file locally
					byte[] bytes = file.getBytes();
					Path path = Paths.get(uploadDir + File.separator + file.getOriginalFilename());
					Files.write(path, bytes);

					// Save the path to the database
					String filePath = "uploads/" + file.getOriginalFilename();
					user.setProfile(filePath);
				} catch (Exception e) {
					e.printStackTrace();
					session.setAttribute("message", new Message("File upload failed.", "alert-danger"));
					model.addAttribute("user", user);
					return "myAccount";
				}
			}
			output = userService.updateUserDetailsByUserId(user);
			if (output.equalsIgnoreCase("update")) {
				model.addAttribute("user", new UserModel());
				return "redirect:/getMyAccount";
				/* return "myAccount"; */
			} else {
				model.addAttribute("user", user);
				session.setAttribute("message", new Message("Something went wrong.", "alert-danger"));
				return "myAccount";
			}
		}
	}

	@PostMapping("/deleteAccount")
	public String deleteMyAccount(@RequestParam("userId") Long userId) {
		System.out.println("userId==>"+userId);
		String result = userService.deleteUserByUserId(String.valueOf(userId));
		System.out.println("result====>"+result);
		if (result.equalsIgnoreCase("success")) {
			
			return "Landing";
		} else {
			return "myAccount";
		}
	}

	// Account section end..............

	// Reply section.......................
	@PostMapping("/do_Reply")
	public String do_Reply(@ModelAttribute("reply") ReplyModel replyModel, @RequestParam("postId") Long postId) {
		String currentUser = getLoggedInUsername();
		UserModel userModel = userService.getUserByUserEmail(currentUser);
		PostModel postModel = userService.getPostDetailsByPostId(postId);
		replyModel.setUserModel(userModel);
		replyModel.setPost(postModel);
		userService.saveReply(replyModel);
		return "redirect:/getAllPosts";

	}

	@PostMapping("/saveLikeDislike")
	public String saveLikeDislike(@RequestParam Long postId, @RequestParam String action) {
		String currentUser = getLoggedInUsername();
		UserModel userModel = userService.getUserByUserEmail(currentUser);
		PostModel postModel = userService.getPostDetailsByPostId(postId);
		LikeDislikeModel likeDislikeModel = new LikeDislikeModel();
		likeDislikeModel.setContent(action);
		likeDislikeModel.setCreatedBy(currentUser);
		likeDislikeModel.setUpdatedBy(currentUser);
		likeDislikeModel.setUserModel(userModel);
		likeDislikeModel.setPost(postModel);
		userService.saveLikeDislike(likeDislikeModel);
		return "redirect:/getAllPosts";
	}

	// Reply section end................

	// Active User start...............
	@GetMapping("/getActiveUsers")
	public String getActiveUser(Model model) {
		String userRole = getCurrentUserRole();
		System.out.println("userRole====>"+userRole);
		model.addAttribute("userRole", userRole);
		List<UserModel> users = userService.getAllActiveUsers();
		model.addAttribute("users", users);
		return "activeUsers";
	}

	@GetMapping("/getUserAccount")
	public String getUserAccount(@RequestParam("userId") String userId, Model model) {
		UserModel userV1 = userService.getUserDetailsByUserId(userId);
		model.addAttribute("user", userV1);
		return "userAccount";
	}

	@PostMapping("/update_userAccount")
	public String update_userAccount(@Valid @ModelAttribute("user") UserModelV1 user, BindingResult result,
			HttpSession session, Model model, @RequestParam("profileFile") MultipartFile file) {
		String output = "failed";
		if (result.hasErrors()) {
			System.out.println("result====>" + result.toString());
			if ((!(user.getName().isBlank() || user.getName().isEmpty()))
					&& (!(user.getEmail().isBlank() || user.getEmail().isEmpty()))) {
				session.setAttribute("message",
						new Message(result.getFieldError().getDefaultMessage(), "alert-danger"));
			}
			model.addAttribute("user", user);
			return "userAccount";
		} else {

// Save the file to the local directory
			if (!file.isEmpty()) {
				try {
					// Save the file locally
					byte[] bytes = file.getBytes();
					Path path = Paths.get(uploadDir + File.separator + file.getOriginalFilename());
					Files.write(path, bytes);

					// Save the path to the database
					String filePath = "uploads/" + file.getOriginalFilename();
					user.setProfile(filePath);
				} catch (Exception e) {
					e.printStackTrace();
					session.setAttribute("message", new Message("File upload failed.", "alert-danger"));
					model.addAttribute("user", user);
					return "userAccount";
				}
			}
			output = userService.updateUserDetailsByUserId(user);
			System.out.println("output====>"+output);
			if (output.equalsIgnoreCase("update")) {
				model.addAttribute("user", user);
				session.setAttribute("message", new Message("Updated Successfully!", "alert-success"));
				return "userAccount";
			} else {
				model.addAttribute("user", user);
				session.setAttribute("message", new Message("Something went wrong.", "alert-danger"));
				return "userAccount";
			}
		}
	}
	
	
	
	
	@DeleteMapping("/deleteUserAccount")
	public String deleteUserAccount(@RequestParam("userId") String userId, Model model) {
		System.out.println("userId==>"+userId);
		String result = userService.deleteUserByUserId(String.valueOf(userId));
		System.out.println("result====>"+result);
		if (result.equalsIgnoreCase("success")) {
			return "redirect:/getActiveUser";
		} else {
			return "redirect:/getActiveUser";
		}
	}
	// Active User end................

}
