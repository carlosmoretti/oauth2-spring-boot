package com.carlosmoretti.oauth.example.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "USER")
@AllArgsConstructor
@NoArgsConstructor
public class User {
	@Id
	@Column(name = "USER_CD_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "USER_TX_NAME")
	private String name;
	
	@Column(name = "USER_TX_EMAIL")
	private String email;
	
	@Column(name = "USER_TX_PASSWORD")
	private String password;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "USER_ROLE", 
			joinColumns = @JoinColumn(name = "USER_CD_ID"),
			inverseJoinColumns = @JoinColumn(name = "ROLE_CD_ID"))
	private List<Role> roles;
}
