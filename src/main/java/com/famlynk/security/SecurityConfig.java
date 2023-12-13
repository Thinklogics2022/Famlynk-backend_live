package com.famlynk.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.famlynk.jwt.JWTAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private JWTAuthenticationFilter authenticationFilter;
	@Autowired
	private RegUserDetailsService userDetailsService;

	private static final String[] ADMIN = {

			"/login/retrivelogin",

			"/register/users",

			"/complaint/delete/{id}", 
			"/complaint/retrieve", 
			"/complaint/newsFeedComplaints",
			"/complaint/commentComplaints", 
			"/complaint/count"
	};
	
	private static final String[] USER = {

			"/register/retrievemembers/{userId}",
			"/register/retrieveregisterbyid/{userId}",
			"/register/updateregisterbyid/{userId}",
			"/register/retrievemembersbyuserid/{userId}",
			"/register/retrieveregister",
			"/register/retrievemembers/{userId}",

			"/newsfeed/createnewsfeed", 
			"/newsfeed/updatenewsfeed/{newsFeedId}",
			"/newsfeed/profileandnameupdate/{userId}",
			"/newsfeed/retrievenewsfeed/pagination/{offset}/{pageSize}/{userId}", 
			"/newsfeed/newsfeedTotal",
			"/newsfeed/newsfeedtotalwithfamily/{userId}", 
			"/newsfeed/newsfeedtotalwithoutfamily/{userId}",
			"/newsfeed/retrievefamilynewsfeed/{userId}", 
			"/newsfeed/likes/{userId}/{newsFeedId}",
			"/newsfeed/retrieveusernewsfeed/{userId}/{uniqueUserID}", 
			"/newsfeed/retrieveregistereduser/{userId}",
			"/newsfeed/retrievenewsfeed/{shared}", 
			"/newsfeed/sharedperson/{uniqueUserID}",
			"/newsfeed/retrievefamilynewsfeed/{userId}/{shared}", 
			"/newsfeed/deletePost/{userId}",
			"/newsfeed/userLikes/{userId}",

			"/comment/updatecommentbyid/{id}", 
			"/comment/createfamilymembers/{id}", 
			"/comment/createcomment",
			"/comment/retrievenewsfeedcomments/{userId}/{newsFeedId}",

			"/familymembers/updatefamilymembers/{famid}", 
			"/familymembers/getmutualconnection/{userId}/{uniqueUserID}",
			"/familymembers/getFamilyTree/{userId}/{uniqueUserID}/{level}",
			"/familymembers/createfamilymembers/{email}", 
			"/familymembers/addfamily/{userId}/{uniqueUserID}",
			"/familymembers/declinefamilymember/{userId}/{uniqueUserID}",
			"/familymembers/getsecondlevelrelation/{firstLevelRelation}",
			"/familymembers/getthirdlevelrelation/{secondLevelRelation}",
			"/familymembers/getmutualconnectionnames/{userId}/{uniqueUserID}",
			"/familymembers/getmutualconnection/{userId}/{uniqueUserID}",

			"/notify/retrievenotifybyuniqueuserid/{toUniqueUserID}",
			"/notify/acceptFamilyMember/{userID}/{uniqueUserID}/{relation}/{toUniqueUserID}",

			"/complaint/add", 
			"/complaint/update/{id}",

	};

	private static final String[] UN_SECURED_URLs = { 
			
			"/authenticate/login", 
			"/authenticate/loginport",
			"/authenticate/refreshToken", 
			"/authenticate/logout",

			"/register/createregister", 
			"/register/updatepasswordbyemail/{email}", 
			"/register/verifyotp/{otp}",
			"/register/retrieveregisterbyemailforpassword/{email}", 
			"/register/resend/{userId}",

			"/ws",
	};

	private static final String[] ADMIN_AND_USER_URL = { 
			
			"/register/retrieveRegisterUsers",
			
			"/familymembers/retrieveFamilyMembers/{userId}",
			"/familymembers/deletefamilymember/{userId}/{uniqueUserID}",
			"/familymembers/deletefamilymember/{userId}/{uniqueUserID}",
			
			"/register/getmemberprofile/{uniqueUserID}", 
			
			"/newsfeed/deletenewsfeed/{userId}/{newsFeedId}",
			"/newsfeed/deletenewsfeed/{userId}/{newsFeedId}",
			"/newsfeed/userNewsFeed/{newsFeedId}",
			
			"/comment/deletecomment/{id}",
			"/comment/retrievecommentbyid/{newsFeedId}",
	};

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		var authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.csrf(AbstractHttpConfigurer::disable)

				.authorizeHttpRequests(auth->{
					auth
							.requestMatchers(UN_SECURED_URLs).permitAll()
							.requestMatchers(ADMIN).hasAuthority("ADMIN")
							.requestMatchers(USER).hasAuthority("USER")
							.requestMatchers(ADMIN_AND_USER_URL).hasAnyAuthority("ADMIN", "USER")
							.anyRequest().authenticated();
				})

				.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))


				.authenticationProvider(authenticationProvider())
				.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
				.build();
	}


	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}

}
