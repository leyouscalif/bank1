package com.test.banck.service;

import java.util.List;

import com.test.banck.DTO.ClientDTO;
import com.test.banck.DTO.CompteCourantDTO;
import com.test.banck.DTO.CompteDTO;
import com.test.banck.DTO.CompteEpagneDTO;
import com.test.banck.DTO.OperationDTO;
import com.test.banck.entities.Client;
import com.test.banck.entities.Compte;
import com.test.banck.entities.Operation;
import com.test.banck.exception.NotCompteExiste;
import com.test.banck.exception.NotFoundException;
import com.test.banck.exception.SoldeInsuffisantException;

public interface InterfaceClient {

	ClientDTO creationClient(ClientDTO clientDTO);

	List<ClientDTO> getlisteClient();

	ClientDTO getClient(String idClient) throws NotCompteExiste;

	Client transformClient(ClientDTO Cltdto);

	ClientDTO transformClientDTO(Client client);

	ClientDTO updateClient(ClientDTO clientDTO, String idClient);

	void deletClient(String idClient);

	List<ClientDTO> searchclient(String motClee);
	
	


	

	


	
	

}
