package com.loop.fidelicard.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.loop.fidelicard.model.Enterprise;
import com.loop.fidelicard.security.model.LoginUser;

public interface EnterpriseRepository extends JpaRepository<Enterprise, Long> {

	Enterprise findByOwnerLoginUser(LoginUser loginUser);

}
