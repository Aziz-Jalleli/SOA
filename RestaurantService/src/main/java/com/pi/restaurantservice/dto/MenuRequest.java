package com.pi.restaurantservice.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record MenuRequest(
		@NotBlank(message = "Le nom du menu est obligatoire")
		@Size(max = 255, message = "Le nom du menu ne doit pas dépasser 255 caractères")
		String nom,

		@NotNull(message = "Le prix du menu est obligatoire")
		@DecimalMin(value = "0.0", message = "Le prix ne peut pas être négatif")
		@Digits(integer = 8, fraction = 2, message = "Le prix doit avoir au plus 8 chiffres et 2 décimales")
		BigDecimal prix) {
}
