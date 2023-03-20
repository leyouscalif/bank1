package com.test.banck.service;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.test.banck.DTO.OperationDTO;
import com.test.banck.exception.NotCompteExiste;
import com.test.banck.exception.SoldeInsuffisantException;

public interface InterfaceOperation {

	List<OperationDTO> getListOperation(String idCompte) throws NotCompteExiste, Exception ;

	OperationDTO DebiterCompte(String id, double montant)
			throws NotCompteExiste, SoldeInsuffisantException, JsonProcessingException;

	OperationDTO CrediterCompte(String id, double montant) throws NotCompteExiste;

	void Transfert(String idCompteSource, String idCompteDestination, long montant) throws NotCompteExiste,JsonProcessingException ,SoldeInsuffisantException;

	List<OperationDTO> getOperationByIdCompte(String idCompte) throws Exception;
	
}
