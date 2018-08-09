package com.loop.fidelicard.model;

import java.io.Serializable;
import java.time.LocalDateTime;
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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.loop.fidelicard.dto.consumer.ConsumerOfferDTO;
import com.loop.fidelicard.dto.offer.OfferDTO;
import com.loop.fidelicard.dto.offer.ResponseOfferDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "offer")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString
@Proxy(lazy = false)
public class Offer implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "description", nullable = false)
	private String description;

	@Column(name = "quantity", nullable = false)
	private Integer quantity;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	private Enterprise enterprise;

	@OneToMany(mappedBy = "offer", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Card> cards;

	@Column(name = "createdAt")
	private final LocalDateTime createdAt = LocalDateTime.now();

	public Offer(OfferDTO offerDTO) {
		setName(offerDTO.getOfferName());
		setDescription(offerDTO.getOfferDescription());
		setQuantity(offerDTO.getOfferQuantity());
	}

	public ResponseOfferDTO toResponseOfferDTO() {
		return new ResponseOfferDTO(this);
	}

	public ConsumerOfferDTO toConsumerOfferDTO() {
		return new ConsumerOfferDTO(this);
	}

}
