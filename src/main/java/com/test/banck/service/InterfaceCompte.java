package com.test.banck.service;

import java.util.List;

import com.test.banck.DTO.CompteCourantDTO;
import com.test.banck.DTO.CompteDTO;
import com.test.banck.DTO.CompteEpagneDTO;
import com.test.banck.exception.NotCompteExiste;
import com.test.banck.exception.NotFoundException;


public interface InterfaceCompte {

	
	CompteCourantDTO creationCompteCourant(CompteCourantDTO compteCC) throws NotFoundException;

	CompteEpagneDTO creationCompteEpagne(CompteEpagneDTO compteEPdto) ;

//	List<CompteDTO> getListeCompte();
	
	
	List<CompteCourantDTO> getlistCC();

	List<CompteEpagneDTO> getlistCE();
	
	CompteCourantDTO searchCompteCCById(String id) throws Exception;

	CompteCourantDTO getCompteCCById(String idCompte) throws NotCompteExiste;

	CompteEpagneDTO getCompteEPById(String idCompte) throws NotCompteExiste;
	
	
}
