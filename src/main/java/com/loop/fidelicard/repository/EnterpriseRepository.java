package com.loop.fidelicard.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.loop.fidelicard.model.Enterprise;
import com.loop.fidelicard.security.model.LoginUser;

public interface EnterpriseRepository extends JpaRepository<Enterprise, Long> {

	// @Query("select e from Enterprise e where e.ownerLoginUser = ?")
	// from Cat where name='Fritz'

	Enterprise findByOwnerLoginUser(LoginUser ownerLoginUser);

	Enterprise findByName(String name);

	Enterprise findById(Long enterpriseId);

}
