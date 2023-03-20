package com.test.banck.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class CompteEpagneDTO extends CompteDTO {

	private double interet;
}
