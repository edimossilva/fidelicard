package com.loop.fidelicard.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.loop.fidelicard.model.Offer;

public interface OfferRepository extends JpaRepository<Offer, Long> {

}
