package com.loop.fidelicard.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.loop.fidelicard.model.FinalClient;

public interface FinalClientRepository extends JpaRepository<FinalClient, Long> {

	FinalClient findByUniqueIdentifier(String uniqueIdentifier);

}
