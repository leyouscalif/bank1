package com.test.banck.RestAPI;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.test.banck.DTO.CompteCourantDTO;
import com.test.banck.DTO.CompteDTO;
import com.test.banck.DTO.CompteEpagneDTO;
import com.test.banck.exception.NotCompteExiste;
import com.test.banck.exception.NotFoundException;
import com.test.banck.service.ClientServiceBanque;
import com.test.banck.service.CompteServiceBanque;
import com.test.banck.service.OperationServiceBanque;

@RestController
@CrossOrigin("*")
public class CompteAPIRest {
	
	private ClientServiceBanque clientService;
	private CompteServiceBanque compteService;
	private OperationServiceBanque operationService;
	
	public CompteAPIRest(ClientServiceBanque clientService, CompteServiceBanque compteService, OperationServiceBanque operationService) {
		this.clientService=clientService;
		this.compteService=compteService;
		this.operationService=operationService;
	}
	
	@GetMapping("/compte/compteCC")
	public List<CompteCourantDTO> getListCC(){
		return compteService.getlistCC();
	}
	
	@GetMapping("/compte/compteEP")
	public List<CompteEpagneDTO>getlistCE(){
		return compteService.getlistCE();
	}
	
	@GetMapping("/compte/compteCC/{id}")
	public CompteCourantDTO getCompteCCById(@PathVariable(name="id") String idCompte) throws Exception {
		
		return compteService.getCompteCCById(idCompte);
	}
	
	@GetMapping("/compte/compteEP/{id}")
	public CompteEpagneDTO getCompteEPById(@PathVariable (name="id") String IdCompte) throws NotCompteExiste {
		return compteService.getCompteEPById(IdCompte);
	}

	@PostMapping("/compte/compteCC/{idCompte}/ajout")
	public CompteCourantDTO CreationCC(@RequestBody CompteCourantDTO CCDto) throws NotFoundException{
			
		return compteService.creationCompteCourant(CCDto);
	}
	
	@PostMapping("compte/compteEP/{idCompte}/ajout")
	public CompteEpagneDTO CreationEP( @RequestBody CompteEpagneDTO compteEP ) throws NotCompteExiste {
		
		return compteService.getCompteEPById(compteEP.getId());
	}
	
	@GetMapping("/compte/compteCourant/{idCompteCC}")
	public CompteCourantDTO searchCC(@PathVariable(name="idCompteCC") String id) throws Exception{
		 return  compteService.searchCompteCCById("%"+id+"%");
	}
	
}
