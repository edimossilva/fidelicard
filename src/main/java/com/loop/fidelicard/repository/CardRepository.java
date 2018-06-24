package com.loop.fidelicard.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.loop.fidelicard.model.Card;
import com.loop.fidelicard.model.FinalClient;
import com.loop.fidelicard.model.Offer;

public interface CardRepository extends JpaRepository<Card, Long> {

	public Optional<Card> findById(Long id);

	public Optional<Card> findByFinalClientAndOffer(FinalClient finalClient, Offer offer);
}
