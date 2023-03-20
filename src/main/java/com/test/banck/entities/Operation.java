package com.test.banck.entities;

import java.util.Date;

import com.test.banck.DTO.CompteDTO;
import com.test.banck.enummeration.TypeOperation;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @ToString
public class Operation {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Temporal(TemporalType.DATE)
	private Date dateOperation;
	
	private double montant;
	
	//private String description;
	
	@ManyToOne
	private Compte compte;
	
	//cette annotation permet d' afficher les valeur qui seront passees en parametre lors de creation d' une operation 
	//dans le cas contraire Maven attribura des entiesr comme valeurs par defaut 
	@Enumerated(EnumType.STRING)
	private TypeOperation typeOperation;

	
		
	}

