package com.example.PersonalOpinionVersion1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		http.authorizeHttpRequests(request -> request.requestMatchers("/", "/signUp", "/login", "/getContactPage", "/getLandingPage", "/getAllPosts", "/posts",
				"/getAllMyPost", "/UpdateMyPost", "/getMyAccount","update_myAccount", "/do_Reply","saveLikeDislike", "/getActiveUsers", "/getUserAccount", "/getLandingPage",
				"/getContactList", "/do_writePost", "do_contact", "/do_register", "/index", "/getIndexPage", "/css/**", "/js/**", 
				"/images/**", "/uploads/**", "/fonts/**", "/styles/**", "/scripts/**").permitAll()
				.anyRequest().authenticated())
		.formLogin(form->form.loginPage("/login")
				.loginProcessingUrl("/login")
				.defaultSuccessUrl("/login")
                .failureUrl("/login?error=true").permitAll())
		.logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll());
		return http.build();
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	UserDetailsService userDetailsService() {
		return new CustomUserDetailService();
	}
	@Bean
	AuthenticationManager authenticationManager(HttpSecurity http) throws Exception{
		AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
		authenticationManagerBuilder.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
		return authenticationManagerBuilder.build();
	}

}
