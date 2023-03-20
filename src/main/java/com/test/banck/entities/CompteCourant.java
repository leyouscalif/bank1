package com.test.banck.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@DiscriminatorValue("CC")
@Data @NoArgsConstructor @AllArgsConstructor @ToString
public class CompteCourant extends Compte {

	private double decouvert;
}
