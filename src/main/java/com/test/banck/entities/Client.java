package com.test.banck.entities;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor @ToString
public class Client {
	
	@Id
	private String id;
	
	@Column(length=22)
	private String nom;
	
	@Column(length=40)
	private String email;
	
	@OneToMany(mappedBy="client" , fetch=FetchType.LAZY)
	private List<Compte> compte;
}
