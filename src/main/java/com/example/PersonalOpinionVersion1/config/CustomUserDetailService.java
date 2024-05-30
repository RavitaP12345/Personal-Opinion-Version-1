package com.example.PersonalOpinionVersion1.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.example.PersonalOpinionVersion1.models.UserModel;
import com.example.PersonalOpinionVersion1.services.UserService;



public class CustomUserDetailService implements UserDetailsService{
	
	@Autowired
	UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserModel userModel = userService.getUserByUserEmail(username);
		if(userModel == null) {
			return null;
		}
		String [] roles = {userModel.getRole()};
		return User.builder()
				.username(userModel.getEmail())
				.password(userModel.getPassword())
				.roles(roles).build();

	}
	

}
