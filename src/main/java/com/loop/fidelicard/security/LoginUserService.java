package com.loop.fidelicard.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginUserService {
	@Autowired
	LoginUserRepository loginUserRepository;

	public List<LoginUser> findAll() {
		return loginUserRepository.findAll();
	}
}
