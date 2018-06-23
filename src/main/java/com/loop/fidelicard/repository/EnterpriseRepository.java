package com.loop.fidelicard.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.loop.fidelicard.model.Enterprise;

public interface EnterpriseRepository extends JpaRepository<Enterprise, Long> {

}
