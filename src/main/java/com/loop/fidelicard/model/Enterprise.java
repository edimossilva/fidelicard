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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

import com.loop.fidelicard.dto.enterprise.EnterpriseDTO;
import com.loop.fidelicard.dto.enterprise.ResponseEnterpriseWithLoginUserDTO;
import com.loop.fidelicard.security.model.LoginUser;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "enterprises")
@Getter
@Setter
@Builder
@AllArgsConstructor
// @NoArgsConstructor
@Proxy(lazy = false)
@EqualsAndHashCode(of = "id")
public class Enterprise {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name = "name", nullable = false)
	private String name;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "enterprise_final_client", joinColumns = @JoinColumn(name = "client_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "enterprise_id", referencedColumnName = "id"))
	private List<FinalClient> finalClients;

	@OneToMany(mappedBy = "enterprise", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Offer> offers;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE, mappedBy = "enterprise")
	private LoginUser ownerLoginUser;

	public Enterprise() {
	}

	public Enterprise(EnterpriseDTO enterpriseDTO) {
		setName(enterpriseDTO.getName());
	}

	public void addFinalClient(FinalClient finalCLient) {
		getFinalClients().add(finalCLient);
	}

	public ResponseEnterpriseWithLoginUserDTO toResponseEnterpriseDTO() {
		return new ResponseEnterpriseWithLoginUserDTO(this);
	}

	public String getOwnerLoginUserEmail() {
		return getOwnerLoginUser().getEmail();
	}

}
