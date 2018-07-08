package com.loop.fidelicard.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.loop.fidelicard.dto.card.ResponseCardDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "card")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Card implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	private FinalClient finalClient;

	@OneToMany(orphanRemoval = true, mappedBy = "card", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Stamp> stamps;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	private Offer offer;

	public Card(FinalClient finalClient, Offer offer) {
		setFinalClient(finalClient);
		setOffer(offer);
	}

	public int getNormalizedQuantity() {
		if (getStamps() == null) {
			return 0;
		}
		int stampQuantity = getStamps().size();
		int offerQuantity = getOffer().getQuantity();

		if (isFull()) {
			if (stampQuantity == 0) {
				return 0;
			} else {
				return offerQuantity;
			}
		} else {
			return stampQuantity % offerQuantity;
		}
	}

	public boolean isFull() {

		int stampQuantity = getStamps().size();
		int offerQuantity = getOffer().getQuantity();
		if (stampQuantity == 0) {
			return false;
		}
		return stampQuantity % offerQuantity == 0;

	}

	public boolean isAlmostFull() {

		int stampQuantity = getStamps().size() + 1;
		int offerQuantity = getOffer().getQuantity();
		if (stampQuantity == 0) {
			return false;
		}
		return stampQuantity % offerQuantity == 0;

	}

	public ResponseCardDTO toResponseCardDTO() {
		return new ResponseCardDTO(this);
	}

	public int getStampQuantity() {
		return getStamps().size();
	}
}
