package com.loop.fidelicard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.loop.fidelicard.model.Card;
import com.loop.fidelicard.model.Enterprise;
import com.loop.fidelicard.model.FinalClient;
import com.loop.fidelicard.model.Offer;

public interface CardRepository extends JpaRepository<Card, Long> {

	public Card findById(Long id);

	public Card findByFinalClientAndOffer(FinalClient finalClient, Offer offer);

	public List<Card> findAllByEnterprise(Enterprise enterprise);
}
