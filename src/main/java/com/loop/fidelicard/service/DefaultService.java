package com.loop.fidelicard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import com.loop.fidelicard.dto.DefaultDTO;
import com.loop.fidelicard.factory.ModelFactory;
import com.loop.fidelicard.model.DefaultModel;

public abstract class DefaultService<T extends DefaultModel> {

	@Autowired
	JpaRepository<T, Long> jpaRepository;

	public Iterable<T> findAll() {
		return jpaRepository.findAll();
	}

	@SuppressWarnings("unchecked")
	public T save(DefaultDTO defaultDTO) {
		T model = (T) ModelFactory.get(defaultDTO);
		return jpaRepository.save(model);
	}
}
