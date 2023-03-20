package com.test.banck.repositorie;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.banck.entities.Operation;

public interface OperationRepository extends JpaRepository <Operation,Long> {

	List<Operation> findAllById(String id);

}
