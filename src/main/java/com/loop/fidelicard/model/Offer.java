package com.loop.fidelicard.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.loop.fidelicard.dto.OfferDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "offer")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Offer implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "name", nullable = false, unique = true)
	private String name;

	@Column(name = "description", nullable = false, unique = true)
	private String description;

	@Column(name = "quantity", nullable = false, unique = true)
	private Integer quantity;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	private Enterprise enterprise;

	public Offer(OfferDTO offerDTO) {
		setName(offerDTO.getName());
		setDescription(offerDTO.getDescription());
		setQuantity(offerDTO.getQuantity());
	}

}
