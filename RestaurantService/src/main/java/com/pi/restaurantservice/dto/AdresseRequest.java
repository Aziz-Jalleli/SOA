package com.pi.restaurantservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AdresseRequest(
		@NotBlank(message = "Le numéro est obligatoire")
		@Size(max = 20, message = "Le numéro ne doit pas dépasser 20 caractères")
		String numero,

		@NotBlank(message = "La rue est obligatoire")
		@Size(max = 255)
		String rue,

		@NotBlank(message = "La cité est obligatoire")
		@Size(max = 255)
		String cite,

		@NotBlank(message = "La ville est obligatoire")
		@Size(max = 255)
		String ville) {
}
