package com.test.banck.DTO;

import java.util.Date;

import com.test.banck.DTO.CompteDTO;
import com.test.banck.enummeration.TypeOperation;

import lombok.Data;

@Data 
public class OperationDTO {
	
	private Long id;
		
	private Date dateOperation;
	
	private double montant;
	
	//private String description;
		
	private CompteDTO compte;
	
	private TypeOperation typeOperation;
}
