package com.test.banck.repositorie;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.test.banck.entities.Compte;
import com.test.banck.entities.CompteCourant;

public interface CompteRepository extends JpaRepository<Compte,String>{

	@Query("select c From Compte c where c.decouvert like:d")
	List<CompteCourant> searchCompteCC(@Param("d") double doucouvert);

	Compte findAllById(String compteId);
}
