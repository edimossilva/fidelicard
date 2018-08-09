package com.loop.fidelicard.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

import com.loop.fidelicard.dto.card.ResponseCardDTO;
import com.loop.fidelicard.dto.consumer.ConsumerCardDTO;
import com.loop.fidelicard.dto.consumer.ConsumerFinalClientDTO;
import com.loop.fidelicard.dto.finalclient.FinalClientAndEnterpriseIdDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "finalClient")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Proxy(lazy = false)
public class FinalClient implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "email", nullable = false, unique = false)
	private String email;

	@Column(name = "uniqueIdentifier", nullable = false, unique = true)
	private String uniqueIdentifier;

	@OneToMany(mappedBy = "finalClient", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Card> cards;

	@Column(name = "createdAt")
	private final LocalDateTime createdAt = LocalDateTime.now();


	public boolean isCardAlmostFull(Offer offer) {
		for (Card card : cards) {
			if (card.getOffer().equals(offer)) {
				if (card.isAlmostFull()) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean isCardFull(Offer offer) {
		for (Card card : cards) {
			if (card.getOffer().equals(offer)) {
				if (card.isFull()) {
					return true;
				}
			}
		}
		return false;
	}

	public Card getCardByEnterprise(Enterprise enterprise) {
		for (Card card : cards) {
			if (card.getOffer().getEnterprise().equals(enterprise)) {
				return card;
			}
		}
		return null;
	}

	public ConsumerFinalClientDTO toConsumerFinalClientDTO() {
		return new ConsumerFinalClientDTO(this);
	}

	public FinalClient(FinalClientAndEnterpriseIdDTO dto) {
		setEmail(dto.getFinalClientEmail());
		setUniqueIdentifier(dto.getFinalClientUI());
	}

	public Card findCardByEnterpriseId(Long enterpriseId) {
		if (cards != null) {
			for (Card card : cards) {
				if (card.getEnterprise().getId() == enterpriseId) {
					return card;
				}
			}
		}
		return null;
	}

	public List<ResponseCardDTO> getResponseCardsDTO() {
		List<ResponseCardDTO> dTOList = new ArrayList<>();
		if (getCards() != null) {
			getCards().forEach(c -> dTOList.add(c.toResponseCardDTO()));
		}
		return dTOList;
	}

	public List<ConsumerCardDTO> getConsumerCardsDTO() {
		List<ConsumerCardDTO> dTOList = new ArrayList<>();
		if (getCards() != null) {
			getCards().forEach(c -> dTOList.add(c.toConsumerCardDTO()));
		}

		return dTOList;
	}

}
