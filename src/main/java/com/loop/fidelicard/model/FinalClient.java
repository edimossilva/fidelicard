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

public class FinalClient implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "uniqueIdentifier", nullable = false, unique = true)
	private String uniqueIdentifier;

	@OneToMany(mappedBy = "finalClient", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Card> cards;

	public FinalClient(String uniqueIdentifier) {
		setUniqueIdentifier(uniqueIdentifier);
	}

}
