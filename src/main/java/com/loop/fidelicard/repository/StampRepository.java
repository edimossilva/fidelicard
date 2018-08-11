package com.loop.fidelicard.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.loop.fidelicard.model.Stamp;

public interface StampRepository extends JpaRepository<Stamp, Long> {

	Stamp findById(Long id);


}
