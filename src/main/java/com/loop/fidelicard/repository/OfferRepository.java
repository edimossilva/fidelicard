package com.loop.fidelicard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.loop.fidelicard.model.Enterprise;
import com.loop.fidelicard.model.Offer;

public interface OfferRepository extends JpaRepository<Offer, Long> {

	List<Offer> findAllByEnterprise(Enterprise enterprise);

	Offer findByDescription(String offerDescription);

}
