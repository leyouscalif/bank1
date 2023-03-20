package com.test.banck.RestAPI;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.test.banck.DTO.ClientDTO;
import com.test.banck.DTO.CompteCourantDTO;
import com.test.banck.DTO.CompteDTO;
import com.test.banck.DTO.CompteEpagneDTO;
import com.test.banck.DTO.OperationDTO;
import com.test.banck.entities.CompteCourant;
import com.test.banck.exception.NotCompteExiste;
import com.test.banck.exception.NotFoundException;
import com.test.banck.service.ClientServiceBanque;
import com.test.banck.service.CompteServiceBanque;
import com.test.banck.service.OperationServiceBanque;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
// NB: dans le @CrossOrigin("*") l' utilisation de l'étoile entreparenthese
// indique que autorise l' accè à n'importe quel serveur ou domaine
@CrossOrigin("*")
public class ClientAPIRest {
	
	private ClientServiceBanque clientService;
	private CompteServiceBanque compteService;
	private OperationServiceBanque operationService;
	
	@GetMapping("/clients")
	public List<ClientDTO> getListeClient() {
		
		return  clientService.getlisteClient();
	}
	
	@GetMapping("/clients/{idClient}")
	public ClientDTO getclient(@PathVariable String idClient) throws NotCompteExiste {
		 return clientService.getClient(idClient);
	}
				
	@GetMapping("/clients/search")
	public List<ClientDTO>searchClients(@RequestParam(name="motClee" ,defaultValue="" )  String motClee){
		return  clientService.searchclient("%"+motClee+"%");
	}
	
		
	@PostMapping("/clients/ajout")
		public ClientDTO ajoutClient(@RequestBody ClientDTO clientDTO) {
			return clientService.creationClient(clientDTO);
	}
	
	@PutMapping("/clients/modifier/{id}")
	public ClientDTO updateClient(@RequestBody ClientDTO clientdto, @PathVariable(name="id") String idClient) {
		
		return clientService.updateClient(clientdto, idClient);
		
	}
	
	@DeleteMapping("/clients/supprimer/{idClient}")
	public void deletClient(@PathVariable String idClient)  {
		 clientService.deletClient(idClient);
	}
	
	
	
	
	
}
