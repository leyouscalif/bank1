package com.test.banck.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("CE")
@Data @NoArgsConstructor @AllArgsConstructor
public class CompteEpagne extends Compte {

	private double interet;
}
