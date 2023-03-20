package com.test.banck;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.test.banck.DTO.ClientDTO;
import com.test.banck.DTO.CompteCourantDTO;
import com.test.banck.DTO.CompteDTO;
import com.test.banck.DTO.CompteEpagneDTO;
import com.test.banck.Mapping.MappingTransform;
import com.test.banck.entities.Client;
import com.test.banck.entities.Compte;
import com.test.banck.entities.CompteCourant;
import com.test.banck.entities.CompteEpagne;
import com.test.banck.exception.NotCompteExiste;
import com.test.banck.exception.NotFoundException;
import com.test.banck.exception.SoldeInsuffisantException;
import com.test.banck.repositorie.ClientRepository;
import com.test.banck.repositorie.CompteRepository;
import com.test.banck.service.ClientServiceBanque;
import com.test.banck.service.CompteServiceBanque;

import com.test.banck.service.OperationServiceBanque;

@SpringBootApplication
public class BanckApplication {

	public static void main(String[] args) {
		SpringApplication.run(BanckApplication.class, args);
	}
	
	//chargement des commandes a executer issue des interface repositoriy
	@Bean
	CommandLineRunner start(CompteRepository compteRep,ClientRepository clientRep ,  CompteServiceBanque compteService,ClientServiceBanque clientService, OperationServiceBanque operationService, MappingTransform mapping) {
		return agrs ->{
			//synstaxe pour creation d'une liste de client
			Stream.of("Yous","Calif","Stev","Davis","KD","Harden","Goss","kyrie").forEach(nom->{
				ClientDTO client=new ClientDTO();
				client.setId(UUID.randomUUID().toString());
				client.setNom(nom);
				client.setEmail(nom+"@gmail.com");
				clientService.creationClient(client);
			});
			
			clientService.getlisteClient().forEach(client->{
				
				CompteCourantDTO compteCC = new CompteCourantDTO();
						 compteCC.setId(UUID.randomUUID().toString());
						 compteCC.setClientDTO(client);
						 compteCC.setDateCreation(new Date());
						 compteCC.setDecouvert(10000+Math.random()*10000);
						 compteCC.setSolde(50000+Math.random()*25);
						 compteCC.setStatutCompte(compteCC.getStatutCompte().ACTIVER);	
					 
				CompteEpagneDTO compteEp=new CompteEpagneDTO();
								compteEp.setDateCreation(new Date());
								compteEp.setId(UUID.randomUUID().toString());
								compteEp.setInteret(4.5);
								compteEp.setSolde( 25000+Math.random()*250);
								compteEp.setStatutCompte(compteEp.getStatutCompte().ACTIVER);
								compteEp.setClientDTO(client);
						try {
							
							compteService.creationCompteCourant(compteCC);
							compteService.creationCompteEpagne(compteEp);
																
						} catch (NotFoundException e) {
							System.out.println("***************************");
								e.printStackTrace();
							System.out.println("***************************");
						}   
										
					List<CompteCourantDTO> comptes= compteService.getlistCC();
						for(CompteDTO compte: comptes ) {
							
							if(compte.getSolde()<=100000) {
												
									try {
										operationService.CrediterCompte(compte.getId(),100000);
									} catch (NotCompteExiste e) {
										System.out.println("***************************");
											e.printStackTrace();
										System.out.println("***************************");
									}
							}
						} 
							
					List<CompteEpagneDTO> comptesEp = compteService.getlistCE();
					for(CompteEpagneDTO compte:comptesEp) {
							if(compte.getSolde()>25000) {
								try {
									operationService.DebiterCompte(compte.getId(), 20000);
								}catch (NotCompteExiste |SoldeInsuffisantException | JsonProcessingException e) {
									System.out.println("***************************");
									e.printStackTrace();
								System.out.println("***************************");
								
							}
					}
				};
			});						
	};					
										
  }
}								
												
			

			
							
			
	