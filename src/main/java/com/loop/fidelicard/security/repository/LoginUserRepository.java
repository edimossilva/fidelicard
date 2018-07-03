package com.loop.fidelicard.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.loop.fidelicard.security.model.LoginUser;

public interface LoginUserRepository extends JpaRepository<LoginUser, Long> {

	LoginUser findByEmail(String email);

}
