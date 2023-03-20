package com.test.banck.DTO;



import java.util.Date;

import com.test.banck.DTO.ClientDTO;
import com.test.banck.enummeration.StatutCompte;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor

public abstract class  CompteDTO {
	
	private String id;
	
	private double solde;

	private Date dateCreation;

	private ClientDTO clientDTO;
	
	private StatutCompte statutCompte;
	
}
