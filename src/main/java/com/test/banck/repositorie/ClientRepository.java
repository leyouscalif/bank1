package com.test.banck.repositorie;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.test.banck.entities.Client;

public interface ClientRepository extends JpaRepository <Client,String> {

	@Query("select c from Client c Where c.nom like:mk" )
	List<Client> searchClient(@Param("mk") String motClee);
		
}

