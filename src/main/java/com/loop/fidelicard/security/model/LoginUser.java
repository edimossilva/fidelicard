package com.loop.fidelicard.security.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.loop.fidelicard.model.Enterprise;
import com.loop.fidelicard.security.dto.ResponseLoginUserDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "login_user")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")

public class LoginUser implements Serializable {

	private static final long serialVersionUID = -4911355131744430193L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "email", nullable = false, unique = true)
	private String email;

	@Column(name = "password", nullable = false)
	private String password;

	@Enumerated(EnumType.STRING)
	@Column(name = "user_role", nullable = false)
	private UserRole userRole;

	@OneToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "login_user_id", nullable = true)
	private Enterprise enterprise;

	public ResponseLoginUserDTO toResponseLoginUserDTO() {
		return new ResponseLoginUserDTO(this);
	}
}
