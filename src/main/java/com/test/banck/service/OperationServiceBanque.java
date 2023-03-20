package com.test.banck.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.test.banck.DTO.CompteCourantDTO;
import com.test.banck.DTO.CompteEpagneDTO;
import com.test.banck.DTO.OperationDTO;
import com.test.banck.Mapping.MappingTransform;
import com.test.banck.entities.Compte;
import com.test.banck.entities.CompteCourant;
import com.test.banck.entities.CompteEpagne;
import com.test.banck.entities.Operation;
import com.test.banck.exception.NotCompteExiste;
import com.test.banck.exception.NotFoundException;
import com.test.banck.exception.SoldeInsuffisantException;
import com.test.banck.repositorie.ClientRepository;
import com.test.banck.repositorie.CompteRepository;
import com.test.banck.repositorie.OperationRepository;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class OperationServiceBanque implements InterfaceOperation {
	
	private MappingTransform mapping;
	private ClientRepository clientRep;
	private CompteRepository compteRep;
	private OperationRepository operationRep;
	private CompteServiceBanque compteService;
	public OperationServiceBanque(CompteServiceBanque compteService,MappingTransform mapping,ClientRepository clientRep,CompteRepository compteRep,OperationRepository operationRep ) {
		this.mapping=mapping;
		this.clientRep=clientRep;
		this.compteRep=compteRep;
		this.operationRep=operationRep;
		this.compteService=compteService;
		
	}
	@Override
	public List<OperationDTO> getListOperation(String idCompte) throws Exception  {
		Compte compte=compteRep.findAllById(idCompte);
		if(compte!=null) {
				List<Operation> opera = operationRep.findAllById(compte.getId());
				List<OperationDTO> listDTO = new ArrayList<OperationDTO>();
			opera.forEach(ope -> {
			//	OperationDTO OperaDto=mapping.transformVersOperationDTO(ope);
				listDTO.add(mapping.transformVersOperationDTO(ope));
			});
			return listDTO;
		}
		else
			throw new Exception("L'id du compte Compte courant n' a pas ete trouvé ");
		}
		
	@Override
	public List<OperationDTO> getOperationByIdCompte(String idCompte) throws Exception{
		Compte cpt=compteRep.findAllById(idCompte);
		List<OperationDTO> list = new ArrayList<OperationDTO>();
			if(cpt instanceof CompteCourant && cpt!=null) {
				CompteCourant CC=(CompteCourant) cpt;
					List<Operation> operations=CC.getOperation();
					operations.forEach(ope->{
						//OperationDTO operaDTO=mapping.transformVersOperationDTO(ope);
					//	ope.setCompte(mapping.transformVersCompteCCDTO((CompteCourant) cpt));
					list.add(mapping.transformVersOperationDTO(ope));
				});	
				return list;
				
			}
			else if(cpt instanceof CompteEpagne && cpt!=null) {
				CompteEpagne CE= (CompteEpagne) cpt;
					List<Operation>operations=CE.getOperation();
					operations.forEach(ope->{
							ope.setCompte(cpt);
							list.add(mapping.transformVersOperationDTO(ope));
					});
				return list;
			}
			else 
				throw new NotCompteExiste("Cet id fait réference à aucun compte");
	}	
	
	@Override
	public OperationDTO CrediterCompte(String id, double montant) throws NotCompteExiste {
		CompteCourant compte=(CompteCourant) compteRep.findAllById(id);
		OperationDTO operaDTO= new OperationDTO();
		if(compte!=null) {
			Operation opera=new Operation();
					  opera.setCompte(compte);
					  opera.setDateOperation(new Date());
					  opera.setMontant(montant);
					  opera.setTypeOperation(opera.getTypeOperation().CREDITER);
					  
			operaDTO = mapping.transformVersOperationDTO(operationRep.save(opera));
		
					compte.setSolde(compte.getSolde()+montant);
			compteRep.save(compte);
					
			return  operaDTO;	
		}else {
			throw new NotCompteExiste("L'id du compte Compte courant n' a pas ete trouvé");
		}
	
	}
	@Override
	public OperationDTO DebiterCompte(String id, double montant) throws NotCompteExiste, SoldeInsuffisantException, JsonProcessingException {
		CompteEpagne compte=(CompteEpagne) compteRep.findAllById(id);
		
		if(compte==null) 
			throw new NotCompteExiste("L'id du compte Compte courant n' a pas ete trouvé");
		else if(compte.getSolde()<montant)
			throw new SoldeInsuffisantException("Le solde de votre compte est insuffisant pour effeectuer le retait!");
		else{
			OperationDTO operaDTO=new OperationDTO();
			Operation opera=new Operation();
					  opera.setCompte(compte);
					  opera.setDateOperation(new Date());
					  opera.setMontant(montant);
					  opera.setTypeOperation(opera.getTypeOperation().DEBITER);
					  					  
			operaDTO = mapping.transformVersOperationDTO(operationRep.save(opera));
					   mapping.toJson(operaDTO);
					compte.setSolde(compte.getSolde()-montant);
			compteRep.save(compte);
			
			return  operaDTO;	
		}
	}
	
	@Override
	public void Transfert(String idCompteSource, String idCompteDestination, long montant) throws NotCompteExiste, SoldeInsuffisantException, JsonProcessingException {
			Compte compteSR = compteRep.findAllById(idCompteDestination);
			Compte compteDS = compteRep.findAllById(idCompteDestination);
			OperationDTO opera= new OperationDTO();
						 opera.setTypeOperation(opera.getTypeOperation().VIREMENT);
			DebiterCompte(compteSR.getId(),montant);
			CrediterCompte(compteDS.getId(),montant);
			compteSR.setSolde(compteSR.getSolde()-montant);
			compteRep.save(compteSR);
			compteDS.setSolde(compteDS.getSolde()+montant);
			compteRep.save(compteDS);
	}

}
