package com.loop.fidelicard.security.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.loop.fidelicard.security.model.LoginUser;
import com.loop.fidelicard.security.model.UserRole;

@Service
public class ComercialUserDetailsService {
	@Autowired
	private LoginUserService localUserService;

	//
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		LoginUser user = localUserService.findByEmail(email);
		if (user == null) {
			throw new UsernameNotFoundException("No user found with username: " + email);
		}
		boolean enabled = true;
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;

		return new User(user.getEmail(), user.getPassword().toLowerCase(), enabled, accountNonExpired,
				credentialsNonExpired, accountNonLocked, getAuthorities(user.getUserRole()));
	}

	private static List<GrantedAuthority> getAuthorities(UserRole userRole) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(userRole + ""));
		return authorities;
	}

}
