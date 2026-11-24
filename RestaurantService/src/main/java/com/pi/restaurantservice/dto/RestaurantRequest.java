package com.pi.restaurantservice.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RestaurantRequest(
		@NotBlank(message = "Le nom est obligatoire")
		@Size(max = 255, message = "Le nom ne doit pas dépasser 255 caractères")
		String nom,

		@NotNull(message = "L'adresse est obligatoire")
		@Valid
		AdresseRequest adresse) {
}
