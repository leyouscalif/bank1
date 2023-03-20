package com.test.banck.entities;

import java.util.Date;
import java.util.List;
//import java.util.stream.Stream;

import com.test.banck.DTO.ClientDTO;
import com.test.banck.enummeration.StatutCompte;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @ToString
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="TYPE",length=4)
public abstract class  Compte {
	@Id
	private String id;
	
	private double solde;
	
	@Temporal(TemporalType.DATE)
	private Date dateCreation;
	
	@ManyToOne
	private Client client;
	
	@Enumerated(EnumType.STRING)
	private StatutCompte statutCompte;
	
	@OneToMany(mappedBy="compte", fetch=FetchType.LAZY)
	private List<Operation> operation;

	
}
