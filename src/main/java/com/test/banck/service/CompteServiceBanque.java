package com.test.banck.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

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
import com.test.banck.repositorie.OperationRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public  class CompteServiceBanque implements InterfaceCompte{
	private MappingTransform mapping;
	private ClientRepository clientRep;
	private CompteRepository compteRep;
	private OperationRepository operationRep;
	
	public CompteServiceBanque(MappingTransform mapping,ClientRepository clientRep,CompteRepository compteRep,OperationRepository operationRep ) {
		this.mapping=mapping;
		this.clientRep=clientRep;
		this.compteRep=compteRep;
		this.operationRep=operationRep;
	}

	@Override
	public CompteCourantDTO creationCompteCourant(CompteCourantDTO compteCC2)
			throws NotFoundException {
		Compte compteCC= new CompteCourant();
			   compteCC.setId(compteCC2.getId());
			   compteCC.setSolde(compteCC2.getSolde());
			   compteCC.setDateCreation(compteCC2.getDateCreation());
			   compteCC.setStatutCompte(compteCC2.getStatutCompte().ACTIVER);
			   ((CompteCourant) compteCC).setDecouvert(compteCC2.getDecouvert());
			   compteCC.setClient(mapping.transformVersClient(compteCC2.getClientDTO()));
		CompteCourantDTO compteCCdto=mapping.transformVersCompteCCDTO((CompteCourant) compteRep.save(compteCC));   
				
		return compteCCdto;
	}

	@Override
	public CompteEpagneDTO creationCompteEpagne(CompteEpagneDTO Epdto){
			
					Compte compteEP = new CompteEpagne(); 
					compteEP.setSolde(Epdto.getSolde());
					((CompteEpagne) compteEP).setInteret(Epdto.getInteret());
					compteEP.setId(Epdto.getId());
					compteEP.setDateCreation(new Date());
					compteEP.setClient(mapping.transformVersClient(Epdto.getClientDTO()));
					compteEP.setStatutCompte(Epdto.getStatutCompte().ACTIVER);
					CompteEpagneDTO compteEPdto=mapping.transformVersCompteCEDTO((CompteEpagne) compteRep.save(compteEP));
					
					return compteEPdto;
				
	}

/*	@Override
	public List<CompteDTO> getListeCompte() {
		List<CompteDTO> listDto = new ArrayList<>();
			compteRep.findAll().forEach(cpt-> listDto.add(mapping.transformVersCompteDTO(cpt)));
		return listDto;
	} */

	@Override
	public List<CompteCourantDTO> getlistCC() {
		List<CompteCourantDTO>listCC=new ArrayList<CompteCourantDTO>();
		List<Compte> list = compteRep.findAll();
		list.forEach(compte ->{
				if(compte instanceof CompteCourant) {
					listCC.add(mapping.transformVersCompteCCDTO((CompteCourant) compte));
									}	
		});
		return listCC;
	}

	@Override
	public List<CompteEpagneDTO> getlistCE() {
		List<CompteEpagneDTO> listEP = new ArrayList<CompteEpagneDTO>();
		List<Compte> list=compteRep.findAll();
		list.forEach(compte -> {
			if(compte instanceof CompteEpagne) {
				listEP.add(mapping.transformVersCompteCEDTO((CompteEpagne)compte));
			}
		});
		return listEP;
	}

	@Override
	public CompteCourantDTO searchCompteCCById(String idcompte) throws Exception {
			CompteCourant compte=(CompteCourant) compteRep.findAllById(idcompte);
		if(compte!=null) {
			CompteCourantDTO compteDto=mapping.transformVersCompteCCDTO(compte);
			return compteDto;
		}else {
			throw new Exception("L'id du compte Compte courant n' a pas ete trouvé ");
		}
	}
	
	@Override
	public CompteCourantDTO getCompteCCById(String idCompte) throws NotCompteExiste {
		
		CompteCourant compteCC = (CompteCourant) compteRep.findAllById(idCompte);
		 if(compteCC!=null)
			 return mapping.transformVersCompteCCDTO(compteCC);
		 else
			 throw new NotCompteExiste("L'id de ce Compte courant fais refference à aucun compte veeillez sasr un compte correcte");
				
	}
	
	@Override
	public CompteEpagneDTO getCompteEPById(String idCompte) throws NotCompteExiste {
		
		CompteEpagne compteCC = (CompteEpagne) compteRep.findAllById(idCompte);
		 if(compteCC!=null)
			 return mapping.transformVersCompteCEDTO(compteCC);
		 else
			 throw new NotCompteExiste("L'id de ce Compte Epagne fais refference à aucun compte veeillez sasr un compte correcte");
				
	}
	
	
	/*
	@Override
	public CompteEpagneDTO creationCompteEpagne(double montantInit, double interet, String idClient) throws NotFoundException {
		
		Client client=clientRep.findById(idClient).orElse(null);
			
		if( client == null)
			throw new NotFoundException("id non trouver");
		
		CompteEpagne compteEP = new CompteEpagne();
			compteEP.setClient(client);
			compteEP.setInteret( (int) interet);
			compteEP.setId(UUID.randomUUID().toString());
			compteEP.setDateCreation(new Date());
			compteEP.setSolde((int) montantInit);
			compteEP.setStatutCompte(StatutCompte.ACTIVER);
			
			Compte compteEpagne =compteRep.save(compteEP);
		return mapperService.transformVersCompteCEDTO((CompteEpagne) compteEpagne);
	}

 //	public 
	

	@Override
	public void debiterCompte(String idCompte, long montant, String description) throws NotCompteExiste, SoldeInsuffisantException {
						
			Compte compte= compteRep.findById(idCompte).orElseThrow(
				()-> new NotCompteExiste("\n id inexistant dans la base de donnée\n")
			);	
										
			if(compte.getSolde()<montant)
				throw new SoldeInsuffisantException("le solde est plus petit le montant à rétirer");
			
			Operation operation = new Operation();
			operation.setId(operation.getId());
			operation.setDateOperation(new Date());
			operation.setDescription(description);
			operation.setMontant(montant);
			operation.setTypeOperation(TypeOperation.DEBITER);
			operation.setCompte(compte);
			operationRep.save(operation);
						
			compte.setSolde(compte.getSolde()-montant);
			compteRep.save(compte);
	}

	@Override
	public void CrediterCompte(String idCompte, long montant, String description) throws NotFoundException {
		 
		Compte compte=compteRep.findById(idCompte).orElseThrow(
				()-> new NotFoundException("\n le compte lie a cet id n' existe pas!\n"));
		//CompteDTO compteDTO=mapperService.transformVersCompteDTO(compte);
		Operation operation=new Operation();
		
		 operation.setDateOperation(new Date());
		 operation.setDescription(description);
		 operation.setMontant(montant);
		 operation.setTypeOperation(TypeOperation.CREDITER);
		 operation.setCompte(compte);
		 operationRep.save(operation);
		 
		 compte.setSolde(montant+compte.getSolde());
		 compteRep.save(compte);
		 
	}
	
	public List<OperationDTO>getListOperation(String idCompte) throws NotCompteExiste{
		
		Compte compte=compteRep.findById(idCompte).orElseThrow(
				()-> new NotCompteExiste("Compte y n'existant "));
		List<Operation>listoper=compte.getOperation();
		List<OperationDTO>listOperDTO = new ArrayList<OperationDTO>();
		for(Operation opera: listoper) {
			opera.setCompte(compte);
			 compte.getOperation().forEach(optyp-> opera.setTypeOperation(optyp.getTypeOperation()));
		
			listOperDTO.add(mapperService.transformVersOperationDTO(opera));
		}
		System.out.println("/n"+compte+"/n");
		System.out.println("/n"+listOperDTO+"/n");
		
		return listOperDTO;
	}
	

	@Override
	public void Transfert(String idCompteSource, String idCompteDestination, long montant, String description) throws NotFoundException, NotCompteExiste, SoldeInsuffisantException {
						
		CrediterCompte( idCompteDestination, montant, "Tansfert vers le compte ayant pour Id:"+idCompteDestination);
		debiterCompte( idCompteSource,  montant,  "Transfert en provenance du compte ayant pour Id: "+idCompteSource);
	}


public CompteCourantDTO creationCompteCourant( double soldeinitial, double decouvert, String idClient) throws NotFoundException {
		
		Client client=clientRep.findById(idClient).orElse(null);
		
		if(client == null)
			throw new NotFoundException("cet id n' existe pas dans la base de donneé");
		
		CompteCourant compteCC = new CompteCourant();
			compteCC.setDateCreation(new Date());
			compteCC.setDecouvert( (int) decouvert);
			compteCC.setId(UUID.randomUUID().toString());
			compteCC.setSolde( (int)soldeinitial);
			compteCC.setStatutCompte(StatutCompte.ACTIVER);
			compteCC.setClient(client);
		Compte compteCourant=compteRep.save(compteCC);
			
		return mapperService.transformVersCompteCCDTO((CompteCourant) compteCourant);
	}
	@Override
	public List<CompteDTO> getListeCompte(){
		List<Compte> comptes = compteRep.findAll();
		List<CompteDTO> compteDTOs = new ArrayList();
		for(Compte compte: comptes) {
			CompteDTO compteDTO = mapperService.transformVersCompteDTO(compte);
			compteDTOs.add(compteDTO);
		}
		
		return compteDTOs;
	}
	
	
	@Override
	public CompteDTO getCompte(String idCompte) throws NotCompteExiste {
		Compte compte = compteRep.findById(idCompte).orElseThrow(
				()-> new NotCompteExiste("compte y n'existant"));
		CompteDTO compteDTO = mapperService.transformVersCompteDTO(compte) ;
		
		return compteDTO;
	}
	
	@Override
	public List<CompteCourantDTO> getlistCC(){
		List<Compte> comptes = compteRep.findAll();
		List<CompteCourantDTO> CClist = new ArrayList<CompteCourantDTO>();
	//	List<CompteEpagne> CE = new ArrayList<CompteEpagne>();
		for(Compte compte: comptes) {
			if(compte instanceof CompteCourant) {
				CClist.add(mapperService.transformVersCompteCCDTO((CompteCourant) compte));
			}
		}
		
		return CClist;
	}
	
	@Override
	public List<CompteEpagneDTO> getlistCE(){
		List<CompteEpagneDTO> CElist = new ArrayList<CompteEpagneDTO>() ;
		List<Compte> comptes = compteRep.findAll();
				for(Compte compte:comptes)
					if(compte instanceof CompteEpagne)
						CElist.add(mapperService.transformVersCompteCEDTO((CompteEpagne) compte));
						
		return CElist;
	}

	@Override
	public CompteCourantDTO searchCompteCC(String id){
		CompteCourantDTO comptCC= mapperService.transformVersCompteCCDTO(compteRep.findAllById(id))  ;
			
		return comptCC;
	}
	

	*/

}
