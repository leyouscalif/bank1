package com.test.banck.RestAPI;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.test.banck.DTO.OperationDTO;
import com.test.banck.exception.NotCompteExiste;
import com.test.banck.service.ClientServiceBanque;
import com.test.banck.service.CompteServiceBanque;
import com.test.banck.service.OperationServiceBanque;

@RestController
@CrossOrigin("*")
public class ApiRestOperation {


	private ClientServiceBanque clientService;
	private CompteServiceBanque compteService;
	private OperationServiceBanque operationService;
	
	public ApiRestOperation(ClientServiceBanque clientService, CompteServiceBanque compteService, OperationServiceBanque operationService) {
		this.clientService=clientService;
		this.compteService=compteService;
		this.operationService=operationService;
	}
	
	@GetMapping("/compte/compteEP/{idCompte}/operation")
		public List<OperationDTO> getlistOperationEPById(@PathVariable(name="idCompte") String id) {
		try {
			return operationService.getOperationByIdCompte(id);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return null;
	}

	@GetMapping("/compte/compteCC/{idCompte}/operation")
		public List<OperationDTO> getlistOperationCCById(@PathVariable (name="idCompte") String idCompte) throws NotCompteExiste{
	
			try {
				return operationService.getOperationByIdCompte(idCompte);
			} catch (Exception e) {
				// TODO Auto-generated catch block
			 e.printStackTrace();
			}
			return null;
		}
	
}
