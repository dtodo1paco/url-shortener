package org.dtodo1paco.samples.urlshortener.config;

import java.util.Arrays;

import org.dtodo1paco.samples.urlshortener.model.ServiceUser;
import org.dtodo1paco.samples.urlshortener.repository.ServiceUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
@Service
public class MyAppUserDetailsService implements UserDetailsService {
	@Autowired
	private ServiceUserRepository repository;
	@Override
	public UserDetails loadUserByUsername(String userName)
			throws UsernameNotFoundException {
		ServiceUser activeUserInfo = repository.findByUserName(userName);
		// TODO: update statistics
		GrantedAuthority authority = new SimpleGrantedAuthority(activeUserInfo.getRole());
		UserDetails userDetails = (UserDetails) new User(activeUserInfo.getUserName(),
				activeUserInfo.getPassword(), Arrays.asList(authority));
		return userDetails;
	}
}

