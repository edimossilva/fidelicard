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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

import com.loop.fidelicard.dto.card.ResponseCardDTO;
import com.loop.fidelicard.dto.consumer.ConsumerCardDTO;

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
@Proxy(lazy = false)
public class Card implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	private FinalClient finalClient;

	@OneToMany(mappedBy = "card", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	private List<Stamp> stamps;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	private Enterprise enterprise;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	private Offer offer;

	@Column(name = "rewardReceivede")
	private boolean RewardReceived;

	@Column(name = "createdAt")
	private final LocalDateTime createdAt = LocalDateTime.now();

	public Card(FinalClient finalClient, Offer offer, Enterprise enterprise) {
		setFinalClient(finalClient);
		setOffer(offer);
		setEnterprise(enterprise);
	}

	public int getNormalizedQuantity() {
		if (getStamps() == null) {
			return 0;
		}
		int stampQuantity = getStamps().size();
		int offerQuantity = getOffer().getQuantity();

		if (isFull()) {
			if (isRewardReceived()) {
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
		} else if (isRewardReceived()) {
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

	public Enterprise getEnterprise() {
		return getOffer().getEnterprise();
	}

	public List<ResponseCardDTO> toResponseCardDTOList() {
		List<ResponseCardDTO> dtoList = new ArrayList<>();
		dtoList.add(this.toResponseCardDTO());
		return dtoList;
	}

	public ConsumerCardDTO toConsumerCardDTO() {
		return new ConsumerCardDTO(this);
	}

	public List<Stamp> getStamps() {
		if (stamps == null) {
			setStamps(new ArrayList<>());
		}
		return stamps;
	}

	public void addStamp(Stamp stamp) {
		getStamps().add(stamp);
		setRewardReceived(false);
	}

}
