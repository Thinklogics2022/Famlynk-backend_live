package com.famlynk.jwt;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.famlynk.model.Register;
import com.famlynk.repository.IRegisterRepository;
import com.famlynk.service.FamilyMembersService;

@RestController
@CrossOrigin("*")
@RequestMapping("/authenticate")
public class JWTControllerLogin {
	@Autowired
	public FamilyMembersService familyMemberService;

	private final JWTService jwtService;
	private final AuthenticationManager authenticationManager;
	private final Refresh refreshObj;
	private final IRegisterRepository registerRepository;

	public JWTControllerLogin(JWTService jwtService, AuthenticationManager authenticationManager, Refresh refreshObj,
			IRegisterRepository registerRepository) {
		this.jwtService = jwtService;
		this.authenticationManager = authenticationManager;
		this.refreshObj = refreshObj;
		this.registerRepository = registerRepository;

	}

	@PostMapping("/login")
	public Map getTokenForAuthenticatedUsers(@RequestBody JWTAuthenticationRequest authRequest) {
		Map<String, String> response = new HashMap<>();
		Map<String, String> respo = new HashMap<>();
		String user = null;
		String usernames = null;
		String uniqueUserID = null;
		String email = null;
		String role = null;
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));
		if (authentication.isAuthenticated()) {

			System.out.println("posted here");
			System.out.println(authRequest);
			System.out.println(jwtService.generateToken(authRequest.getUserName()));
			String token = jwtService.generateToken(authRequest.getUserName());

//    authRequest.setToken(token);

			Register users = registerRepository.findByEmail(authRequest.getUserName());

			usernames = users.getName();
			user = users.getUserId();
			uniqueUserID = users.getUniqueUserID();
			email = users.getEmail();
            role = users.getRole();
			response.put("userId", user);
			response.put("token", token);
			response.put("name", usernames);
			response.put("uniqueUserID", uniqueUserID);
			response.put("email", email);
            response.put("role", role);
            
			respo.put("name", usernames);

			if (users.getEnabled() == true) {
				familyMemberService.getFamilyTree(user, uniqueUserID, "firstLevel");
				return response;
			} else {
				return respo;
			}

//    return response;
		} else {
			throw new UsernameNotFoundException("invalid user found");
		}
	}

	@PostMapping("/refreshToken")
	public String refreshToken(@RequestBody JWTAuthenticationRequest authRequest) {
		String refreshToken = refreshObj.generateRefreshToken(authRequest.getUserName());
		System.out.println("refresh");
		return refreshToken;
	}

	@DeleteMapping("/logout")
	public void logout() {
		familyMemberService.evictFamilyTreeCache();
	}
}
