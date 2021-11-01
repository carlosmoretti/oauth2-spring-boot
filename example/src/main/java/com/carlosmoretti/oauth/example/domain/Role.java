package com.carlosmoretti.oauth.example.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ROLE")
@AllArgsConstructor
@NoArgsConstructor
public class Role implements GrantedAuthority {
	
	private static final long serialVersionUID = 1L;
	
	public Role(String nome) {
		this.name = nome;
	}

	@Id
	@Column(name = "ROLE_CD_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "ROLE_TX_NAME")
	private String name;

	@Override
	public String getAuthority() {
		// TODO Auto-generated method stub
		return this.name;
	}
}
