package com.loop.fidelicard.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.loop.fidelicard.security.JwtUserFactory;
import com.loop.fidelicard.security.model.LoginUser;
import com.loop.fidelicard.security.model.LoginUserService;

@Service
public class MyUserDetailService implements UserDetailsService {
	@Autowired
	private LoginUserService loginUserService;

	// public Properties getAll() {
	// Properties users = new Properties();
	// for (LoginUser loginUser : loginUserService.findAll()) {
	// String credentials = loginUser.getPassword() + ",ROLE_" +
	// loginUser.getUserRole() + ",enabled";
	// users.put(loginUser.getEmail(), credentials);
	// }
	// return users;
	// }
	//
	// public void giveCredentials(LoginUser loginUser, InMemoryUserDetailsManager
	// inMemoryUserDetailsManager) {
	// String email = loginUser.getEmail();
	// String password = loginUser.getPassword();
	// List<GrantedAuthority> authorities = getAuthorities(loginUser.getUserRole());
	// User user = new User(email, password, authorities);
	// System.out.println("give credentials"+user);
	// inMemoryUserDetailsManager.createUser(user);
	// }
	//
	// private static List<GrantedAuthority> getAuthorities(UserRole userRole) {
	// List<GrantedAuthority> authorities = new ArrayList<>();
	// authorities.add(new SimpleGrantedAuthority("ROLE_" + userRole));
	// return authorities;
	// }

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		LoginUser funcionario = loginUserService.findByEmail(username);
		if (funcionario != null) {
			return JwtUserFactory.create(funcionario);
		}

		throw new UsernameNotFoundException("Email não encontrado.");
	}
}
