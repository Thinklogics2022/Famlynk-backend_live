package com.famlynk.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.famlynk.repository.IRegisterRepository;




@Component
public class RegUserDetailsService implements UserDetailsService {
	@Autowired
	private IRegisterRepository regRepo;

	@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return regRepo.findById(username).map(RegUserDetails::new)
        		.orElseThrow(() -> new UsernameNotFoundException("No user found"));
    }

}

