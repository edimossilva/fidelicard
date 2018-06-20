package com.loop.fidelicard.model;

import java.io.Serializable;
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

@Entity
@Table(name = "finalClient")
public class FinalClient implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "uniqueIdentifier", nullable = false)
	private String uniqueIdentifier;

	@OneToMany(mappedBy = "finalClient", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Card> cards;

	public FinalClient() {
	}

	public FinalClient(String uniqueIdentifier) {
		setUniqueIdentifier(uniqueIdentifier);
	}

	public FinalClient(Long id, String uniqueIdentifier, List<Card> cards) {
		this.id = id;
		this.uniqueIdentifier = uniqueIdentifier;
		this.cards = cards;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUniqueIdentifier() {
		return uniqueIdentifier;
	}

	public void setUniqueIdentifier(String uniqueIdentifier) {
		this.uniqueIdentifier = uniqueIdentifier;
	}

	public List<Card> getCards() {
		return cards;
	}

	public void setCards(List<Card> cards) {
		this.cards = cards;
	}

	public void addCard(Card card) {
		getCards().add(card);
	}

}
