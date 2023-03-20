package com.test.banck.service;



import java.util.ArrayList;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.test.banck.DTO.ClientDTO;

import com.test.banck.Mapping.MappingTransform;
import com.test.banck.entities.Client;

import com.test.banck.exception.NotCompteExiste;
import com.test.banck.repositorie.ClientRepository;
import com.test.banck.repositorie.CompteRepository;
import com.test.banck.repositorie.OperationRepository;

@Service
@Transactional

public class ClientServiceBanque implements InterfaceClient {

	private  MappingTransform mapperService;
	
	private ClientRepository clientRep;
	private CompteRepository compteRep;
	private OperationRepository operationRep;

	public ClientServiceBanque(ClientRepository clientRep, CompteRepository compteRep,OperationRepository operationRep, MappingTransform mapperService) {
		this.mapperService=mapperService;
		this.clientRep=clientRep;
		this.compteRep=compteRep;
		this.operationRep=operationRep;
	}
	
	Logger log= LoggerFactory.getLogger(this.getClass().getName());

	@Override
	public ClientDTO creationClient(ClientDTO clientDTO) {
		Client client=mapperService.transformVersClient(clientDTO);
		client.setId(UUID.randomUUID().toString());
		client.setNom(clientDTO.getNom());
		Client Clt=	clientRep.save(client);
					
		return mapperService.transformVersClientDTO(Clt);
	}
	
	@Override
	public List<ClientDTO> getlisteClient() {
		
		List<Client> clients = clientRep.findAll();
		List<ClientDTO> clientDTOs = new ArrayList<ClientDTO>();
		for (Client client: clients) {
				ClientDTO clientdto = mapperService.transformVersClientDTO(client);
				clientDTOs.add(clientdto);
		}
		return clientDTOs;
	}
		
	@Override
	public ClientDTO getClient(String idClient) throws NotCompteExiste {
		
			Client	client = clientRep.findById(idClient).orElseThrow(
					()-> new NotCompteExiste(" ce compte n existe pas"));
			ClientDTO clientDTO=mapperService.transformVersClientDTO(client);
			return clientDTO;
	}
			
	@Override
	public Client transformClient(ClientDTO Cltdto) {
		return mapperService.transformVersClient(Cltdto);
	}
	
	@Override
	public ClientDTO transformClientDTO(Client client) {
		return mapperService.transformVersClientDTO(client);
	}

	@Override
	public ClientDTO updateClient(ClientDTO clientDTO, String idClient) {
			Client client=mapperService.transformVersClient(clientDTO);
			client.setNom(clientDTO.getNom());
			client.setEmail(clientDTO.getEmail());
			client.setId(idClient);
			clientRep.save(client);
			
		return mapperService.transformVersClientDTO(client);
	}
	
	@Override
	public void deletClient(String idClient) {
		
		clientRep.deleteById(idClient);
				
	}
		
	@Override
	public List<ClientDTO> searchclient(String motClee){
		List<Client> list=clientRep.searchClient(motClee);
		List<ClientDTO>listclientDto= new ArrayList<ClientDTO>();
		for(Client client:list) {
			listclientDto.add(transformClientDTO(client));
		}
				
		return listclientDto;
	}
	

	
		
}
