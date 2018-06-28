package com.loop.fidelicard.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.loop.fidelicard.dto.enterprise.EnterpriseDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "enterprise")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class Enterprise {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name = "name", nullable = false)
	private String name;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "enterprise_final_client", joinColumns = @JoinColumn(name = "client_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "enterprise_id", referencedColumnName = "id"))
	private List<FinalClient> finalClients;

	@OneToMany(mappedBy = "enterprise", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Offer> offers;

	public Enterprise(EnterpriseDTO enterpriseDTO) {
		setName(enterpriseDTO.getName());
	}

	public void addFinalClient(FinalClient finalCLient) {
		getFinalClients().add(finalCLient);
	}
}
