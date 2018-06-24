package com.loop.fidelicard.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.loop.fidelicard.model.Card;
import com.loop.fidelicard.model.FinalClient;
import com.loop.fidelicard.model.Offer;
import com.loop.fidelicard.model.Stamp;

public interface StampRepository extends JpaRepository<Stamp, Long> {


}
