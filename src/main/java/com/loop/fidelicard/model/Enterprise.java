package com.loop.fidelicard.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.loop.fidelicard.dto.EnterpriseDTO;

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

	public Enterprise(EnterpriseDTO enterpriseDTO) {
		setName(enterpriseDTO.getName());
	}
}
