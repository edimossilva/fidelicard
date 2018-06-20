package com.loop.fidelicard.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "card")
public class Card implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@OneToMany(mappedBy = "card", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Stamp> stamps;

	public Card() {

	}

	public Card(Long id, List<Stamp> stamps) {
		super();
		this.id = id;
		this.stamps = stamps;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Stamp> getStamps() {
		return stamps;
	}

	public void setStamps(List<Stamp> stamps) {
		this.stamps = stamps;
	}
}
