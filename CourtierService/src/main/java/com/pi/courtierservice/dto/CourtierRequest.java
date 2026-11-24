package com.pi.courtierservice.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/** Création d'un courtier. {@code disponible} vaut {@code true} s'il est omis. */
public record CourtierRequest(
		@NotBlank(message = "Le CIN est obligatoire")
		@Size(max = 20, message = "Le CIN ne doit pas dépasser 20 caractères")
		String cin,

		@NotBlank(message = "Le nom est obligatoire")
		@Size(max = 100, message = "Le nom ne doit pas dépasser 100 caractères")
		String nom,

		@NotBlank(message = "Le téléphone est obligatoire")
		@Pattern(regexp = "^\\+?[0-9 ]{8,15}$", message = "Numéro de téléphone invalide")
		String telephone,

		Boolean disponible,

		@Valid
		LocationRequest location) {
}
