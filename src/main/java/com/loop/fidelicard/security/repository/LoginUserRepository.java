package com.loop.fidelicard.security.repository;

import org.springframework.data.repository.CrudRepository;

import com.loop.fidelicard.security.model.LoginUser;

public interface LoginUserRepository extends CrudRepository<LoginUser, Long> {

	LoginUser findByEmail(String email);

	void deleteById(Long id);

	LoginUser findById(Long loginUserId);

}
