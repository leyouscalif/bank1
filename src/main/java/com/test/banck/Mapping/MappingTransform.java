package com.test.banck.Mapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.banck.DTO.ClientDTO;
import com.test.banck.DTO.CompteCourantDTO;
import com.test.banck.DTO.CompteDTO;
import com.test.banck.DTO.CompteEpagneDTO;
import com.test.banck.DTO.OperationDTO;
import com.test.banck.entities.Client;
import com.test.banck.entities.Compte;
import com.test.banck.entities.CompteCourant;
import com.test.banck.entities.CompteEpagne;
import com.test.banck.entities.Operation;
import com.test.banck.exception.NotCompteExiste;

import lombok.Data;


@Service
@Data
public class MappingTransform {
	
	public String toJson(ClientDTO client) throws JsonProcessingException {
		ObjectMapper obj = new ObjectMapper();
		return obj.writerWithDefaultPrettyPrinter().writeValueAsString(client);
	}
	
	public String toJson(CompteCourantDTO compteCC) throws JsonProcessingException {
		ObjectMapper obj = new ObjectMapper();
		return obj.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
	}
	public String toJson(CompteEpagneDTO compteEP) throws JsonProcessingException {
		ObjectMapper obj = new ObjectMapper();
		return obj.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
	}
	public String toJson(OperationDTO opera) throws JsonProcessingException {
		ObjectMapper obj = new ObjectMapper();
		return obj.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
	}

	
	public ClientDTO transformVersClientDTO (Client client) {
		
		ClientDTO clientDTO = new ClientDTO();
		clientDTO.setId(client.getId());
		clientDTO.setNom(client.getNom());
		clientDTO.setEmail(client.getEmail());
		
		return clientDTO;
	}
	
	public Client transformVersClient(ClientDTO clientDTO) {
		Client client=new Client();
		client.setId(clientDTO.getId());
		client.setNom(client.getNom());
		client.setEmail(clientDTO.getEmail());
		
		return client;
	} 
	
/*	public CompteDTO transformVersCompteDTO(Compte compte) {
		
		CompteDTO compteDTO = new CompteDTO();
			compteDTO.setId(compte.getId());
			compteDTO.setDateCreation(compte.getDateCreation());
			compteDTO.setSolde(compte.getSolde());;
			compteDTO.setStatutCompte(compte.getStatutCompte());
			compteDTO.setClientDTO(transformVersClientDTO(compte.getClient()));
				
		return compteDTO;
	} */
	
	public CompteCourantDTO transformVersCompteCCDTO(CompteCourant compteCourant) {
		
	//	CompteDTO compteDTO= new CompteDTO();
		CompteCourantDTO CCdto = new CompteCourantDTO();
		//	compteDTO =  transformVersCompteDTO(compteCourant);
			CCdto.setDateCreation(compteCourant.getDateCreation());
			CCdto.setId(compteCourant.getId());
			CCdto.setSolde(compteCourant.getSolde());
			CCdto.setStatutCompte(compteCourant.getStatutCompte());
			CCdto.setClientDTO(transformVersClientDTO(compteCourant.getClient()));
			CCdto.setDecouvert(compteCourant.getDecouvert());
				
		return CCdto;
	}
	
	public CompteEpagneDTO transformVersCompteCEDTO(CompteEpagne compteEpagne) {
				
		CompteEpagneDTO CEdto = new CompteEpagneDTO();
		CEdto.setId(compteEpagne.getId());
		CEdto.setSolde(compteEpagne.getSolde());
		CEdto.setDateCreation(compteEpagne.getDateCreation());
		CEdto.setInteret(compteEpagne.getInteret());
		CEdto.setClientDTO(transformVersClientDTO(compteEpagne.getClient()));
		CEdto.setStatutCompte(compteEpagne.getStatutCompte());
			
		return CEdto;
	}

	public OperationDTO transformVersOperationDTO(Operation operation) {
		
		OperationDTO operDTO = new OperationDTO();
			operDTO.setDateOperation(operation.getDateOperation());
			operDTO.setId(operation.getId());
			operDTO.setMontant(operation.getMontant());
			operDTO.setTypeOperation(operation.getTypeOperation());
			 if(operDTO.getCompte() instanceof CompteCourantDTO)
				 operDTO.setCompte( transformVersCompteCCDTO((CompteCourant) operation.getCompte()));
			 else if(operDTO.getCompte() instanceof CompteEpagneDTO)
				 operDTO.setCompte(transformVersCompteCEDTO( (CompteEpagne) operation.getCompte()));
		
		return operDTO;
		
	}
	 public List<OperationDTO> recuperListeOperationDTO(List<Operation> listes){
		List<OperationDTO> DTOliste = new ArrayList();
				
			for(Operation oper: listes ) {
					
				OperationDTO operationDTO = transformVersOperationDTO(oper);
					DTOliste.add(operationDTO);
				}
		 	
		 return DTOliste;
		 
	 }

}
