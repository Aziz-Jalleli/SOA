package com.pi.courtierservice.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/** Mise à jour d'un courtier : le CIN vient de l'URL et ne peut pas être modifié. */
public record CourtierUpdateRequest(
		@NotBlank(message = "Le nom est obligatoire")
		@Size(max = 100, message = "Le nom ne doit pas dépasser 100 caractères")
		String nom,

		@NotBlank(message = "Le téléphone est obligatoire")
		@Pattern(regexp = "^\\+?[0-9 ]{8,15}$", message = "Numéro de téléphone invalide")
		String telephone,

		@NotNull(message = "La disponibilité est obligatoire")
		Boolean disponible,

		@Valid
		LocationRequest location) {
}
